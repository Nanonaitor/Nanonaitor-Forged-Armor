"""Draw the Armor Forge screen texture (textures/gui/armor_forge.png) in the vanilla container style.

Layout (176x166 panel at 0,0):
  armor slot (27,47), attachment slot (76,47), result slot (134,47), player inventory from (8,84), hotbar (8,142)
Sprites to the right of the panel, used by ArmorForgeScreen:
  (176,0)  18x18 empty slot frame for the attachment-position chips
  (176,18) 15x15 red cross drawn over the arrow when the inputs do not combine
  (176,34) 18x18 green chip border (position is free)
  (194,34) 18x18 gold chip border (position is taken and will be replaced or removed)

Run: python tools/generate_gui_texture.py
"""
from pathlib import Path
from PIL import Image

OUT = Path(__file__).resolve().parent.parent / "src/main/resources/assets/nanonaitor_forged_armor/textures/gui/armor_forge.png"

BLACK = (0, 0, 0, 255)
PANEL = (198, 198, 198, 255)
LIGHT = (255, 255, 255, 255)
SHADE = (85, 85, 85, 255)
SLOT_DARK = (55, 55, 55, 255)
SLOT_FILL = (139, 139, 139, 255)


def rect(img, x0, y0, x1, y1, color):
    for x in range(x0, x1):
        for y in range(y0, y1):
            img.putpixel((x, y), color)


def panel(img, w, h):
    rect(img, 2, 2, w - 2, h - 2, PANEL)
    rect(img, 2, 0, w - 3, 1, BLACK)          # outline, with the corners left open like vanilla
    rect(img, 0, 2, 1, h - 3, BLACK)
    rect(img, 3, h - 1, w - 2, h, BLACK)
    rect(img, w - 1, 3, w, h - 2, BLACK)
    img.putpixel((1, 1), BLACK); img.putpixel((w - 3, 1), BLACK); img.putpixel((w - 2, 2), BLACK)
    img.putpixel((1, h - 3), BLACK); img.putpixel((2, h - 2), BLACK); img.putpixel((w - 2, h - 2), BLACK)
    rect(img, 1, 2, 3, h - 3, LIGHT); rect(img, 2, 1, w - 3, 3, LIGHT)        # top-left highlight
    img.putpixel((3, 3), LIGHT)
    rect(img, w - 3, 3, w - 1, h - 2, SHADE); rect(img, 3, h - 3, w - 2, h - 1, SHADE)  # bottom-right shade
    img.putpixel((w - 4, h - 4), SHADE)
    img.putpixel((w - 3, 2), PANEL); img.putpixel((2, h - 3), PANEL)


def slot(img, x, y):
    """18x18 slot frame whose 16x16 item area starts at (x+1, y+1)."""
    rect(img, x, y, x + 17, y + 1, SLOT_DARK); rect(img, x, y, x + 1, y + 17, SLOT_DARK)
    rect(img, x + 1, y + 17, x + 18, y + 18, LIGHT); rect(img, x + 17, y + 1, x + 18, y + 18, LIGHT)
    rect(img, x + 1, y + 1, x + 17, y + 17, SLOT_FILL)
    img.putpixel((x + 17, y), SLOT_FILL); img.putpixel((x, y + 17), SLOT_FILL)


def border(img, x, y, color, dark):
    for i in range(18):
        for j in range(18):
            edge = i in (0, 17) or j in (0, 17)
            inner = i in (1, 16) or j in (1, 16)
            if edge: img.putpixel((x + i, y + j), dark)
            elif inner: img.putpixel((x + i, y + j), color)


def main():
    img = Image.new("RGBA", (256, 256), (0, 0, 0, 0))
    panel(img, 176, 166)
    for x in (26, 75, 133):
        slot(img, x, 46)
    for row in range(3):
        for col in range(9):
            slot(img, 7 + col * 18, 83 + row * 18)
    for col in range(9):
        slot(img, 7 + col * 18, 141)

    # plus between armor and attachment, arrow between attachment and result (vanilla anvil proportions)
    rect(img, 53, 54, 66, 57, SLOT_FILL); rect(img, 58, 49, 61, 62, SLOT_FILL)
    rect(img, 101, 54, 119, 57, SLOT_FILL)
    for i in range(7):
        rect(img, 118 + i, 49 + i, 119 + i, 62 - i, SLOT_FILL)

    # sprites
    slot(img, 176, 0)
    red, dark_red = (255, 64, 64, 255), (120, 20, 20, 255)
    for i in range(15):
        for d, c in ((0, red), (1, dark_red)):
            if i + d < 15:
                img.putpixel((176 + i, 18 + i + d), c)
                img.putpixel((176 + 14 - i, 18 + i + d), c)
    border(img, 176, 34, (96, 220, 96, 255), (24, 96, 24, 255))
    border(img, 194, 34, (255, 200, 64, 255), (128, 88, 0, 255))

    OUT.parent.mkdir(parents=True, exist_ok=True)
    img.save(OUT)
    print("wrote", OUT)


if __name__ == "__main__":
    main()
