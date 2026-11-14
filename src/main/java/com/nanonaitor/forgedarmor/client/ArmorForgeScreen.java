package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.ForgedArmor;
import com.nanonaitor.forgedarmor.ArmorForgeMenu;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentSlot;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

/**
 * Armor Forge screen: armor + attachment = forged armor, or armor alone = armor with every attachment removed.
 * The row under the title shows the armor piece's two attachment positions and what currently fills them.
 */
public final class ArmorForgeScreen extends AbstractContainerScreen<ArmorForgeMenu> {
    private static final ResourceLocation TEXTURE = new ResourceLocation(ForgedArmor.ID, "textures/gui/armor_forge.png");
    private static final int CHIP_Y = 19;
    private static final int[] CHIP_X = {8, 92};
    private static final int ARROW_CENTER_X = 113;

    public ArmorForgeScreen(ArmorForgeMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        imageWidth = 176;
        imageHeight = 166;
        titleLabelX = 8;
        titleLabelY = 6;
        inventoryLabelY = 72;
    }

    /** The two attachment positions of the armor in the forge, or none when the armor slot is empty. */
    private List<AttachmentSlot> positions() {
        List<AttachmentSlot> result = new ArrayList<>();
        Equipable equipable = Equipable.get(menu.armor());
        if (equipable == null) return result;
        EquipmentSlot piece = equipable.getEquipmentSlot();
        for (AttachmentSlot slot : AttachmentSlot.values()) if (slot.armorSlot() == piece) result.add(slot);
        return result;
    }

    private int frameX(int index) {
        AttachmentSlot slot = positions().get(index);
        return CHIP_X[index] + font.width(slot.displayName()) + 4;
    }

    @Override
    protected void renderBg(GuiGraphics graphics, float partialTick, int mouseX, int mouseY) {
        graphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);
        ItemStack armor = menu.armor();
        AttachmentType incoming = AttachmentData.attachmentType(menu.attachment());
        boolean removing = menu.isRemoving();
        List<AttachmentSlot> positions = positions();
        for (int i = 0; i < positions.size() && i < CHIP_X.length; i++) {
            AttachmentSlot slot = positions.get(i);
            int x = leftPos + frameX(i), y = topPos + CHIP_Y;
            graphics.blit(TEXTURE, x, y, 176, 0, 18, 18);
            AttachmentType current = AttachmentType.byId(AttachmentData.getId(armor, slot));
            if (current != null) graphics.renderItem(AttachmentData.attachmentStack(current), x + 1, y + 1);
            boolean targeted = incoming != null && incoming.slot() == slot && menu.getSlot(2).hasItem();
            if (targeted && current == null) graphics.blit(TEXTURE, x, y, 176, 34, 18, 18);
            else if ((targeted || removing) && current != null) graphics.blit(TEXTURE, x, y, 194, 34, 18, 18);
        }
        if (!armor.isEmpty() && !menu.attachment().isEmpty() && !menu.getSlot(2).hasItem())
            graphics.blit(TEXTURE, leftPos + ARROW_CENTER_X - 7, topPos + 48, 176, 18, 15, 15);
    }

    @Override
    protected void renderLabels(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderLabels(graphics, mouseX, mouseY);
        List<AttachmentSlot> positions = positions();
        if (positions.isEmpty()) {
            graphics.drawString(font, Component.translatable("gui.nanonaitor_forged_armor.forge.empty"),
                    8, CHIP_Y + 5, 0x707070, false);
        }
        for (int i = 0; i < positions.size() && i < CHIP_X.length; i++)
            graphics.drawString(font, positions.get(i).displayName(), CHIP_X[i], CHIP_Y + 5, 0x404040, false);
        if (menu.isRemoving()) {
            Component remove = Component.translatable("gui.nanonaitor_forged_armor.forge.remove");
            graphics.drawString(font, remove, ARROW_CENTER_X - font.width(remove) / 2, 65, 0x8B5A00, false);
        }
    }

    @Override
    protected void renderTooltip(GuiGraphics graphics, int mouseX, int mouseY) {
        super.renderTooltip(graphics, mouseX, mouseY);
        if (!menu.getCarried().isEmpty() || hoveredSlot != null) return;
        List<AttachmentSlot> positions = positions();
        for (int i = 0; i < positions.size() && i < CHIP_X.length; i++) {
            int x = leftPos + frameX(i), y = topPos + CHIP_Y;
            if (mouseX < x || mouseX >= x + 18 || mouseY < y || mouseY >= y + 18) continue;
            AttachmentType current = AttachmentType.byId(AttachmentData.getId(menu.armor(), positions.get(i)));
            List<Component> lines = new ArrayList<>();
            lines.add(positions.get(i).displayName().copy().append(" position"));
            if (current == null) {
                lines.add(Component.translatable("gui.nanonaitor_forged_armor.forge.free").withStyle(ChatFormatting.GRAY));
            } else {
                lines.add(Component.literal(current.displayName()).withStyle(ChatFormatting.GREEN));
                lines.add(Component.translatable("attachment.nanonaitor_forged_armor." + current.id() + ".description")
                        .withStyle(ChatFormatting.GRAY));
            }
            graphics.renderComponentTooltip(font, lines, mouseX, mouseY);
        }
    }

    @Override public void render(GuiGraphics graphics, int mouseX, int mouseY, float partialTick) {
        renderBackground(graphics);
        super.render(graphics, mouseX, mouseY, partialTick);
        renderTooltip(graphics, mouseX, mouseY);
    }
}
