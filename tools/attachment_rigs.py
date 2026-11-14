"""Single source of truth for every equipped attachment model.

Each rig copies the real parts of the source mob's vanilla 1.20.1 model (same boxes, same UV offsets,
same texture size, same mirror flags) and then maps them onto the player with one transform:

    vertex_on_player = dst + scale * rotation_zyx(rot) * (vertex_in_vanilla_space - src)

`src` is a point in the vanilla model's space, `dst` is where it lands in the player part's space
(head, body or leg, in pixels, y pointing down, -z pointing out of the player's face/chest).
Player armour shells: helmet +/-5 around the head, chestplate z = -3 / +3, leggings +/-2.5, boots +/-3.

Running this script regenerates:
  * src/main/java/com/nanonaitor/forgedarmor/client/AttachmentRigs.java
  * textures/entity/attachment/*.png for attachments that have no vanilla mob part (hearts, cores, wind)
  * a three.js preview page (path given on the command line, default build/attachment_preview.html)
"""
from __future__ import annotations

import base64
import io
import json
import math
import sys
from dataclasses import dataclass, field
from pathlib import Path

from PIL import Image

ROOT = Path(__file__).resolve().parents[1]
MOD = "nanonaitor_forged_armor"
ASSETS = ROOT / "src/main/resources/assets" / MOD
JAVA_OUT = ROOT / "src/main/java/com/nanonaitor/forgedarmor/client/AttachmentRigs.java"
GENERATED_TEX = ASSETS / "textures/entity/attachment"


# ----------------------------------------------------------------------------------------------- model types

@dataclass
class Box:
    u: int
    v: int
    x: float
    y: float
    z: float
    w: float
    h: float
    d: float
    mirror: bool = False
    grow: float = 0.0


@dataclass
class Part:
    name: str
    boxes: list
    pivot: tuple = (0.0, 0.0, 0.0)
    rot: tuple = (0.0, 0.0, 0.0)  # radians, applied like ModelPart (Z * Y * X)
    children: list = field(default_factory=list)


@dataclass
class Rig:
    type: str
    anchor: str  # head | body | legs
    texture: str  # namespace:path below textures/, e.g. minecraft:entity/goat/goat
    tex_size: tuple
    parts: dict  # side -> [Part]; side is both | left | right (left/right only for legs)
    src: tuple
    dst: tuple
    rot_deg: tuple = (0.0, 0.0, 0.0)
    scale: tuple | float = 1.0
    translucent: bool = False
    glow: str | None = None  # emissive overlay texture rendered with RenderType.eyes

    @property
    def scale3(self):
        s = self.scale
        return (s, s, s) if isinstance(s, (int, float)) else tuple(s)


def deg(a):
    return math.radians(a)


def both(*parts):
    return {"both": list(parts)}


# ----------------------------------------------------------------------------------------------- generated textures

def voxel_parts(sprite_path: Path, atlas_out: Path):
    """Extrude a 16x16 item sprite into 1x1x1 voxels, exactly like a held item, with a per-voxel colour atlas."""
    sprite = Image.open(sprite_path).convert("RGBA")
    atlas = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    boxes = []
    k = 0
    for py in range(sprite.height):
        for px in range(sprite.width):
            c = sprite.getpixel((px, py))
            if c[3] < 128:
                continue
            u, v = 4 * (k % 16), 2 * (k // 16)
            for dx in range(4):
                for dy in range(2):
                    atlas.putpixel((u + dx, v + dy), c[:3] + (255,))
            boxes.append(Box(u, v, px - 8, py - 8, -0.5, 1, 1, 1))
            k += 1
    assert k <= 512, sprite_path
    atlas.save(atlas_out)
    return [Part("voxels", boxes)]


def breeze_texture(out: Path):
    """Pale translucent wind streaks for the three swirling breeze rings (Breeze does not exist in 1.20.1)."""
    img = Image.new("RGBA", (64, 64), (0, 0, 0, 0))
    rings = [(0, 0, 7, 2, 7), (0, 10, 8, 2, 8), (0, 21, 9, 2, 9)]
    for (u, v, w, h, d) in rings:
        # side faces span u .. u+2d+2w, rows v+d .. v+d+h
        length = 2 * d + 2 * w
        for i in range(length):
            for j in range(h):
                phase = (i + j * 3) % 6
                if phase in (0, 1):
                    col = (233, 247, 255, 215)
                elif phase == 2:
                    col = (176, 214, 240, 170)
                elif phase == 3:
                    col = (140, 186, 226, 120)
                else:
                    col = (0, 0, 0, 0)
                img.putpixel((u + i, v + d + j), col)
    img.save(out)


# ----------------------------------------------------------------------------------------------- the rigs

def spike_ring(u, v, yaw_list, radius):
    """Guardian spikes (vanilla 2x9x2 box) pointing outward horizontally at the given yaws (radians)."""
    parts = []
    for i, yaw in enumerate(yaw_list):
        px, pz = -radius * math.sin(yaw), -radius * math.cos(yaw)
        parts.append(Part(f"spike_{i}", [Box(u, v, -1, -4.5, -1, 2, 9, 2)], (px, 0, pz), (deg(-90), yaw, 0)))
    return parts


def build_rigs():
    R = []
    item = ASSETS / "textures/item"
    GENERATED_TEX.mkdir(parents=True, exist_ok=True)

    # ---------------------------------------------------------------- helmet: horns & face
    R.append(Rig("ravager_horn", "head", "minecraft:entity/illager/ravager", (128, 128), both(
        Part("right_horn", [Box(74, 55, 0, -14, -2, 2, 14, 4)], (-10, -14, -8), (1.0996, 0, 0)),
        Part("left_horn", [Box(74, 55, 0, -14, -2, 2, 14, 4, mirror=True)], (8, -14, -8), (1.0996, 0, 0)),
    ), src=(-1, -14, -8), dst=(0, -6.6, 0.4), scale=0.56))

    R.append(Rig("goat_horn", "head", "minecraft:entity/goat/goat", (64, 64), both(
        Part("left_horn", [Box(12, 55, -0.01, -16, -10, 2, 7, 2)]),
        Part("right_horn", [Box(12, 55, -2.99, -16, -10, 2, 7, 2)]),
    ), src=(-0.5, -9, -9), dst=(0, -8.6, 0.3), rot_deg=(-12, 0, 0), scale=1.25))

    R.append(Rig("pillager_nose", "head", "minecraft:entity/illager/pillager", (64, 64), both(
        Part("nose", [Box(24, 0, -1, -1, -6, 2, 4, 2)], (0, -2, 0)),
    ), src=(0, -3, -4), dst=(0, -3.2, -4.9), scale=1.0))

    for name, tex, size, hat in (("zombie_mask", "zombie/zombie", (64, 64), True),
                                 ("skeleton_mask", "skeleton/skeleton", (64, 32), False),
                                 ("creeper_mask", "creeper/creeper", (64, 32), False)):
        boxes = [Box(0, 0, -4, -8, -4, 8, 8, 8)]
        if hat:
            boxes.append(Box(32, 0, -4, -8, -4, 8, 8, 8, grow=0.25))
        R.append(Rig(name, "head", f"minecraft:entity/{tex}", size, both(Part("head", boxes)),
                     src=(0, -4, 0), dst=(0, -4.2, 0), scale=1.3))

    R.append(Rig("spider_mask", "head", "minecraft:entity/spider/spider", (64, 32), both(
        Part("head", [Box(32, 4, -4, -4, -8, 8, 8, 8)]),
    ), src=(0, 0, -4), dst=(0, -4.2, 0), scale=1.3, glow="minecraft:entity/spider_eyes"))

    R.append(Rig("hoglin_horn", "head", "minecraft:entity/hoglin/hoglin", (128, 64), both(
        Part("right_horn", [Box(10, 13, -1, -11, -1, 2, 11, 2)], (-7, 2, -12)),
        Part("left_horn", [Box(1, 13, -1, -11, -1, 2, 11, 2)], (7, 2, -12)),
    ), src=(0, 2, -12), dst=(0, 0.6, -5.5), rot_deg=(-8, 0, 0), scale=0.62))

    R.append(Rig("bunny_ears", "head", "minecraft:entity/rabbit/brown", (64, 32), both(
        Part("right_ear", [Box(52, 0, -2.5, -9, -1, 2, 5, 1)], (0, 16, -1), (0, -0.2618, 0)),
        Part("left_ear", [Box(58, 0, 0.5, -9, -1, 2, 5, 1)], (0, 16, -1), (0, 0.2618, 0)),
    ), src=(0, 12, -1), dst=(0, -8.7, 0.6), scale=1.8))

    R.append(Rig("ender_dragon_mask", "head", "minecraft:entity/enderdragon/dragon", (256, 256), both(
        Part("head", [
            Box(176, 44, -6, -1, -24, 12, 5, 16),
            Box(112, 30, -8, -8, -10, 16, 16, 16),
            Box(0, 0, -5, -12, -4, 2, 4, 6, mirror=True),
            Box(112, 0, -5, -3, -22, 2, 2, 4, mirror=True),
            Box(0, 0, 3, -12, -4, 2, 4, 6),
            Box(112, 0, 3, -3, -22, 2, 2, 4),
        ], children=[Part("jaw", [Box(176, 65, -6, 0, -16, 12, 4, 16)], (0, 4, -8))]),
    ), src=(0, 0, -2), dst=(0, -5.6, 0.6), scale=0.89))

    R.append(Rig("warden_ears", "head", "minecraft:entity/warden/warden", (128, 128), both(
        Part("right_tendril", [Box(52, 32, -16, -13, 0, 16, 16, 0)], (-8, -12, 0)),
        Part("left_tendril", [Box(58, 0, 0, -13, 0, 16, 16, 0)], (8, -12, 0)),
    ), src=(0, -12, 0), dst=(0, -6.4, 0), scale=0.625))

    # ---------------------------------------------------------------- chestplate: chest & back
    for name, s in (("witch_heart", 0.5), ("iron_golem_heart", 0.55), ("creaking_heart", 0.55),
                    ("warden_heart", 0.55), ("snow_golem_ice_core", 0.5), ("iron_golem_iron_core", 0.55)):
        parts = voxel_parts(item / f"{name}.png", GENERATED_TEX / f"{name}.png")
        R.append(Rig(name, "body", f"{MOD}:entity/attachment/{name}", (64, 64), {"both": parts},
                     src=(0, 0, 0.5), dst=(0, 4.6, -3.05), scale=(s, s, 0.8)))

    R.append(Rig("turtle_shell", "body", "minecraft:entity/turtle/big_sea_turtle", (128, 64), both(
        Part("shell", [Box(7, 37, -9.5, 3, -10, 19, 20, 6)]),
    ), src=(0, 13, -10), dst=(0, 6.2, 3.05), scale=0.55))  # vanilla's south face is the green carapace

    R.append(Rig("allay_wings", "body", "minecraft:entity/allay/allay", (32, 32), both(
        Part("right_wing", [Box(16, 14, 0, 1, 0, 0, 5, 8)], (-0.5, 0, 0.6), (0.43633, -0.7854, 0)),
        Part("left_wing", [Box(16, 14, 0, 1, 0, 0, 5, 8)], (0.5, 0, 0.6), (0.43633, 0.7854, 0)),
    ), src=(0, 0, 0.6), dst=(0, 1.2, 3.1), scale=1.5, translucent=True))

    R.append(Rig("vex_wings", "body", "minecraft:entity/illager/vex", (32, 32), both(
        Part("right_wing", [Box(16, 14, 0, 0, 0, 0, 5, 8)], (-0.5, 1, 1), (0.4712, -0.95, 0)),
        Part("left_wing", [Box(16, 14, 0, 0, 0, 0, 5, 8, mirror=True)], (0.5, 1, 1), (0.4712, 0.95, 0)),
    ), src=(0, 1, 1), dst=(0, 1.4, 3.1), scale=1.5, translucent=True))

    R.append(Rig("shulker_shell", "body", "minecraft:entity/shulker/shulker", (64, 64), both(
        Part("lid", [Box(0, 0, -8, -16, -8, 16, 12, 16)], (0, 24, 0)),
        Part("base", [Box(0, 28, -8, -8, -8, 16, 8, 16)], (0, 24, 0)),
    ), src=(0, 16, -8), dst=(0, 6.0, 3.05), scale=0.5))

    R.append(Rig("phantom_wings", "body", "minecraft:entity/phantom", (64, 64), both(
        Part("left_wing_base", [Box(23, 12, 0, 0, 0, 6, 2, 9)], (2, -2, -8), (0, 0, 0.1), children=[
            Part("left_wing_tip", [Box(16, 24, 0, 0, 0, 13, 1, 9)], (6, 0, 0), (0, 0, 0.1))]),
        Part("right_wing_base", [Box(23, 12, -6, 0, 0, 6, 2, 9, mirror=True)], (-3, -2, -8), (0, 0, -0.1), children=[
            Part("right_wing_tip", [Box(16, 24, -13, 0, 0, 13, 1, 9, mirror=True)], (-6, 0, 0), (0, 0, -0.1))]),
    ), src=(-0.5, 0, -8), dst=(0, 1.0, 3.1), rot_deg=(-90, 0, 0), scale=0.6))

    # ---------------------------------------------------------------- leggings: tail & knees
    R.append(Rig("bee_stinger", "body", "minecraft:entity/bee/bee", (64, 64), both(
        Part("stinger", [Box(26, 7, 0, -1, 5, 0, 1, 2)]),
        Part("stinger_cross", [Box(26, 7, 0, -0.5, 5, 0, 1, 2)], (0, -0.5, 0), (0, 0, deg(90))),
    ), src=(0, -0.5, 5), dst=(0, 10.6, 2.55), scale=3.0))

    R.append(Rig("parrot_tail", "body", "minecraft:entity/parrot/parrot_red_blue", (32, 32), both(
        Part("tail", [Box(22, 1, -1.5, -1, -1, 3, 4, 1)]),
    ), src=(0, -1, -1), dst=(0, 10.2, 2.55), rot_deg=(32, 0, 0), scale=2.2))

    R.append(Rig("camel_tail", "body", "minecraft:entity/camel/camel", (128, 128), both(
        Part("tail", [Box(122, 0, -1.5, 0, 0, 3, 14, 0)]),
    ), src=(0, 0, 0), dst=(0, 10.0, 2.6), rot_deg=(15, 0, 0), scale=0.75))

    R.append(Rig("guardian_spikes", "legs", "minecraft:entity/guardian", (64, 64),
                 both(*spike_ring(0, 0, (0.0, 0.7, -0.7), 4.5)),
                 src=(0, 0, 0), dst=(0, 5.5, -2.5), scale=0.45))

    R.append(Rig("shulker_plates", "legs", "minecraft:entity/shulker/shulker", (64, 64), both(
        Part("lid", [Box(0, 0, -8, -16, -8, 16, 12, 16)]),
    ), src=(0, -4, 0), dst=(0, 6.0, -2.55), rot_deg=(90, 0, 0), scale=(0.3, 0.3, 0.12)))

    R.append(Rig("ravager_hide", "legs", "minecraft:entity/illager/ravager", (128, 128), both(
        Part("hide", [Box(64, 10, -4, 0, -4, 8, 8, 8)]),
    ), src=(0, 4, 4), dst=(0, 6.0, -2.55), scale=(0.62, 0.62, 0.18)))

    R.append(Rig("silverfish_carapace", "legs", "minecraft:entity/silverfish", (64, 32), both(
        Part("knee_plate", [Box(20, 0, -5, 0, -1.5, 10, 8, 3)]),
        Part("shin_plate", [Box(20, 11, -3, 0, -1.5, 6, 4, 3)], (0, 8.5, 0)),
    ), src=(0, 4, 1.5), dst=(0, 5.5, -2.55), scale=0.48))

    R.append(Rig("iron_golem_plate", "legs", "minecraft:entity/iron_golem/iron_golem", (128, 128), both(
        Part("plate", [Box(37, 4, -3, 0, -2.5, 6, 6, 5)]),
    ), src=(0, 3, 2.5), dst=(0, 6.0, -2.55), scale=(0.8, 0.8, 0.28)))

    # ---------------------------------------------------------------- boots: soles & top
    r = 3.0 / 0.45 + 4.5
    R.append(Rig("elder_guardian_spikes", "legs", "minecraft:entity/guardian_elder", (64, 64), {
        "both": spike_ring(0, 0, (0.0, math.pi), r),
        "right": [Part("outer_spike", [Box(0, 0, -1, -4.5, -1, 2, 9, 2)], (-r, 0, 0), (deg(-90), deg(90), 0))],
        "left": [Part("outer_spike", [Box(0, 0, -1, -4.5, -1, 2, 9, 2)], (r, 0, 0), (deg(-90), deg(-90), 0))],
    }, src=(0, 0, 0), dst=(0, 9.5, 0), scale=0.45))

    R.append(Rig("strider_feet", "legs", "minecraft:entity/strider/strider", (64, 128), {
        "right": [Part("foot", [Box(0, 42, -2, 0, -2, 4, 6, 4), Box(0, 32, -2, 6, -2, 4, 0, 4)])],
        "left": [Part("foot", [Box(0, 65, -2, 0, -2, 4, 6, 4), Box(0, 55, -2, 6, -2, 4, 0, 4)])],
    }, src=(0, 6, 0), dst=(0, 13.1, 0), scale=(1.6, 0.9, 1.6)))

    R.append(Rig("rabbit_feet", "legs", "minecraft:entity/rabbit/brown", (64, 32), {
        "right": [Part("foot", [Box(8, 24, -1, 5.5, -3.7, 2, 1, 7)])],
        "left": [Part("foot", [Box(26, 24, -1, 5.5, -3.7, 2, 1, 7)])],
    }, src=(0, 6.5, -0.2), dst=(0, 13.15, -2.2), scale=1.8))

    R.append(Rig("slime", "legs", "minecraft:entity/slime/slime", (64, 32), both(
        Part("outer", [Box(0, 0, -4, 16, -4, 8, 8, 8)]),
        Part("inner", [Box(0, 16, -3, 17, -3, 6, 6, 6), Box(32, 0, -3.25, 18, -3.5, 2, 2, 2),
                       Box(32, 4, 1.25, 18, -3.5, 2, 2, 2), Box(32, 8, 0, 21, -3.5, 1, 1, 1)]),
    ), src=(0, 24, 0), dst=(0, 13.2, -0.4), scale=(0.85, 0.45, 0.95), translucent=True))

    breeze_texture(GENERATED_TEX / "breeze_wind.png")
    R.append(Rig("breeze_wind", "legs", f"{MOD}:entity/attachment/breeze_wind", (64, 64), both(
        Part("low_ring", [Box(0, 0, -3.5, 0, -3.5, 7, 2, 7)], (0, 11, 0), (0, 0.35, 0)),
        Part("mid_ring", [Box(0, 10, -4, 0, -4, 8, 2, 8)], (0, 8.6, 0), (0, -0.4, 0)),
        Part("top_ring", [Box(0, 21, -4.5, 0, -4.5, 9, 2, 9)], (0, 6.2, 0), (0, 0.75, 0)),
    ), src=(0, 0, 0), dst=(0, 0, 0), scale=1.0, translucent=True))

    R.append(Rig("frog_legs", "legs", "minecraft:entity/frog/temperate_frog", (48, 48), {
        "right": [Part("foot", [Box(18, 32, -4, 0.01, -4, 8, 0, 8)]),
                  Part("thigh", [Box(0, 25, -1.5, 0, -2, 3, 3, 4)], (0, -3, 2.6))],
        "left": [Part("foot", [Box(2, 32, -4, 0.01, -4, 8, 0, 8)]),
                 Part("thigh", [Box(14, 25, -1.5, 0, -2, 3, 3, 4)], (0, -3, 2.6))],
    }, src=(0, 0, 0), dst=(0, 13.0, -1.6), scale=1.55))

    R.append(Rig("piglin_hooves", "legs", "minecraft:entity/piglin/piglin", (64, 64), {
        "right": [Part("hoof", [Box(0, 25, -2, 0, -2, 4, 3, 4), Box(0, 16, -2, 3, -2, 4, 0, 4)])],
        "left": [Part("hoof", [Box(16, 57, -2, 0, -2, 4, 3, 4), Box(16, 48, -2, 3, -2, 4, 0, 4)])],
    }, src=(0, 3, 0), dst=(0, 13.1, 0), scale=(1.6, 1.25, 1.6)))

    R.append(Rig("hoglin_hooves", "legs", "minecraft:entity/hoglin/hoglin", (128, 64), {
        "right": [Part("hoof", [Box(66, 52, -3, 0, -3, 6, 4, 6), Box(66, 42, -3, 4, -3, 6, 0, 6)])],
        "left": [Part("hoof", [Box(41, 52, -3, 0, -3, 6, 4, 6), Box(41, 42, -3, 4, -3, 6, 0, 6)])],
    }, src=(0, 4, 0), dst=(0, 13.1, 0), scale=(1.1, 1.0, 1.1)))

    off = 3.0 / 0.45
    R.append(Rig("spider_legs", "legs", "minecraft:entity/spider/spider", (64, 32), {
        "right": [Part("front_leg", [Box(18, 0, -15, -1, -1, 16, 2, 2)], (-off, 0, -1), (0, -0.7854, -0.7854)),
                  Part("hind_leg", [Box(18, 0, -15, -1, -1, 16, 2, 2)], (-off, 0, 1), (0, 0.7854, -0.7854))],
        "left": [Part("front_leg", [Box(18, 0, -1, -1, -1, 16, 2, 2, mirror=True)], (off, 0, -1), (0, 0.7854, 0.7854)),
                 Part("hind_leg", [Box(18, 0, -1, -1, -1, 16, 2, 2, mirror=True)], (off, 0, 1), (0, -0.7854, 0.7854))],
    }, src=(0, 0, 0), dst=(0, 7.6, 0), scale=0.45))

    return R


# ----------------------------------------------------------------------------------------------- Java output

def f(x):
    s = repr(round(float(x), 5))
    return (s[:-2] if s.endswith(".0") else s) + "F"


def java_camel(name):
    head, *rest = name.split("_")
    return head + "".join(p.title() for p in rest)


def java_part(lines, parent, part, var_counter):
    var = f"p{var_counter[0]}"
    var_counter[0] += 1
    cubes = "CubeListBuilder.create()"
    for b in part.boxes:
        grow = f", new CubeDeformation({f(b.grow)})" if b.grow else ""
        cubes += (f".texOffs({b.u}, {b.v}).mirror({'true' if b.mirror else 'false'})"
                  f".addBox({f(b.x)}, {f(b.y)}, {f(b.z)}, {f(b.w)}, {f(b.h)}, {f(b.d)}{grow})")
    px, py, pz = part.pivot
    rx, ry, rz = part.rot
    pose = f"PartPose.offsetAndRotation({f(px)}, {f(py)}, {f(pz)}, {f(rx)}, {f(ry)}, {f(rz)})"
    decl = f"PartDefinition {var} = " if part.children else ""
    lines.append(f"        {decl}{parent}.addOrReplaceChild(\"{part.name}\", {cubes}, {pose});")
    for child in part.children:
        java_part(lines, var, child, var_counter)


def tex_location(path):
    ns, p = path.split(":")
    return f"new ResourceLocation(\"{ns}\", \"textures/{p}.png\")"


def write_java(rigs):
    out = []
    out.append("""package com.nanonaitor.forgedarmor.client;

// GENERATED FILE - edit tools/attachment_rigs.py and run `python tools/attachment_rigs.py` instead.

import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Equipped attachment geometry copied from the source mobs' vanilla 1.20.1 models (identical boxes, UV offsets,
 * texture sizes and mirroring) plus the transform that fits each piece onto the player outside the armour.
 */
public final class AttachmentRigs {
    public enum Anchor { HEAD, BODY, LEGS }

    /** vertex_on_player = dst + scale * rotationZYX(rotation) * (vertex - src), all in model pixels. */
    public record Rig(AttachmentType type, ModelLayerLocation layer, ResourceLocation texture, ResourceLocation glow,
                      boolean translucent, Anchor anchor, float[] src, float[] dst, float[] rotation, float[] scale) {}

    public static final Map<AttachmentType, Rig> RIGS;

    private AttachmentRigs() {}

    private static ModelLayerLocation layer(String id) {
        return new ModelLayerLocation(new ResourceLocation(ForgedArmor.ID, "attachment/" + id), "main");
    }

    private static PartDefinition[] roots(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        return new PartDefinition[]{
                root.addOrReplaceChild("both", CubeListBuilder.create(), PartPose.ZERO),
                root.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.ZERO),
                root.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.ZERO)};
    }
""")
    out.append("    static {")
    out.append("        Map<AttachmentType, Rig> m = new EnumMap<>(AttachmentType.class);")
    for r in rigs:
        rx, ry, rz = (deg(a) for a in r.rot_deg)
        glow = tex_location(r.glow) if r.glow else "null"
        out.append(
            f"        m.put(AttachmentType.{r.type.upper()}, new Rig(AttachmentType.{r.type.upper()}, layer(\"{r.type}\"),\n"
            f"                {tex_location(r.texture)}, {glow}, {'true' if r.translucent else 'false'}, Anchor.{r.anchor.upper()},\n"
            f"                new float[]{{{', '.join(f(x) for x in r.src)}}}, new float[]{{{', '.join(f(x) for x in r.dst)}}},\n"
            f"                new float[]{{{f(rx)}, {f(ry)}, {f(rz)}}}, new float[]{{{', '.join(f(x) for x in r.scale3)}}}));")
    out.append("        RIGS = Collections.unmodifiableMap(m);")
    out.append("    }\n")

    out.append("    public static LayerDefinition create(AttachmentType type) {")
    out.append("        return switch (type) {")
    for r in rigs:
        out.append(f"            case {r.type.upper()} -> {java_camel(r.type)}();")
    out.append("            default -> throw new IllegalArgumentException(\"No equipped model for \" + type);")
    out.append("        };")
    out.append("    }")

    for r in rigs:
        out.append("")
        out.append(f"    private static LayerDefinition {java_camel(r.type)}() {{")
        out.append("        MeshDefinition mesh = new MeshDefinition();")
        out.append("        PartDefinition[] sides = roots(mesh);")
        counter = [0]
        for side, idx in (("both", 0), ("left", 1), ("right", 2)):
            for part in r.parts.get(side, []):
                java_part(out, f"sides[{idx}]", part, counter)
        out.append(f"        return LayerDefinition.create(mesh, {r.tex_size[0]}, {r.tex_size[1]});")
        out.append("    }")
    out.append("}")
    JAVA_OUT.write_text("\n".join(out) + "\n", encoding="utf-8")


# ----------------------------------------------------------------------------------------------- preview output

def part_json(p):
    return {"name": p.name, "pivot": p.pivot, "rot": p.rot,
            "boxes": [[b.u, b.v, b.x, b.y, b.z, b.w, b.h, b.d, b.mirror, b.grow] for b in p.boxes],
            "children": [part_json(c) for c in p.children]}


def write_preview(rigs, vanilla_textures: Path, out: Path):
    def png_uri(path: Path):
        return "data:image/png;base64," + base64.b64encode(path.read_bytes()).decode()

    def tex_file(loc):
        ns, p = loc.split(":")
        if ns == "minecraft":
            return vanilla_textures / f"{p}.png"
        return ASSETS / f"textures/{p}.png"

    textures = {}
    for loc in {r.texture for r in rigs} | {r.glow for r in rigs if r.glow} | {
            "minecraft:entity/player/wide/steve", "minecraft:models/armor/iron_layer_1",
            "minecraft:models/armor/iron_layer_2"}:
        textures[loc] = png_uri(tex_file(loc))
    data = [{"type": r.type, "anchor": r.anchor, "texture": r.texture, "size": r.tex_size, "glow": r.glow,
             "translucent": r.translucent, "src": r.src, "dst": r.dst,
             "rot": [deg(a) for a in r.rot_deg], "scale": r.scale3,
             "parts": {k: [part_json(p) for p in v] for k, v in r.parts.items()}} for r in rigs]
    html = (Path(__file__).with_name("attachment_preview_template.html").read_text(encoding="utf-8")
            .replace("/*RIGS*/null", json.dumps(data)).replace("/*TEXTURES*/null", json.dumps(textures)))
    out.parent.mkdir(parents=True, exist_ok=True)
    out.write_text(html, encoding="utf-8")


if __name__ == "__main__":
    rigs = build_rigs()
    write_java(rigs)
    print(f"wrote {JAVA_OUT.relative_to(ROOT)} with {len(rigs)} rigs")
    if len(sys.argv) >= 2:
        # argv[1]: folder holding extracted vanilla assets/minecraft/textures; argv[2]: preview html output
        tex_root = Path(sys.argv[1])
        out = Path(sys.argv[2]) if len(sys.argv) > 2 else ROOT / "build/attachment_preview.html"
        write_preview(rigs, tex_root, out)
        print(f"wrote preview {out}")
