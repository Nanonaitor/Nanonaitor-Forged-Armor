package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.ArmorForgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;

public final class ArmorForgeScreen extends AbstractContainerScreen<ArmorForgeMenu> {
    public ArmorForgeScreen(ArmorForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
        inventoryLabelY = 72;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        int x = leftPos;
        int y = topPos;
        graphics.fill(x, y, x + imageWidth, y + imageHeight, 0xFF245A3A);
        graphics.fill(x + 4, y + 4, x + imageWidth - 4, y + imageHeight - 4, 0xFFC6C6C6);
        slotBox(graphics, x + 43, y + 34);
        slotBox(graphics, x + 79, y + 34);
        slotBox(graphics, x + 133, y + 34);
        graphics.drawString(font, "+", x + 67, y + 39, 0x404040, false);
        graphics.drawString(font, "→", x + 108, y + 39, 0x245A3A, false);
    }

    private static void slotBox(GuiGraphics graphics, int x, int y) {
        graphics.fill(x - 1, y - 1, x + 18, y + 18, 0xFF373737);
        graphics.fill(x, y, x + 17, y + 17, 0xFF8B8B8B);
    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
