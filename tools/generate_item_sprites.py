"""Generate the canonical 16x16 inventory art for Forged Armor attachments."""
from pathlib import Path
from PIL import Image, ImageDraw
import json

T = (0, 0, 0, 0)

def model_json(name):
    return {"parent": "minecraft:item/generated",
            "textures": {"layer0": f"nanonaitor_forged_armor:item/{name}"}}

def save(items: Path, models: Path, name: str, painter, custom_model=None):
    image = Image.new("RGBA", (16, 16), T)
    painter(ImageDraw.Draw(image))
    image.save(items / f"{name}.png")
    (models / f"{name}.json").write_text(json.dumps(custom_model or model_json(name), separators=(",", ":")) + "\n")

def heart(colors, accent=None):
    def paint(d):
        edge, dark, mid, light = colors
        d.polygon([(2,3),(4,2),(7,3),(8,4),(9,3),(12,2),(14,4),(14,7),(12,10),(8,14),(4,10),(2,7)], fill=edge)
        d.polygon([(3,4),(5,3),(7,4),(8,6),(9,4),(11,3),(13,4),(13,7),(11,9),(8,12),(5,9),(3,7)], fill=dark)
        d.polygon([(4,4),(6,4),(8,7),(10,4),(12,4),(12,7),(10,9),(8,11),(6,9),(4,7)], fill=mid)
        d.rectangle((4,4,5,5), fill=light)
        if accent:
            for box, color in accent: d.rectangle(box, fill=color)
    return paint

def paired_wings(colors, pointed=False):
    def paint(d):
        edge, mid, light, shine = colors
        left = [(1,3),(4,3),(4,5),(6,5),(6,8),(8,8),(8,13),(6,13),(6,11),(3,11),(3,9),(1,9)]
        if pointed: left = [(1,2),(4,3),(4,5),(7,6),(8,12),(6,13),(5,10),(2,9)]
        right = [(15-x,y) for x,y in left]
        d.polygon(left, fill=edge); d.polygon(right, fill=edge)
        d.polygon([(2,4),(4,4),(4,6),(6,6),(6,9),(7,9),(7,11),(5,10),(3,9),(2,8)], fill=mid)
        d.polygon([(13,4),(11,4),(11,6),(9,6),(9,9),(8,9),(8,11),(10,10),(12,9),(13,8)], fill=mid)
        d.rectangle((3,5,4,7), fill=light); d.rectangle((11,5,12,7), fill=light)
        d.point((4,5), fill=shine); d.point((11,5), fill=shine)
    return paint

def horn(colors, hoglin=False):
    def paint(d):
        edge, base, mid, tip = colors
        if hoglin:
            d.polygon([(2,12),(3,8),(5,5),(8,3),(12,2),(13,4),(10,6),(8,9),(7,13),(5,15)], fill=edge)
            d.polygon([(3,11),(4,8),(6,6),(9,4),(12,3),(11,5),(9,7),(7,11),(6,14),(5,13)], fill=base)
        else:
            d.polygon([(1,13),(2,8),(5,7),(7,4),(9,1),(12,1),(12,5),(14,5),(14,9),(12,11),(9,11),(6,14),(3,15)], fill=edge)
            d.polygon([(3,12),(3,9),(6,8),(8,5),(9,2),(11,2),(11,6),(13,6),(13,8),(11,10),(8,10),(5,13)], fill=base)
        d.polygon([(7,7),(8,4),(10,2),(11,2),(11,6),(13,6),(12,9),(9,10)], fill=mid)
        d.rectangle((10,2,11,4), fill=tip)
    return paint

def plate(colors, spikes=False):
    def paint(d):
        edge, dark, mid, light = colors
        d.polygon([(3,3),(12,3),(14,5),(14,11),(12,13),(3,13),(1,11),(1,5)], fill=edge)
        d.rectangle((3,5,12,11), fill=dark)
        d.polygon([(4,5),(11,5),(12,7),(10,10),(5,11),(3,8)], fill=mid)
        d.rectangle((4,5,7,6), fill=light)
        if spikes:
            d.polygon([(1,6),(0,4),(2,5),(3,1),(4,4),(7,0),(8,4),(11,1),(12,5),(15,4),(14,7)], fill=edge)
    return paint

def hooves(colors):
    def paint(d):
        edge, mid, light = colors
        d.polygon([(2,3),(7,3),(7,12),(6,14),(2,14),(1,12),(1,5)], fill=edge)
        d.polygon([(9,3),(14,3),(15,5),(15,12),(14,14),(10,14),(9,12)], fill=edge)
        d.rectangle((3,4,6,11), fill=mid); d.rectangle((10,4,13,11), fill=mid)
        d.rectangle((3,4,5,5), fill=light); d.rectangle((10,4,12,5), fill=light)
        d.rectangle((2,12,6,14), fill=(43,35,35,255)); d.rectangle((10,12,14,14), fill=(43,35,35,255))
    return paint

def generate_all(items: Path, models: Path):
    items.mkdir(parents=True, exist_ok=True); models.mkdir(parents=True, exist_ok=True)
    save(items, models, "ravager_horn", horn(((39,36,38,255),(74,70,69,255),(145,139,126,255),(211,203,180,255))))
    def bunny_ears(d):
        d.polygon([(3,13),(3,3),(4,1),(7,1),(7,11),(6,14)], fill=(91,53,40,255)); d.polygon([(9,11),(9,1),(12,1),(13,3),(13,13),(10,14)], fill=(91,53,40,255))
        d.rectangle((4,3,5,10), fill=(224,125,139,255)); d.rectangle((10,3,11,10), fill=(224,125,139,255)); d.rectangle((4,12,6,14), fill=(224,207,184,255)); d.rectangle((9,12,11,14), fill=(224,207,184,255)); d.rectangle((6,13,9,14), fill=(241,229,211,255))
    save(items, models, "bunny_ears", bunny_ears)
    save(items, models, "pillager_nose", lambda d: (d.polygon([(5,2),(11,2),(12,4),(12,12),(10,14),(6,14),(4,12),(4,5)], fill=(71,64,63,255)), d.rectangle((6,4,10,11), fill=(104,94,91,255)), d.rectangle((6,4,7,6), fill=(131,119,114,255))))

    spider_model = {"parent":"minecraft:item/handheld","textures":{"particle":"nanonaitor_forged_armor:item/spider_mask","mask":"nanonaitor_forged_armor:item/spider_mask"},"elements":[{"from":[3,3,3],"to":[13,13,13],"faces":{face:{"uv":[0,0,16,16],"texture":"#mask"} for face in ("north","south","east","west","up","down")}}]}
    def spider(d):
        d.rectangle((1,1,14,14), fill=(35,25,24,255)); d.rectangle((2,2,13,13), fill=(51,36,34,255))
        d.rectangle((3,4,5,6), fill=(181,0,0,255)); d.rectangle((10,4,12,6), fill=(181,0,0,255))
        d.rectangle((4,5,6,7), fill=(247,20,20,255)); d.rectangle((9,5,11,7), fill=(247,20,20,255))
        d.rectangle((2,8,4,9), fill=(109,8,8,255)); d.rectangle((11,8,13,9), fill=(109,8,8,255))
        d.rectangle((5,10,10,12), fill=(19,15,15,255)); d.rectangle((5,12,6,15), fill=(210,201,180,255)); d.rectangle((9,12,10,15), fill=(210,201,180,255))
    save(items, models, "spider_mask", spider, spider_model)
    save(items, models, "hoglin_horn", horn(((75,58,43,255),(165,143,104,255),(221,207,168,255),(246,239,204,255)), True))

    def warden_ears(d):
        dark, mid, cyan = (3,35,43,255),(4,83,94,255),(20,180,186,255)
        d.polygon([(1,2),(5,2),(5,5),(7,5),(7,13),(5,13),(5,9),(3,9),(3,6),(1,6)], fill=dark)
        d.polygon([(14,2),(10,2),(10,5),(8,5),(8,13),(10,13),(10,9),(12,9),(12,6),(14,6)], fill=dark)
        d.rectangle((2,3,4,5), fill=mid); d.rectangle((11,3,13,5), fill=mid); d.rectangle((5,6,6,8), fill=cyan); d.rectangle((9,6,10,8), fill=cyan)
    save(items, models, "warden_ears", warden_ears)
    save(items, models, "witch_heart", heart(((43,18,48,255),(90,35,99,255),(135,63,143,255),(191,104,187,255)), [((6,8,9,11),(116,159,72,255))]))
    save(items, models, "iron_golem_heart", heart(((77,72,68,255),(164,153,145,255),(211,201,194,255),(244,234,228,255)), [((5,5,6,9),(87,132,50,255)),((10,7,11,10),(224,184,42,255))]))
    def shell(d):
        d.polygon([(5,1),(10,1),(10,2),(13,2),(13,4),(15,4),(15,11),(13,11),(13,13),(10,13),(10,15),(5,15),(5,14),(2,14),(2,12),(0,12),(0,5),(2,5),(2,3),(5,3)], fill=(8,71,36,255))
        d.polygon([(5,3),(10,3),(13,5),(13,11),(10,13),(5,13),(2,10),(2,6)], fill=(44,147,56,255))
        d.polygon([(6,4),(9,4),(11,6),(10,8),(5,8),(4,6)], fill=(91,188,75,255)); d.polygon([(5,9),(10,9),(11,11),(9,13),(6,13),(4,11)], fill=(69,169,65,255))
        d.rectangle((7,6,8,11), fill=(18,103,44,255))
    save(items, models, "turtle_shell", shell)
    save(items, models, "creaking_heart", heart(((48,32,25,255),(78,48,35,255),(111,67,41,255),(156,91,41,255)), [((5,5,6,7),(219,84,3,255)),((9,8,11,9),(255,119,4,255))]))
    save(items, models, "allay_wings", paired_wings(((4,105,137,255),(10,162,194,255),(40,218,235,255),(150,250,247,255))))
    save(items, models, "vex_wings", paired_wings(((71,82,97,255),(117,135,153,255),(170,190,202,255),(223,237,238,255)), True))
    save(items, models, "warden_heart", heart(((2,32,39,255),(3,63,74,255),(5,111,120,255),(24,207,207,255)), [((6,8,9,10),(32,230,224,255))]))
    save(items, models, "phantom_wings", paired_wings(((25,30,54,255),(38,54,94,255),(62,75,132,255),(188,181,145,255)), True))

    def core(colors):
        def paint(d):
            edge, dark, mid, light = colors
            d.polygon([(6,1),(10,1),(10,3),(13,3),(13,6),(15,6),(15,10),(13,10),(13,13),(10,13),(10,15),(6,15),(6,13),(3,13),(3,10),(1,10),(1,6),(3,6),(3,3),(6,3)], fill=edge)
            d.polygon([(6,3),(10,3),(13,6),(13,10),(10,13),(6,13),(3,10),(3,6)], fill=dark)
            d.polygon([(7,4),(11,6),(11,9),(8,12),(5,9),(5,6)], fill=mid); d.rectangle((6,5,8,7), fill=light)
        return paint
    save(items, models, "snow_golem_ice_core", core(((82,132,180,255),(116,176,224,255),(170,215,248,255),(229,246,255,255))))
    save(items, models, "iron_golem_iron_core", core(((73,73,73,255),(132,132,132,255),(189,189,189,255),(241,241,241,255))))

    def stinger(d):
        d.polygon([(1,13),(4,9),(7,7),(10,3),(13,2),(12,5),(9,8),(6,10),(3,14)], fill=(74,42,19,255))
        d.polygon([(3,12),(5,9),(8,7),(11,3),(12,3),(10,6),(7,9),(5,11)], fill=(232,184,33,255)); d.rectangle((5,8,7,9), fill=(246,225,126,255))
    save(items, models, "bee_stinger", stinger)
    save(items, models, "guardian_spikes", plate(((40,63,67,255),(72,111,109,255),(102,153,142,255),(191,214,170,255)), True))

    def parrot_tail(d):
        d.polygon([(3,2),(7,2),(7,12),(5,15),(3,14)], fill=(188,12,19,255)); d.polygon([(7,3),(11,3),(11,12),(9,15),(7,13)], fill=(252,203,8,255)); d.polygon([(10,5),(14,5),(14,12),(12,14),(10,12)], fill=(24,80,193,255)); d.rectangle((4,3,6,5), fill=(238,39,30,255))
    save(items, models, "parrot_tail", parrot_tail)
    save(items, models, "shulker_plates", plate(((55,35,63,255),(104,65,113,255),(154,99,163,255),(205,146,210,255))))
    save(items, models, "ravager_hide", plate(((41,38,39,255),(71,67,65,255),(100,94,89,255),(144,135,124,255))))

    def carapace(d):
        d.polygon([(2,5),(4,3),(11,3),(14,6),(14,10),(12,12),(4,12),(1,10)], fill=(54,65,73,255)); d.polygon([(4,4),(10,4),(13,6),(12,9),(9,11),(4,10),(2,8)], fill=(113,130,140,255)); d.rectangle((5,5,9,6), fill=(169,183,188,255)); d.rectangle((2,10,4,13), fill=(66,78,84,255)); d.rectangle((11,10,13,13), fill=(66,78,84,255))
    save(items, models, "silverfish_carapace", carapace)
    save(items, models, "iron_golem_plate", plate(((71,70,67,255),(154,150,142,255),(211,205,194,255),(243,240,229,255)), False))
    save(items, models, "elder_guardian_spikes", plate(((60,54,77,255),(112,106,130,255),(184,180,165,255),(224,220,199,255)), True))

    def feet(colors, split=False):
        def paint(d):
            edge, mid, light = colors
            d.polygon([(2,4),(7,4),(7,12),(6,14),(1,14),(1,10)], fill=edge); d.polygon([(9,4),(14,4),(15,10),(15,14),(10,14),(9,12)], fill=edge)
            d.rectangle((3,5,6,11), fill=mid); d.rectangle((10,5,13,11), fill=mid); d.rectangle((3,5,5,6), fill=light); d.rectangle((10,5,12,6), fill=light)
            if split: d.point((4,13), fill=(33,30,31,255)); d.point((11,13), fill=(33,30,31,255))
        return paint
    save(items, models, "strider_feet", feet(((63,27,33,255),(150,54,57,255),(194,77,72,255))))

    def breeze(d):
        pale, mid, dark = (207,220,246,255),(139,159,211,255),(87,91,151,255)
        d.polygon([(3,3),(9,1),(13,3),(11,5),(5,5)], fill=mid); d.polygon([(1,7),(6,5),(12,6),(15,8),(12,10),(5,9)], fill=pale); d.polygon([(4,11),(11,9),(14,11),(10,14),(5,14),(2,12)], fill=mid); d.rectangle((8,10,11,12), fill=dark)
    save(items, models, "breeze_wind", breeze)

    def frog(d):
        d.polygon([(2,3),(6,3),(7,7),(5,10),(2,14),(0,14),(2,9)], fill=(61,91,32,255)); d.polygon([(10,3),(14,3),(16,9),(15,14),(13,14),(11,10),(9,7)], fill=(61,91,32,255)); d.rectangle((3,4,5,8), fill=(112,138,51,255)); d.rectangle((11,4,13,8), fill=(112,138,51,255)); d.rectangle((1,13,5,15), fill=(171,174,83,255)); d.rectangle((11,13,15,15), fill=(171,174,83,255))
    save(items, models, "frog_legs", frog)
    save(items, models, "piglin_hooves", hooves(((73,39,34,255),(185,112,83,255),(232,160,121,255))))
    save(items, models, "hoglin_hooves", hooves(((60,35,37,255),(139,61,66,255),(188,85,83,255))))

    # Elytra and Shulker Shell intentionally remain vanilla items with vanilla art.
    for orphan in ("elytra_reference", "shulker_shell_reference"):
        (items / f"{orphan}.png").unlink(missing_ok=True)
        (models / f"{orphan}.json").unlink(missing_ok=True)

def make_preview(items: Path, output: Path):
    names = [
        "ravager_horn","pillager_nose","spider_mask","hoglin_horn","bunny_ears","warden_ears",
        "witch_heart","iron_golem_heart","turtle_shell","creaking_heart","allay_wings","vex_wings",
        "warden_heart","phantom_wings","snow_golem_ice_core","iron_golem_iron_core","bee_stinger",
        "guardian_spikes","parrot_tail","shulker_plates","ravager_hide","silverfish_carapace",
        "iron_golem_plate","elder_guardian_spikes","strider_feet","breeze_wind","frog_legs",
        "piglin_hooves","hoglin_hooves"
    ]
    scale, columns = 8, 6
    preview = Image.new("RGBA", (columns * 16 * scale, ((len(names)+columns-1)//columns) * 16 * scale), (22,22,22,255))
    for i, name in enumerate(names):
        icon = Image.open(items / f"{name}.png").resize((16*scale,16*scale), Image.Resampling.NEAREST)
        preview.alpha_composite(icon, ((i % columns)*16*scale, (i // columns)*16*scale))
    output.parent.mkdir(parents=True, exist_ok=True)
    preview.save(output)

if __name__ == "__main__":
    root = Path(__file__).resolve().parents[1]
    generate_all(root / "src/main/resources/assets/nanonaitor_forged_armor/textures/item",
                 root / "src/main/resources/assets/nanonaitor_forged_armor/models/item")
    make_preview(root / "src/main/resources/assets/nanonaitor_forged_armor/textures/item",
                 root / "build/previews/attachment_item_sprites.png")
    print("Generated canonical 16x16 Forged Armor item sprites.")
