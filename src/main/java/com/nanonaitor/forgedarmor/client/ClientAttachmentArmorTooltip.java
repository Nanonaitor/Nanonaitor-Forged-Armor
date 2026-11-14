package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentArmorTooltip;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.tooltip.ClientTooltipComponent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

public final class ClientAttachmentArmorTooltip implements ClientTooltipComponent {
    private final AttachmentArmorTooltip tooltip;

    public ClientAttachmentArmorTooltip(AttachmentArmorTooltip tooltip) {
        this.tooltip = tooltip;
    }

    private Component label() {
        String armor = switch (tooltip.slot().armorSlot()) {
            case HEAD -> "Helmet";
            case CHEST -> "Chestplate";
            case LEGS -> "Leggings";
            case FEET -> "Boots";
            default -> "Armor";
        };
        return Component.literal(armor + "  •  " + tooltip.slot().displayName().getString() + " slot");
    }

    private ItemStack icon() {
        return new ItemStack(switch (tooltip.slot().armorSlot()) {
            case HEAD -> Items.DIAMOND_HELMET;
            case CHEST -> Items.DIAMOND_CHESTPLATE;
            case LEGS -> Items.DIAMOND_LEGGINGS;
            case FEET -> Items.DIAMOND_BOOTS;
            default -> Items.DIAMOND_CHESTPLATE;
        });
    }

    @Override
    public int getHeight() {
        return 18;
    }

    @Override
    public int getWidth(Font font) {
        return 20 + font.width(label());
    }

    @Override
    public void renderImage(Font font, int x, int y, GuiGraphics graphics) {
        graphics.renderItem(icon(), x, y);
        graphics.drawString(font, label(), x + 20, y + 4, 0x55FFFF, true);
    }
}
