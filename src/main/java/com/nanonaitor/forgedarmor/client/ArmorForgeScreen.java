package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.ArmorForgeMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public final class ArmorForgeScreen extends AbstractContainerScreen<ArmorForgeMenu> {
    private static final ResourceLocation ANVIL_TEXTURE =
            new ResourceLocation("minecraft", "textures/gui/container/anvil.png");

    public ArmorForgeScreen(ArmorForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
        titleLabelX = 62;
        titleLabelY = 24;
        inventoryLabelY = 72;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(ANVIL_TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        // The vanilla anvil background has a red placeholder where AnvilScreen draws its rename box.
        // The forge has no rename box, so paint that area with the panel colour and put the title there.
        graphics.fill(leftPos + 59, topPos + 20, leftPos + 169, topPos + 36, 0xFFC6C6C6);
    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
