from pathlib import Path
from PIL import Image, ImageDraw
import json

ROOT = Path(__file__).resolve().parents[1]
TEX = ROOT / "src/main/resources/assets/nanonaitor_forged_armor/textures"
ITEMS = TEX / "item"
ENTITY = TEX / "entity/attachment"
MODELS = ROOT / "src/main/resources/assets/nanonaitor_forged_armor/models/item"
BB = ROOT / "models/blockbench"
for folder in (ITEMS, ENTITY, MODELS, BB):
    folder.mkdir(parents=True, exist_ok=True)

def sprite(name, painter):
    image = Image.new("RGBA", (32, 32), (0, 0, 0, 0))
    painter(ImageDraw.Draw(image))
    image.save(ITEMS / f"{name}.png")
    (MODELS / f"{name}.json").write_text(json.dumps({
        "parent": "minecraft:item/generated",
        "textures": {"layer0": f"nanonaitor_forged_armor:item/{name}"}
    }, separators=(",", ":")) + "\n")

def ravager_horn(d):
    # Heavy black root and a thick, stair-stepped horn sweeping upward.
    d.polygon([(1,29),(1,20),(4,16),(9,15),(12,11),(16,8),(18,3),(22,1),(24,2),
               (24,6),(27,6),(27,11),(29,11),(29,17),(26,20),(22,20),(19,23),(14,23),
               (10,27),(6,30)], fill=(42,39,41,255))
    d.polygon([(3,27),(3,21),(6,18),(11,18),(14,14),(18,11),(20,6),(23,4),(23,10),
               (26,10),(26,15),(23,18),(20,18),(17,21),(12,21),(8,25),(6,28)], fill=(77,73,71,255))
    d.polygon([(13,18),(16,13),(20,11),(20,6),(23,4),(23,11),(26,11),(26,15),(23,18),
               (20,18),(18,21)], fill=(157,151,136,255))
    d.polygon([(18,12),(20,8),(20,4),(23,2),(23,9),(26,9),(26,13),(24,15),(21,15)], fill=(211,202,181,255))
    d.rectangle((4,21,8,27), fill=(25,22,25,255))
    d.rectangle((5,19,10,21), fill=(54,50,54,255))
    d.rectangle((9,16,13,18), fill=(103,98,91,255))

def bunny_ears(d):
    dark, brown, light, pink, highlight = (91,48,33,255),(132,75,51,255),(181,125,94,255),(225,117,133,255),(247,163,166,255)
    d.polygon([(4,26),(4,6),(6,6),(6,2),(11,2),(11,4),(13,4),(13,24),(12,28),(8,30)], fill=dark)
    d.polygon([(19,24),(19,4),(21,4),(21,2),(26,2),(26,6),(28,6),(28,26),(24,30),(20,28)], fill=dark)
    d.rectangle((6,5,11,24), fill=brown); d.rectangle((21,5,26,24), fill=brown)
    d.rectangle((7,3,10,6), fill=light); d.rectangle((22,3,25,6), fill=light)
    d.polygon([(8,7),(10,7),(10,22),(9,25),(7,22),(7,11)], fill=pink)
    d.polygon([(22,7),(24,7),(25,11),(25,22),(23,25),(22,22)], fill=pink)
    d.rectangle((8,12,10,19), fill=highlight); d.rectangle((22,12,24,19), fill=highlight)
    d.rectangle((7,25,12,29), fill=(222,203,179,255)); d.rectangle((20,25,25,29), fill=(222,203,179,255))
    d.rectangle((11,26,21,28), fill=(240,225,205,255))

def allay_wings(d):
    outline, mid, cyan, shine = (7,118,154,255),(15,165,197,255),(44,218,235,255),(147,249,246,255)
    left = [(1,7),(5,7),(5,9),(9,9),(9,12),(12,12),(12,16),(15,16),(15,28),(11,28),(11,25),(7,25),(7,22),(3,22),(3,18),(1,18)]
    right = [(31-x,y) for x,y in left]
    d.polygon(left, fill=outline); d.polygon(right, fill=outline)
    d.polygon([(3,9),(6,9),(6,11),(10,11),(10,14),(13,14),(13,25),(10,25),(10,22),(6,22),(6,19),(3,19)], fill=mid)
    d.polygon([(29,9),(26,9),(26,11),(22,11),(22,14),(19,14),(19,25),(22,25),(22,22),(26,22),(26,19),(29,19)], fill=mid)
    d.polygon([(5,11),(8,11),(8,14),(11,14),(11,18),(13,18),(13,23),(10,23),(10,20),(6,20),(6,17),(4,17)], fill=cyan)
    d.polygon([(27,11),(24,11),(24,14),(21,14),(21,18),(19,18),(19,23),(22,23),(22,20),(26,20),(26,17),(28,17)], fill=cyan)
    d.rectangle((6,12,9,15), fill=shine); d.rectangle((23,12,26,15), fill=shine)
    d.rectangle((11,18,13,20), fill=shine); d.rectangle((19,18,21,20), fill=shine)

def turtle_shell(d):
    edge, dark, mid, light = (7,73,37,255),(15,103,43,255),(44,151,56,255),(89,190,72,255)
    d.polygon([(10,1),(22,1),(22,3),(26,3),(26,5),(29,5),(29,9),(31,9),(31,23),(29,23),(29,27),(25,27),(25,30),(7,30),(7,28),(3,28),(3,25),(1,25),(1,8),(3,8),(3,5),(7,5),(7,3),(10,3)], fill=edge)
    d.polygon([(10,3),(22,3),(22,5),(26,5),(26,8),(29,8),(29,23),(26,23),(26,27),(22,27),(22,29),(10,29),(10,27),(6,27),(6,24),(3,24),(3,9),(6,9),(6,6),(10,6)], fill=dark)
    d.polygon([(11,5),(21,5),(24,8),(27,11),(27,21),(24,24),(21,27),(11,27),(8,24),(5,21),(5,11),(8,8)], fill=mid)
    d.polygon([(12,7),(20,7),(23,10),(21,13),(18,12),(14,12),(11,13),(9,10)], fill=light)
    d.polygon([(7,12),(11,14),(11,19),(8,22),(6,20),(6,14)], fill=light)
    d.polygon([(25,12),(21,14),(21,19),(24,22),(26,20),(26,14)], fill=light)
    d.polygon([(13,14),(19,14),(21,18),(19,23),(13,23),(11,18)], fill=(56,169,62,255))
    d.polygon([(11,24),(14,22),(18,22),(21,24),(20,27),(12,27)], fill=light)

sprite("ravager_horn", ravager_horn)
sprite("bunny_ears", bunny_ears)
sprite("allay_wings", allay_wings)
sprite("turtle_shell", turtle_shell)

def texture(name, palette):
    image = Image.new("RGBA", (64, 64), palette[0])
    draw = ImageDraw.Draw(image)
    # Vanilla-style hard rectangular color fields; no filtering or antialiasing.
    for i, color in enumerate(palette[1:]):
        inset = 4 + i * 5
        draw.rectangle((inset, inset, 63 - inset, 63 - inset), fill=color)
    image.save(ENTITY / f"{name}.png")

texture("ravager_horn", [(43,41,39,255),(82,79,73,255),(121,117,106,255),(166,161,145,255)])
texture("bunny_ears", [(92,72,61,255),(174,142,119,255),(219,155,157,255),(238,184,181,255)])
texture("allay_wings", [(18,121,151,210),(31,164,193,210),(51,201,230,215),(151,247,247,225)])
texture("turtle_shell", [(17,83,48,255),(29,116,55,255),(54,146,66,255),(101,190,88,255)])

def cube(name, frm, to, uv=(0, 0), rotation=None, origin=None):
    uid = f"{name:0<36}"[:36]
    faces = {face: {"uv": [uv[0], uv[1], uv[0] + 8, uv[1] + 8], "texture": 0}
             for face in ("north", "east", "south", "west", "up", "down")}
    out = {"name": name, "box_uv": False, "rescale": False, "locked": False,
           "from": frm, "to": to, "autouv": 0, "color": 0, "origin": origin or [8,8,8],
           "faces": faces, "type": "cube", "uuid": uid}
    if rotation: out["rotation"] = rotation
    return out

def bbmodel(name, slot, elements):
    data = {
        "meta": {"format_version": "4.10", "model_format": "free", "box_uv": False},
        "name": name.replace("_", " ").title(),
        "resolution": {"width": 64, "height": 64},
        "elements": elements,
        "outliner": [{"name": slot, "origin": [8,8,8], "color": 0,
                      "children": [e["uuid"] for e in elements], "uuid": f"{slot:0<36}"[:36]}],
        "textures": [{"path": f"../../src/main/resources/assets/nanonaitor_forged_armor/textures/entity/attachment/{name}.png",
                      "name": f"{name}.png", "folder": "attachment", "namespace": "nanonaitor_forged_armor",
                      "id": "0", "particle": False, "render_mode": "default", "visible": True,
                      "mode": "bitmap", "saved": True, "uuid": f"texture_{name:0<28}"[:36]}]
    }
    (BB / f"{name}.bbmodel").write_text(json.dumps(data, indent=2) + "\n")

bbmodel("ravager_horn", "head_horns_anchor", [
    cube("left_base", [2,7,6], [5,10,10]), cube("left_mid", [0,4,6.5], [2.5,8,9.5]), cube("left_tip", [-1,1.5,7], [.5,5,9]),
    cube("right_base", [11,7,6], [14,10,10]), cube("right_mid", [13.5,4,6.5], [16,8,9.5]), cube("right_tip", [15.5,1.5,7], [17,5,9])])
bbmodel("bunny_ears", "head_horns_anchor", [
    cube("left_outer", [9,0,6.5], [12,8,9.5]), cube("left_inner", [9.8,.8,6.3], [11.2,6.8,6.7], (16,0)),
    cube("right_outer", [4,0,6.5], [7,8,9.5]), cube("right_inner", [4.8,.8,6.3], [6.2,6.8,6.7], (16,0))])
bbmodel("allay_wings", "body_back_anchor", [
    cube("left_upper", [11,4,9], [18,9,10]), cube("left_lower", [12,8,9], [17.5,13,10], (0,12)),
    cube("right_upper", [-2,4,9], [5,9,10]), cube("right_lower", [-1.5,8,9], [4,13,10], (0,12))])
bbmodel("turtle_shell", "body_back_anchor", [
    cube("outer_shell", [3,3,9], [13,13,12]), cube("center_plate", [4.8,4.8,11.8], [11.2,11.2,13], (0,26)),
    cube("top_ridge", [5.2,2.5,10], [10.8,3.8,12]), cube("bottom_ridge", [5.2,12.2,10], [10.8,13.5,12])])

# The final item pass deliberately overwrites the early concept icons above with
# canonical 16x16 art for every attachment. Geometry and entity textures remain
# generated by this file so one command still refreshes all visual assets.
from generate_item_sprites import generate_all
generate_all(ITEMS, MODELS)

print("Generated attachment models/textures and canonical 16x16 item sprites.")
