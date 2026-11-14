package com.nanonaitor.forgedarmor;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.List;

public final class ForgedArmorConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLE_MOB_DROPS;
    public static final ForgeConfigSpec.DoubleValue BASE_DROP_CHANCE;
    public static final ForgeConfigSpec.DoubleValue LOOTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.DoubleValue LUCK_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DROP_CHANCE_OVERRIDES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> DISABLED_ATTACHMENTS;
    public static final ForgeConfigSpec.DoubleValue MELEE_BONUS;
    public static final ForgeConfigSpec.DoubleValue MAGIC_BONUS;
    public static final ForgeConfigSpec.DoubleValue RANGED_BONUS;
    public static final ForgeConfigSpec.DoubleValue PROJECTILE_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue ARTHROPOD_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static final ForgeConfigSpec.DoubleValue KNOCKBACK_DEALT;
    public static final ForgeConfigSpec.DoubleValue SPEED_BONUS;
    public static final ForgeConfigSpec.DoubleValue IRON_GOLEM_PLATE_ARMOR;
    public static final ForgeConfigSpec.IntValue BEE_STINGER_POISON_SECONDS;
    public static final ForgeConfigSpec.DoubleValue GUARDIAN_SPIKES_DAMAGE;
    public static final ForgeConfigSpec.DoubleValue TURTLE_SHELL_SHIELD_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue JUMP_BOOST;
    public static final ForgeConfigSpec.DoubleValue FROG_SWIM_BOOST;
    public static final ForgeConfigSpec.DoubleValue DOUBLE_JUMP_VELOCITY;
    public static final ForgeConfigSpec.DoubleValue BREEZE_DASH_STRENGTH;
    public static final ForgeConfigSpec.IntValue BREEZE_DASH_COOLDOWN_TICKS;
    public static final ForgeConfigSpec.IntValue PERIODIC_COOLDOWN_SECONDS;
    public static final ForgeConfigSpec.IntValue WARDEN_EARS_RANGE;
    public static final ForgeConfigSpec.BooleanValue ENABLE_END_SHIP_MASK;
    public static final ForgeConfigSpec.DoubleValue END_SHIP_MASK_CHANCE;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();
        b.push("drops");
        ENABLE_MOB_DROPS = b.comment("Enable attachment drops from configured mobs.").define("enableMobDrops", true);
        BASE_DROP_CHANCE = b.comment("Base chance for each eligible attachment to drop.").defineInRange("baseDropChance", 0.05, 0.0, 1.0);
        LOOTING_BONUS_PER_LEVEL = b.comment("Extra drop chance per Looting level.").defineInRange("lootingBonusPerLevel", 0.04, 0.0, 1.0);
        LUCK_BONUS_PER_LEVEL = b.comment("Extra drop chance per level of the Luck status effect.").defineInRange("luckBonusPerLevel", 0.04, 0.0, 1.0);
        DROP_CHANCE_OVERRIDES = b.comment("Per-attachment base drop chances that replace baseDropChance, as \"attachment_id=chance\".",
                        "Example: [\"warden_heart=0.25\", \"bee_stinger=0.02\"]")
                .defineList("dropChanceOverrides", List.of(), ForgedArmorConfig::validOverride);
        ENABLE_END_SHIP_MASK = b.comment("Allow Dragon Heads in End City treasure chests as Face attachments.").define("enableEndShipMask", true);
        END_SHIP_MASK_CHANCE = b.comment("Chance per End City treasure chest to add an attachable Dragon Head.").defineInRange("endShipMaskChance", 0.05, 0.0, 1.0);
        b.pop();

        b.push("attachments");
        DISABLED_ATTACHMENTS = b.comment("Attachment ids that cannot drop, be forged or take effect, e.g. [\"breeze_wind\", \"elytra\"].",
                        "Armor that already carries a disabled attachment keeps it but loses its effect.")
                .defineList("disabledAttachments", List.of(), o -> o instanceof String s && AttachmentType.byId(s) != null);
        b.pop();

        b.push("abilities");
        MELEE_BONUS = b.comment("Ravager Horn: extra melee damage multiplier.").defineInRange("ravagerHornMeleeBonus", 0.20, 0.0, 10.0);
        MAGIC_BONUS = b.comment("Goat Horn: extra magic damage multiplier.").defineInRange("goatHornMagicBonus", 0.20, 0.0, 10.0);
        RANGED_BONUS = b.comment("Hoglin Horn: extra projectile damage multiplier.").defineInRange("hoglinHornRangedBonus", 0.20, 0.0, 10.0);
        PROJECTILE_REDUCTION = b.comment("Shulker Plates: projectile damage reduction.").defineInRange("shulkerPlatesProjectileReduction", 0.25, 0.0, 1.0);
        ARTHROPOD_REDUCTION = b.comment("Silverfish Carapace: arthropod damage reduction.").defineInRange("silverfishCarapaceArthropodReduction", 0.50, 0.0, 1.0);
        KNOCKBACK_RESISTANCE = b.comment("Ravager Hide and Elder Guardian Spikes: knockback resistance.").defineInRange("ravagerHideKnockbackResistance", 0.50, 0.0, 1.0);
        KNOCKBACK_DEALT = b.comment("Hoglin Hooves: extra knockback dealt by melee hits.").defineInRange("hoglinHoovesKnockbackBonus", 0.50, 0.0, 10.0);
        SPEED_BONUS = b.comment("Bunny Ears and Rabbit Feet: movement speed multiplier bonus.").defineInRange("rabbitSpeedBonus", 0.50, 0.0, 5.0);
        IRON_GOLEM_PLATE_ARMOR = b.comment("Iron Golem Plate: extra armor points.").defineInRange("ironGolemPlateArmor", 6.0, 0.0, 30.0);
        BEE_STINGER_POISON_SECONDS = b.comment("Bee Stinger: seconds of Poison II given to attackers.").defineInRange("beeStingerPoisonSeconds", 5, 0, 60);
        GUARDIAN_SPIKES_DAMAGE = b.comment("Guardian Spikes: thorns damage dealt to attackers.").defineInRange("guardianSpikesDamage", 2.0, 0.0, 40.0);
        TURTLE_SHELL_SHIELD_REDUCTION = b.comment("Turtle Shell: extra damage reduction while blocking with a shield.").defineInRange("turtleShellShieldReduction", 0.25, 0.0, 1.0);
        JUMP_BOOST = b.comment("Rabbit Feet and Frog Legs: extra upward jump velocity.").defineInRange("jumpBoost", 0.10, 0.0, 1.0);
        FROG_SWIM_BOOST = b.comment("Frog Legs: swim speed multiplier bonus per tick.").defineInRange("frogSwimBoost", 0.08, 0.0, 1.0);
        DOUBLE_JUMP_VELOCITY = b.comment("Allay and Vex Wings: upward velocity of the mid-air jump.").defineInRange("doubleJumpVelocity", 0.55, 0.0, 3.0);
        BREEZE_DASH_STRENGTH = b.comment("Breeze Wind: horizontal dash speed.").defineInRange("breezeDashStrength", 1.25, 0.0, 5.0);
        BREEZE_DASH_COOLDOWN_TICKS = b.comment("Breeze Wind: ticks between dashes.").defineInRange("breezeDashCooldownTicks", 20, 0, 1200);
        PERIODIC_COOLDOWN_SECONDS = b.comment("Seconds between the periodic heart abilities and the Creaking Heart dodge.").defineInRange("periodicAbilityCooldownSeconds", 30, 1, 3600);
        WARDEN_EARS_RANGE = b.comment("Warden Ears: range in blocks for highlighting hostile mobs.").defineInRange("wardenEarsGlowRange", 32, 1, 128);
        b.pop();
        SPEC = b.build();
    }

    private ForgedArmorConfig() {}

    private static boolean validOverride(Object o) {
        if (!(o instanceof String s)) return false;
        String[] parts = s.split("=", 2);
        if (parts.length != 2 || AttachmentType.byId(parts[0].trim()) == null) return false;
        try {
            double chance = Double.parseDouble(parts[1].trim());
            return chance >= 0 && chance <= 1;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /** False when the attachment is listed in {@code disabledAttachments}; true before the config has loaded. */
    public static boolean allows(AttachmentType type) {
        return !SPEC.isLoaded() || !DISABLED_ATTACHMENTS.get().contains(type.id());
    }

    /** The base drop chance for one attachment, honouring {@code dropChanceOverrides}. */
    public static double baseDropChance(AttachmentType type) {
        for (String entry : DROP_CHANCE_OVERRIDES.get()) {
            String[] parts = entry.split("=", 2);
            if (parts.length == 2 && parts[0].trim().equals(type.id())) {
                try {
                    return Double.parseDouble(parts[1].trim());
                } catch (NumberFormatException ignored) {
                    // validated on load; fall through to the default
                }
            }
        }
        return BASE_DROP_CHANCE.get();
    }
}
