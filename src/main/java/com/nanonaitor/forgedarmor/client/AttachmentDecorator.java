package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.IItemDecorator;
import net.minecraftforge.client.event.RegisterItemDecorationsEvent;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;

/** Draws one small gem per attachment in the top-left corner of forged armor in any inventory slot. */
public final class AttachmentDecorator implements IItemDecorator {
    private static final AttachmentDecorator INSTANCE = new AttachmentDecorator();

    public static void register(RegisterItemDecorationsEvent event) {
        for (Item item : ForgeRegistries.ITEMS) {
            if (item instanceof Equipable equipable && equipable.getEquipmentSlot().getType() == EquipmentSlot.Type.ARMOR)
                event.register(item, INSTANCE);
        }
    }

    @Override
    public boolean render(GuiGraphics graphics, Font font, ItemStack stack, int x, int y) {
        List<AttachmentType> attached = AttachmentData.attached(stack);
        if (attached.isEmpty()) return false;
        graphics.pose().pushPose();
        graphics.pose().translate(0, 0, 200);
        for (int i = 0; i < attached.size(); i++) {
            int gx = x + 1 + i * 4, gy = y + 1;
            int color = attached.get(i).enabled() ? 0xFF55FF55 : 0xFF777777;
            graphics.fill(gx, gy, gx + 3, gy + 3, 0xFF103010);
            graphics.fill(gx, gy, gx + 2, gy + 2, color);
        }
        graphics.pose().popPose();
        return false;
    }
}
