package com.nanonaitor.forgedarmor;

import java.util.Arrays;

public enum AttachmentType {
    RAVAGER_HORN("Ravager Horn", AttachmentSlot.HORNS, "minecraft:ravager"),
    GOAT_HORN("Goat Horn", AttachmentSlot.HORNS, "minecraft:goat"),
    PILLAGER_NOSE("Pillager Nose", AttachmentSlot.FACE, "minecraft:pillager"),
    ZOMBIE_MASK("Zombie Mask", AttachmentSlot.FACE, "minecraft:zombie", "minecraft:husk", "minecraft:drowned"),
    SKELETON_MASK("Skeleton Mask", AttachmentSlot.FACE, "minecraft:skeleton", "minecraft:stray"),
    CREEPER_MASK("Creeper Mask", AttachmentSlot.FACE, "minecraft:creeper"),
    SPIDER_MASK("Spider Mask", AttachmentSlot.FACE, "minecraft:spider", "minecraft:cave_spider"),
    HOGLIN_HORN("Hoglin Horn", AttachmentSlot.HORNS, "minecraft:hoglin"),
    BUNNY_EARS("Bunny Ears", AttachmentSlot.HORNS, "minecraft:rabbit"),
    ENDER_DRAGON_MASK("Ender Dragon Mask", AttachmentSlot.FACE),
    WARDEN_EARS("Warden Ears", AttachmentSlot.HORNS, "minecraft:warden"),

    WITCH_HEART("Witch Heart", AttachmentSlot.CHEST, "minecraft:witch"),
    IRON_GOLEM_HEART("Iron Golem Heart", AttachmentSlot.CHEST, "minecraft:iron_golem"),
    TURTLE_SHELL("Turtle Shell", AttachmentSlot.CHEST, "minecraft:turtle"),
    CREAKING_HEART("Creaking Heart", AttachmentSlot.CHEST, "minecraft:creaking", "palebloom:creaking"),
    ALLAY_WINGS("Allay Wings", AttachmentSlot.BACK, "minecraft:allay"),
    VEX_WINGS("Vex Wings", AttachmentSlot.BACK, "minecraft:vex"),
    SHULKER_SHELL("Shulker Shell", AttachmentSlot.BACK, "minecraft:shulker"),
    WARDEN_HEART("Warden Heart", AttachmentSlot.CHEST, "minecraft:warden"),
    PHANTOM_WINGS("Phantom Wings", AttachmentSlot.BACK, "minecraft:phantom"),
    SNOW_GOLEM_ICE_CORE("Snow Golem Ice Core", AttachmentSlot.CHEST, "minecraft:snow_golem"),
    IRON_GOLEM_IRON_CORE("Iron Golem Iron Core", AttachmentSlot.CHEST, "minecraft:iron_golem"),

    BEE_STINGER("Bee Stinger", AttachmentSlot.TAIL, "minecraft:bee"),
    GUARDIAN_SPIKES("Guardian Spikes", AttachmentSlot.KNEES, "minecraft:guardian"),
    PARROT_TAIL("Parrot Tail", AttachmentSlot.TAIL, "minecraft:parrot"),
    DONKEY_TAIL("Donkey Tail", AttachmentSlot.TAIL, "minecraft:donkey"),
    CAMEL_TAIL("Camel Tail", AttachmentSlot.TAIL, "minecraft:camel"),
    SHULKER_PLATES("Shulker Plates", AttachmentSlot.KNEES, "minecraft:shulker"),
    RAVAGER_HIDE("Ravager Hide", AttachmentSlot.KNEES, "minecraft:ravager"),
    SILVERFISH_CARAPACE("Silverfish Carapace", AttachmentSlot.KNEES, "minecraft:silverfish"),
    IRON_GOLEM_PLATE("Iron Golem Plate", AttachmentSlot.KNEES, "minecraft:iron_golem"),

    ELDER_GUARDIAN_SPIKES("Elder Guardian Spikes", AttachmentSlot.TOP, "minecraft:elder_guardian"),
    STRIDER_FEET("Strider Feet", AttachmentSlot.SOLES, "minecraft:strider"),
    RABBIT_FEET("Rabbit Feet", AttachmentSlot.SOLES, "minecraft:rabbit"),
    SLIME("Slime", AttachmentSlot.SOLES, "minecraft:slime"),
    BREEZE_WIND("Breeze Wind", AttachmentSlot.TOP, "minecraft:breeze"),
    FROG_LEGS("Frog Legs", AttachmentSlot.TOP, "minecraft:frog"),
    PIGLIN_HOOVES("Piglin Hooves", AttachmentSlot.TOP, "minecraft:piglin", "minecraft:piglin_brute"),
    HOGLIN_HOOVES("Hoglin Hooves", AttachmentSlot.TOP, "minecraft:hoglin"),
    SPIDER_LEGS("Spider Legs", AttachmentSlot.SOLES, "minecraft:spider", "minecraft:cave_spider");

    private final String displayName;
    private final AttachmentSlot slot;
    private final String[] sources;

    AttachmentType(String displayName, AttachmentSlot slot, String... sources) {
        this.displayName = displayName;
        this.slot = slot;
        this.sources = sources;
    }

    public String id() { return name().toLowerCase(); }
    public String displayName() { return displayName; }
    public AttachmentSlot slot() { return slot; }
    public boolean dropsFrom(String entityId) { return Arrays.asList(sources).contains(entityId); }
    public static AttachmentType byId(String id) {
        for (AttachmentType type : values()) if (type.id().equals(id)) return type;
        return null;
    }
}
