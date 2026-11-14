package com.nanonaitor.forgedarmor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/** Player-facing description of an attachment, shared by the guide book and the JEI info pages. */
public final class AttachmentInfo {
    private AttachmentInfo() {}

    public static Component armorPiece(AttachmentSlot slot) {
        return Component.translatable("info.nanonaitor_forged_armor.piece." + slot.armorSlot().getName());
    }

    /** "Helmet, Horns position" style line. */
    public static Component fits(AttachmentType type) {
        return Component.translatable("info.nanonaitor_forged_armor.fits", armorPiece(type.slot()), type.slot().displayName());
    }

    public static Component effect(AttachmentType type) {
        return Component.translatable("attachment.nanonaitor_forged_armor." + type.id() + ".description");
    }

    /** Mobs in this game that can drop the attachment; mobs from versions or mods that are absent are left out. */
    public static List<Component> droppers(AttachmentType type) {
        List<Component> names = new ArrayList<>();
        for (String id : type.sources()) {
            ResourceLocation key = ResourceLocation.tryParse(id);
            if (key == null || !ForgeRegistries.ENTITY_TYPES.containsKey(key)) continue;
            names.add(ForgeRegistries.ENTITY_TYPES.getValue(key).getDescription());
        }
        return names;
    }

    /** Where to find the attachment, including the configured drop chance. */
    public static Component obtained(AttachmentType type) {
        if (type == AttachmentType.ENDER_DRAGON_MASK)
            return Component.translatable("info.nanonaitor_forged_armor.source.end_city");
        if (type == AttachmentType.ELYTRA)
            return Component.translatable("info.nanonaitor_forged_armor.source.end_ship");
        List<Component> droppers = droppers(type);
        if (droppers.isEmpty() || !ForgedArmorConfig.SPEC.isLoaded() || !ForgedArmorConfig.ENABLE_MOB_DROPS.get())
            return Component.translatable(type.vanillaItem() != null
                    ? "info.nanonaitor_forged_armor.source.vanilla" : "info.nanonaitor_forged_armor.source.none");
        MutableComponent mobs = Component.empty();
        for (int i = 0; i < droppers.size(); i++) {
            if (i > 0) mobs.append(", ");
            mobs.append(droppers.get(i));
        }
        return Component.translatable("info.nanonaitor_forged_armor.source.mobs", mobs,
                percent(ForgedArmorConfig.baseDropChance(type)), percent(ForgedArmorConfig.LOOTING_BONUS_PER_LEVEL.get()));
    }

    private static String percent(double chance) {
        double value = chance * 100;
        return value == Math.rint(value) ? String.format(Locale.ROOT, "%d%%", (long) value)
                : String.format(Locale.ROOT, "%.1f%%", value);
    }

    /** Every line of the attachment's description, ready to show as a page. */
    public static List<Component> lines(AttachmentType type) {
        List<Component> lines = new ArrayList<>();
        lines.add(Component.literal(type.displayName()).withStyle(ChatFormatting.BOLD));
        lines.add(fits(type).copy().withStyle(ChatFormatting.DARK_BLUE));
        lines.add(effect(type));
        lines.add(obtained(type).copy().withStyle(ChatFormatting.DARK_GRAY));
        if (!type.enabled())
            lines.add(Component.translatable("tooltip.nanonaitor_forged_armor.disabled").withStyle(ChatFormatting.DARK_RED));
        return lines;
    }
}
