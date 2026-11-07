package com.nanonaitor.forgedarmor;

import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.network.chat.Component;

public enum AttachmentSlot {
    HORNS(EquipmentSlot.HEAD), FACE(EquipmentSlot.HEAD),
    CHEST(EquipmentSlot.CHEST), BACK(EquipmentSlot.CHEST),
    KNEES(EquipmentSlot.LEGS), TAIL(EquipmentSlot.LEGS),
    SOLES(EquipmentSlot.FEET), TOP(EquipmentSlot.FEET);

    private final EquipmentSlot armorSlot;
    AttachmentSlot(EquipmentSlot armorSlot) { this.armorSlot = armorSlot; }
    public EquipmentSlot armorSlot() { return armorSlot; }
    public String key() { return name().toLowerCase(); }
    public Component displayName() {
        String lower = key();
        return Component.literal(Character.toUpperCase(lower.charAt(0)) + lower.substring(1));
    }
}
