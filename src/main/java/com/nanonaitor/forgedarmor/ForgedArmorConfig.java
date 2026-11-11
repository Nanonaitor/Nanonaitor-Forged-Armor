package com.nanonaitor.forgedarmor;

import net.minecraftforge.common.ForgeConfigSpec;

public final class ForgedArmorConfig {
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.BooleanValue ENABLE_MOB_DROPS;
    public static final ForgeConfigSpec.DoubleValue BASE_DROP_CHANCE;
    public static final ForgeConfigSpec.DoubleValue LOOTING_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.DoubleValue LUCK_BONUS_PER_LEVEL;
    public static final ForgeConfigSpec.DoubleValue MELEE_BONUS;
    public static final ForgeConfigSpec.DoubleValue MAGIC_BONUS;
    public static final ForgeConfigSpec.DoubleValue RANGED_BONUS;
    public static final ForgeConfigSpec.DoubleValue PROJECTILE_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue ARTHROPOD_REDUCTION;
    public static final ForgeConfigSpec.DoubleValue KNOCKBACK_RESISTANCE;
    public static final ForgeConfigSpec.DoubleValue KNOCKBACK_DEALT;
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
        ENABLE_END_SHIP_MASK = b.comment("Allow Dragon Heads in End City treasure chests as Face attachments.").define("enableEndShipMask", true);
        END_SHIP_MASK_CHANCE = b.comment("Chance per End City treasure chest to add an attachable Dragon Head.").defineInRange("endShipMaskChance", 0.05, 0.0, 1.0);
        b.pop();

        b.push("abilities");
        MELEE_BONUS = b.defineInRange("ravagerHornMeleeBonus", 0.20, 0.0, 10.0);
        MAGIC_BONUS = b.defineInRange("goatHornMagicBonus", 0.20, 0.0, 10.0);
        RANGED_BONUS = b.defineInRange("hoglinHornRangedBonus", 0.20, 0.0, 10.0);
        PROJECTILE_REDUCTION = b.defineInRange("shulkerPlatesProjectileReduction", 0.25, 0.0, 1.0);
        ARTHROPOD_REDUCTION = b.defineInRange("silverfishCarapaceArthropodReduction", 0.50, 0.0, 1.0);
        KNOCKBACK_RESISTANCE = b.defineInRange("ravagerHideKnockbackResistance", 0.50, 0.0, 1.0);
        KNOCKBACK_DEALT = b.defineInRange("hoglinHoovesKnockbackBonus", 0.50, 0.0, 10.0);
        PERIODIC_COOLDOWN_SECONDS = b.defineInRange("periodicAbilityCooldownSeconds", 30, 1, 3600);
        WARDEN_EARS_RANGE = b.defineInRange("wardenEarsGlowRange", 32, 1, 128);
        b.pop();
        SPEC = b.build();
    }

    private ForgedArmorConfig() {}
}
