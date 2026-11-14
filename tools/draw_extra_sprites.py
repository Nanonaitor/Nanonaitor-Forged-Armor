"""Hand-drawn 16x16 sprites for items that had no reference art: Bunny Ears, Camel Tail and the guide book.

Run: python tools/draw_extra_sprites.py
"""
from pathlib import Path
from PIL import Image

OUT = Path(__file__).resolve().parent.parent / "src/main/resources/assets/nanonaitor_forged_armor/textures/item"

SPRITES = {
    "bunny_ears": ({"o": (61, 42, 29), "L": (176, 132, 92), "F": (139, 98, 64), "D": (104, 72, 46),
                    "P": (236, 166, 172), "p": (201, 118, 132)}, [
        "................",
        "...ooo....ooo...",
        "..oLFFo..oFFLo..",
        "..oLPFo..oFPLo..",
        "..oLPFo..oFPLo..",
        "..oLpFo..oFpLo..",
        "..oLpFo..oFpLo..",
        "..oLpFo..oFpLo..",
        "..oLpFo..oFpLo..",
        "..oFpDo..oDpFo..",
        "..oFFDo..oDFFo..",
        ".ooFFDooooDFFoo.",
        ".oLLFFFFFFFFFDo.",
        ".oFFFFFFFFFFDDo.",
        "..oooooooooooo..",
        "................"]),
    "camel_tail": ({"o": (70, 48, 30), "L": (226, 180, 118), "M": (190, 140, 82), "D": (150, 105, 58),
                    "T": (98, 68, 42), "t": (66, 45, 28)}, [
        "................",
        "...........ooo..",
        "..........oLMo..",
        ".........oLMDo..",
        ".........oLMo...",
        "........oLMDo...",
        "........oLMo....",
        ".......oLMDo....",
        ".......oLMo.....",
        "......oLMDo.....",
        ".....oTTTTo.....",
        "....oTtTtTTo....",
        "....oTTtTTTo....",
        "....otTTtTo.....",
        ".....ottTo......",
        "......ooo......."]),
    "forged_armor_guide": ({"o": (24, 40, 26), "G": (66, 142, 74), "g": (40, 96, 48), "Y": (240, 200, 60),
                            "W": (236, 230, 214), "w": (198, 188, 168)}, [
        "................",
        "...ooooooooooo..",
        "..oGGGGGGGGGGGo.",
        "..ogGGGGGGGGGGo.",
        "..ogGGYYYYYGGGo.",
        "..ogGYYYYYYYGGo.",
        "..ogGGGGYGGGGGo.",
        "..ogGGGYYYGGGGo.",
        "..ogGGYYYYYGGGo.",
        "..ogGGGGGGGGGGo.",
        "..ogGGGGGGGGGGo.",
        "..ogGGGGGGGGGGo.",
        "..ogWWWWWWWWWWo.",
        "..ogwwwwwwwwwwo.",
        "...ooooooooooo..",
        "................"]),
}

for name, (palette, rows) in SPRITES.items():
    assert len(rows) == 16 and all(len(r) == 16 for r in rows), name
    img = Image.new("RGBA", (16, 16), (0, 0, 0, 0))
    for y, row in enumerate(rows):
        for x, ch in enumerate(row):
            if ch != ".":
                img.putpixel((x, y), palette[ch] + (255,))
    img.save(OUT / f"{name}.png")
    print("wrote", name)
