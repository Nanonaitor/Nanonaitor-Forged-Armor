package com.nanonaitor.forgedarmor;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Equipable;

import java.util.EnumSet;
import java.util.Set;

public final class AttachmentData {
    private static final String ROOT = "NanonaitorForgedArmor";

    private AttachmentData() {}

    public static boolean armorAccepts(ItemStack armor, AttachmentType type) {
        Equipable equipable = Equipable.get(armor);
        return equipable != null && equipable.getEquipmentSlot() == type.slot().armorSlot();
    }

    public static boolean has(ItemStack armor, AttachmentType type) {
        return type.id().equals(getId(armor, type.slot()));
    }

    public static boolean hasSlot(ItemStack armor, AttachmentSlot slot) {
        return !getId(armor, slot).isEmpty();
    }

    public static String getId(ItemStack armor, AttachmentSlot slot) {
        CompoundTag tag = armor.getTag();
        return tag == null || !tag.contains(ROOT) ? "" : tag.getCompound(ROOT).getString(slot.key());
    }

    public static ItemStack attach(ItemStack armor, AttachmentType type) {
        ItemStack result = armor.copy();
        result.setCount(1);
        result.getOrCreateTagElement(ROOT).putString(type.slot().key(), type.id());
        return result;
    }

    public static AttachmentType attachmentType(ItemStack stack) {
        if (stack.getItem() instanceof ArmorAttachmentItem item) return item.type();
        for (AttachmentType type : AttachmentType.values())
            if (type.vanillaItem() != null && stack.is(type.vanillaItem())) return type;
        return null;
    }

    public static ItemStack attachmentStack(AttachmentType type) {
        if (type.vanillaItem() != null) return new ItemStack(type.vanillaItem());
        return new ItemStack(ForgedArmor.ATTACHMENTS.get(type).get());
    }

    /** Matches vanilla Elytra behavior: flight stops before the equipped item breaks. */
    public static boolean canFlyWith(ItemStack chest) {
        return has(chest, AttachmentType.ELYTRA) && chest.isDamageableItem()
                && chest.getDamageValue() < chest.getMaxDamage() - 1;
    }

    public static Set<AttachmentType> equipped(net.minecraft.world.entity.LivingEntity entity) {
        Set<AttachmentType> result = EnumSet.noneOf(AttachmentType.class);
        for (EquipmentSlot slot : new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET}) {
            ItemStack armor = entity.getItemBySlot(slot);
            for (AttachmentSlot attachmentSlot : AttachmentSlot.values()) {
                if (attachmentSlot.armorSlot() != slot) continue;
                AttachmentType type = AttachmentType.byId(getId(armor, attachmentSlot));
                if (type != null) result.add(type);
            }
        }
        return result;
    }
}
