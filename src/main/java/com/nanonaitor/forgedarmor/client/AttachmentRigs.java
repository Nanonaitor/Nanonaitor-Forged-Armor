package com.nanonaitor.forgedarmor.client;

// GENERATED FILE - edit tools/attachment_rigs.py and run `python tools/attachment_rigs.py` instead.

import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

/**
 * Equipped attachment geometry copied from the source mobs' vanilla 1.20.1 models (identical boxes, UV offsets,
 * texture sizes and mirroring) plus the transform that fits each piece onto the player outside the armour.
 */
public final class AttachmentRigs {
    public enum Anchor { HEAD, BODY, LEGS }

    /** vertex_on_player = dst + scale * rotationZYX(rotation) * (vertex - src), all in model pixels. */
    public record Rig(AttachmentType type, ModelLayerLocation layer, ResourceLocation texture, ResourceLocation glow,
                      boolean translucent, Anchor anchor, float[] src, float[] dst, float[] rotation, float[] scale) {}

    public static final Map<AttachmentType, Rig> RIGS;

    private AttachmentRigs() {}

    private static ModelLayerLocation layer(String id) {
        return new ModelLayerLocation(new ResourceLocation(ForgedArmor.ID, "attachment/" + id), "main");
    }

    private static PartDefinition[] roots(MeshDefinition mesh) {
        PartDefinition root = mesh.getRoot();
        return new PartDefinition[]{
                root.addOrReplaceChild("both", CubeListBuilder.create(), PartPose.ZERO),
                root.addOrReplaceChild("left", CubeListBuilder.create(), PartPose.ZERO),
                root.addOrReplaceChild("right", CubeListBuilder.create(), PartPose.ZERO)};
    }

    static {
        Map<AttachmentType, Rig> m = new EnumMap<>(AttachmentType.class);
        m.put(AttachmentType.RAVAGER_HORN, new Rig(AttachmentType.RAVAGER_HORN, layer("ravager_horn"),
                new ResourceLocation("minecraft", "textures/entity/illager/ravager.png"), null, false, Anchor.HEAD,
                new float[]{-1F, -14F, -8F}, new float[]{0F, -6.6F, 0.4F},
                new float[]{0F, 0F, 0F}, new float[]{0.56F, 0.56F, 0.56F}));
        m.put(AttachmentType.GOAT_HORN, new Rig(AttachmentType.GOAT_HORN, layer("goat_horn"),
                new ResourceLocation("minecraft", "textures/entity/goat/goat.png"), null, false, Anchor.HEAD,
                new float[]{-0.5F, -9F, -9F}, new float[]{0F, -8.6F, 0.3F},
                new float[]{-0.20944F, 0F, 0F}, new float[]{1.25F, 1.25F, 1.25F}));
        m.put(AttachmentType.PILLAGER_NOSE, new Rig(AttachmentType.PILLAGER_NOSE, layer("pillager_nose"),
                new ResourceLocation("minecraft", "textures/entity/illager/pillager.png"), null, false, Anchor.HEAD,
                new float[]{0F, -3F, -4F}, new float[]{0F, -3.2F, -4.9F},
                new float[]{0F, 0F, 0F}, new float[]{1F, 1F, 1F}));
        m.put(AttachmentType.ZOMBIE_MASK, new Rig(AttachmentType.ZOMBIE_MASK, layer("zombie_mask"),
                new ResourceLocation("minecraft", "textures/entity/zombie/zombie.png"), null, false, Anchor.HEAD,
                new float[]{0F, -4F, 0F}, new float[]{0F, -4.2F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.3F, 1.3F, 1.3F}));
        m.put(AttachmentType.SKELETON_MASK, new Rig(AttachmentType.SKELETON_MASK, layer("skeleton_mask"),
                new ResourceLocation("minecraft", "textures/entity/skeleton/skeleton.png"), null, false, Anchor.HEAD,
                new float[]{0F, -4F, 0F}, new float[]{0F, -4.2F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.3F, 1.3F, 1.3F}));
        m.put(AttachmentType.CREEPER_MASK, new Rig(AttachmentType.CREEPER_MASK, layer("creeper_mask"),
                new ResourceLocation("minecraft", "textures/entity/creeper/creeper.png"), null, false, Anchor.HEAD,
                new float[]{0F, -4F, 0F}, new float[]{0F, -4.2F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.3F, 1.3F, 1.3F}));
        m.put(AttachmentType.SPIDER_MASK, new Rig(AttachmentType.SPIDER_MASK, layer("spider_mask"),
                new ResourceLocation("minecraft", "textures/entity/spider/spider.png"), new ResourceLocation("minecraft", "textures/entity/spider_eyes.png"), false, Anchor.HEAD,
                new float[]{0F, 0F, -4F}, new float[]{0F, -4.2F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.3F, 1.3F, 1.3F}));
        m.put(AttachmentType.HOGLIN_HORN, new Rig(AttachmentType.HOGLIN_HORN, layer("hoglin_horn"),
                new ResourceLocation("minecraft", "textures/entity/hoglin/hoglin.png"), null, false, Anchor.HEAD,
                new float[]{0F, 2F, -12F}, new float[]{0F, 0.6F, -5.5F},
                new float[]{-0.13963F, 0F, 0F}, new float[]{0.62F, 0.62F, 0.62F}));
        m.put(AttachmentType.BUNNY_EARS, new Rig(AttachmentType.BUNNY_EARS, layer("bunny_ears"),
                new ResourceLocation("minecraft", "textures/entity/rabbit/brown.png"), null, false, Anchor.HEAD,
                new float[]{0F, 12F, -1F}, new float[]{0F, -8.7F, 0.6F},
                new float[]{0F, 0F, 0F}, new float[]{1.8F, 1.8F, 1.8F}));
        m.put(AttachmentType.ENDER_DRAGON_MASK, new Rig(AttachmentType.ENDER_DRAGON_MASK, layer("ender_dragon_mask"),
                new ResourceLocation("minecraft", "textures/entity/enderdragon/dragon.png"), null, false, Anchor.HEAD,
                new float[]{0F, 0F, -2F}, new float[]{0F, -5.6F, 0.6F},
                new float[]{0F, 0F, 0F}, new float[]{0.89F, 0.89F, 0.89F}));
        m.put(AttachmentType.WARDEN_EARS, new Rig(AttachmentType.WARDEN_EARS, layer("warden_ears"),
                new ResourceLocation("minecraft", "textures/entity/warden/warden.png"), null, false, Anchor.HEAD,
                new float[]{0F, -12F, 0F}, new float[]{0F, -6.4F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{0.625F, 0.625F, 0.625F}));
        m.put(AttachmentType.WITCH_HEART, new Rig(AttachmentType.WITCH_HEART, layer("witch_heart"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/witch_heart.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.5F, 0.5F, 0.8F}));
        m.put(AttachmentType.IRON_GOLEM_HEART, new Rig(AttachmentType.IRON_GOLEM_HEART, layer("iron_golem_heart"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/iron_golem_heart.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.55F, 0.55F, 0.8F}));
        m.put(AttachmentType.CREAKING_HEART, new Rig(AttachmentType.CREAKING_HEART, layer("creaking_heart"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/creaking_heart.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.55F, 0.55F, 0.8F}));
        m.put(AttachmentType.WARDEN_HEART, new Rig(AttachmentType.WARDEN_HEART, layer("warden_heart"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/warden_heart.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.55F, 0.55F, 0.8F}));
        m.put(AttachmentType.SNOW_GOLEM_ICE_CORE, new Rig(AttachmentType.SNOW_GOLEM_ICE_CORE, layer("snow_golem_ice_core"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/snow_golem_ice_core.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.5F, 0.5F, 0.8F}));
        m.put(AttachmentType.IRON_GOLEM_IRON_CORE, new Rig(AttachmentType.IRON_GOLEM_IRON_CORE, layer("iron_golem_iron_core"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/iron_golem_iron_core.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0.5F}, new float[]{0F, 4.6F, -3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.55F, 0.55F, 0.8F}));
        m.put(AttachmentType.TURTLE_SHELL, new Rig(AttachmentType.TURTLE_SHELL, layer("turtle_shell"),
                new ResourceLocation("minecraft", "textures/entity/turtle/big_sea_turtle.png"), null, false, Anchor.BODY,
                new float[]{0F, 13F, -10F}, new float[]{0F, 6.2F, 3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.55F, 0.55F, 0.55F}));
        m.put(AttachmentType.ALLAY_WINGS, new Rig(AttachmentType.ALLAY_WINGS, layer("allay_wings"),
                new ResourceLocation("minecraft", "textures/entity/allay/allay.png"), null, true, Anchor.BODY,
                new float[]{0F, 0F, 0.6F}, new float[]{0F, 1.2F, 3.1F},
                new float[]{0F, 0F, 0F}, new float[]{1.5F, 1.5F, 1.5F}));
        m.put(AttachmentType.VEX_WINGS, new Rig(AttachmentType.VEX_WINGS, layer("vex_wings"),
                new ResourceLocation("minecraft", "textures/entity/illager/vex.png"), null, true, Anchor.BODY,
                new float[]{0F, 1F, 1F}, new float[]{0F, 1.4F, 3.1F},
                new float[]{0F, 0F, 0F}, new float[]{1.5F, 1.5F, 1.5F}));
        m.put(AttachmentType.SHULKER_SHELL, new Rig(AttachmentType.SHULKER_SHELL, layer("shulker_shell"),
                new ResourceLocation("minecraft", "textures/entity/shulker/shulker.png"), null, false, Anchor.BODY,
                new float[]{0F, 16F, -8F}, new float[]{0F, 6F, 3.05F},
                new float[]{0F, 0F, 0F}, new float[]{0.5F, 0.5F, 0.5F}));
        m.put(AttachmentType.PHANTOM_WINGS, new Rig(AttachmentType.PHANTOM_WINGS, layer("phantom_wings"),
                new ResourceLocation("minecraft", "textures/entity/phantom.png"), null, false, Anchor.BODY,
                new float[]{-0.5F, 0F, -8F}, new float[]{0F, 1F, 3.1F},
                new float[]{-1.5708F, 0F, 0F}, new float[]{0.6F, 0.6F, 0.6F}));
        m.put(AttachmentType.BEE_STINGER, new Rig(AttachmentType.BEE_STINGER, layer("bee_stinger"),
                new ResourceLocation("minecraft", "textures/entity/bee/bee.png"), null, false, Anchor.BODY,
                new float[]{0F, -0.5F, 5F}, new float[]{0F, 10.6F, 2.55F},
                new float[]{0F, 0F, 0F}, new float[]{3F, 3F, 3F}));
        m.put(AttachmentType.PARROT_TAIL, new Rig(AttachmentType.PARROT_TAIL, layer("parrot_tail"),
                new ResourceLocation("minecraft", "textures/entity/parrot/parrot_red_blue.png"), null, false, Anchor.BODY,
                new float[]{0F, -1F, -1F}, new float[]{0F, 10.2F, 2.55F},
                new float[]{0.55851F, 0F, 0F}, new float[]{2.2F, 2.2F, 2.2F}));
        m.put(AttachmentType.CAMEL_TAIL, new Rig(AttachmentType.CAMEL_TAIL, layer("camel_tail"),
                new ResourceLocation("minecraft", "textures/entity/camel/camel.png"), null, false, Anchor.BODY,
                new float[]{0F, 0F, 0F}, new float[]{0F, 10F, 2.6F},
                new float[]{0.2618F, 0F, 0F}, new float[]{0.75F, 0.75F, 0.75F}));
        m.put(AttachmentType.GUARDIAN_SPIKES, new Rig(AttachmentType.GUARDIAN_SPIKES, layer("guardian_spikes"),
                new ResourceLocation("minecraft", "textures/entity/guardian.png"), null, false, Anchor.LEGS,
                new float[]{0F, 0F, 0F}, new float[]{0F, 5.5F, -2.5F},
                new float[]{0F, 0F, 0F}, new float[]{0.45F, 0.45F, 0.45F}));
        m.put(AttachmentType.SHULKER_PLATES, new Rig(AttachmentType.SHULKER_PLATES, layer("shulker_plates"),
                new ResourceLocation("minecraft", "textures/entity/shulker/shulker.png"), null, false, Anchor.LEGS,
                new float[]{0F, -4F, 0F}, new float[]{0F, 6F, -2.55F},
                new float[]{1.5708F, 0F, 0F}, new float[]{0.3F, 0.3F, 0.12F}));
        m.put(AttachmentType.RAVAGER_HIDE, new Rig(AttachmentType.RAVAGER_HIDE, layer("ravager_hide"),
                new ResourceLocation("minecraft", "textures/entity/illager/ravager.png"), null, false, Anchor.LEGS,
                new float[]{0F, 4F, 4F}, new float[]{0F, 6F, -2.55F},
                new float[]{0F, 0F, 0F}, new float[]{0.62F, 0.62F, 0.18F}));
        m.put(AttachmentType.SILVERFISH_CARAPACE, new Rig(AttachmentType.SILVERFISH_CARAPACE, layer("silverfish_carapace"),
                new ResourceLocation("minecraft", "textures/entity/silverfish.png"), null, false, Anchor.LEGS,
                new float[]{0F, 4F, 1.5F}, new float[]{0F, 5.5F, -2.55F},
                new float[]{0F, 0F, 0F}, new float[]{0.48F, 0.48F, 0.48F}));
        m.put(AttachmentType.IRON_GOLEM_PLATE, new Rig(AttachmentType.IRON_GOLEM_PLATE, layer("iron_golem_plate"),
                new ResourceLocation("minecraft", "textures/entity/iron_golem/iron_golem.png"), null, false, Anchor.LEGS,
                new float[]{0F, 3F, 2.5F}, new float[]{0F, 6F, -2.55F},
                new float[]{0F, 0F, 0F}, new float[]{0.8F, 0.8F, 0.28F}));
        m.put(AttachmentType.ELDER_GUARDIAN_SPIKES, new Rig(AttachmentType.ELDER_GUARDIAN_SPIKES, layer("elder_guardian_spikes"),
                new ResourceLocation("minecraft", "textures/entity/guardian_elder.png"), null, false, Anchor.LEGS,
                new float[]{0F, 0F, 0F}, new float[]{0F, 9.5F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{0.45F, 0.45F, 0.45F}));
        m.put(AttachmentType.STRIDER_FEET, new Rig(AttachmentType.STRIDER_FEET, layer("strider_feet"),
                new ResourceLocation("minecraft", "textures/entity/strider/strider.png"), null, false, Anchor.LEGS,
                new float[]{0F, 6F, 0F}, new float[]{0F, 13.1F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.6F, 0.9F, 1.6F}));
        m.put(AttachmentType.RABBIT_FEET, new Rig(AttachmentType.RABBIT_FEET, layer("rabbit_feet"),
                new ResourceLocation("minecraft", "textures/entity/rabbit/brown.png"), null, false, Anchor.LEGS,
                new float[]{0F, 6.5F, -0.2F}, new float[]{0F, 13.15F, -2.2F},
                new float[]{0F, 0F, 0F}, new float[]{1.8F, 1.8F, 1.8F}));
        m.put(AttachmentType.SLIME, new Rig(AttachmentType.SLIME, layer("slime"),
                new ResourceLocation("minecraft", "textures/entity/slime/slime.png"), null, true, Anchor.LEGS,
                new float[]{0F, 24F, 0F}, new float[]{0F, 13.2F, -0.4F},
                new float[]{0F, 0F, 0F}, new float[]{0.85F, 0.45F, 0.95F}));
        m.put(AttachmentType.BREEZE_WIND, new Rig(AttachmentType.BREEZE_WIND, layer("breeze_wind"),
                new ResourceLocation("nanonaitor_forged_armor", "textures/entity/attachment/breeze_wind.png"), null, true, Anchor.LEGS,
                new float[]{0F, 0F, 0F}, new float[]{0F, 0F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1F, 1F, 1F}));
        m.put(AttachmentType.FROG_LEGS, new Rig(AttachmentType.FROG_LEGS, layer("frog_legs"),
                new ResourceLocation("minecraft", "textures/entity/frog/temperate_frog.png"), null, false, Anchor.LEGS,
                new float[]{0F, 0F, 0F}, new float[]{0F, 13F, -1.6F},
                new float[]{0F, 0F, 0F}, new float[]{1.55F, 1.55F, 1.55F}));
        m.put(AttachmentType.PIGLIN_HOOVES, new Rig(AttachmentType.PIGLIN_HOOVES, layer("piglin_hooves"),
                new ResourceLocation("minecraft", "textures/entity/piglin/piglin.png"), null, false, Anchor.LEGS,
                new float[]{0F, 3F, 0F}, new float[]{0F, 13.1F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.6F, 1.25F, 1.6F}));
        m.put(AttachmentType.HOGLIN_HOOVES, new Rig(AttachmentType.HOGLIN_HOOVES, layer("hoglin_hooves"),
                new ResourceLocation("minecraft", "textures/entity/hoglin/hoglin.png"), null, false, Anchor.LEGS,
                new float[]{0F, 4F, 0F}, new float[]{0F, 13.1F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{1.1F, 1F, 1.1F}));
        m.put(AttachmentType.SPIDER_LEGS, new Rig(AttachmentType.SPIDER_LEGS, layer("spider_legs"),
                new ResourceLocation("minecraft", "textures/entity/spider/spider.png"), null, false, Anchor.LEGS,
                new float[]{0F, 0F, 0F}, new float[]{0F, 7.6F, 0F},
                new float[]{0F, 0F, 0F}, new float[]{0.45F, 0.45F, 0.45F}));
        RIGS = Collections.unmodifiableMap(m);
    }

    public static LayerDefinition create(AttachmentType type) {
        return switch (type) {
            case RAVAGER_HORN -> ravagerHorn();
            case GOAT_HORN -> goatHorn();
            case PILLAGER_NOSE -> pillagerNose();
            case ZOMBIE_MASK -> zombieMask();
            case SKELETON_MASK -> skeletonMask();
            case CREEPER_MASK -> creeperMask();
            case SPIDER_MASK -> spiderMask();
            case HOGLIN_HORN -> hoglinHorn();
            case BUNNY_EARS -> bunnyEars();
            case ENDER_DRAGON_MASK -> enderDragonMask();
            case WARDEN_EARS -> wardenEars();
            case WITCH_HEART -> witchHeart();
            case IRON_GOLEM_HEART -> ironGolemHeart();
            case CREAKING_HEART -> creakingHeart();
            case WARDEN_HEART -> wardenHeart();
            case SNOW_GOLEM_ICE_CORE -> snowGolemIceCore();
            case IRON_GOLEM_IRON_CORE -> ironGolemIronCore();
            case TURTLE_SHELL -> turtleShell();
            case ALLAY_WINGS -> allayWings();
            case VEX_WINGS -> vexWings();
            case SHULKER_SHELL -> shulkerShell();
            case PHANTOM_WINGS -> phantomWings();
            case BEE_STINGER -> beeStinger();
            case PARROT_TAIL -> parrotTail();
            case CAMEL_TAIL -> camelTail();
            case GUARDIAN_SPIKES -> guardianSpikes();
            case SHULKER_PLATES -> shulkerPlates();
            case RAVAGER_HIDE -> ravagerHide();
            case SILVERFISH_CARAPACE -> silverfishCarapace();
            case IRON_GOLEM_PLATE -> ironGolemPlate();
            case ELDER_GUARDIAN_SPIKES -> elderGuardianSpikes();
            case STRIDER_FEET -> striderFeet();
            case RABBIT_FEET -> rabbitFeet();
            case SLIME -> slime();
            case BREEZE_WIND -> breezeWind();
            case FROG_LEGS -> frogLegs();
            case PIGLIN_HOOVES -> piglinHooves();
            case HOGLIN_HOOVES -> hoglinHooves();
            case SPIDER_LEGS -> spiderLegs();
            default -> throw new IllegalArgumentException("No equipped model for " + type);
        };
    }

    private static LayerDefinition ravagerHorn() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(74, 55).mirror(false).addBox(0F, -14F, -2F, 2F, 14F, 4F), PartPose.offsetAndRotation(-10F, -14F, -8F, 1.0996F, 0F, 0F));
        sides[0].addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(74, 55).mirror(true).addBox(0F, -14F, -2F, 2F, 14F, 4F), PartPose.offsetAndRotation(8F, -14F, -8F, 1.0996F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static LayerDefinition goatHorn() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(12, 55).mirror(false).addBox(-0.01F, -16F, -10F, 2F, 7F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(12, 55).mirror(false).addBox(-2.99F, -16F, -10F, 2F, 7F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition pillagerNose() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("nose", CubeListBuilder.create().texOffs(24, 0).mirror(false).addBox(-1F, -1F, -6F, 2F, 4F, 2F), PartPose.offsetAndRotation(0F, -2F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition zombieMask() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -8F, -4F, 8F, 8F, 8F).texOffs(32, 0).mirror(false).addBox(-4F, -8F, -4F, 8F, 8F, 8F, new CubeDeformation(0.25F)), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition skeletonMask() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -8F, -4F, 8F, 8F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition creeperMask() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -8F, -4F, 8F, 8F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition spiderMask() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("head", CubeListBuilder.create().texOffs(32, 4).mirror(false).addBox(-4F, -4F, -8F, 8F, 8F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition hoglinHorn() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(10, 13).mirror(false).addBox(-1F, -11F, -1F, 2F, 11F, 2F), PartPose.offsetAndRotation(-7F, 2F, -12F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(1, 13).mirror(false).addBox(-1F, -11F, -1F, 2F, 11F, 2F), PartPose.offsetAndRotation(7F, 2F, -12F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    private static LayerDefinition bunnyEars() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(52, 0).mirror(false).addBox(-2.5F, -9F, -1F, 2F, 5F, 1F), PartPose.offsetAndRotation(0F, 16F, -1F, 0F, -0.2618F, 0F));
        sides[0].addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(58, 0).mirror(false).addBox(0.5F, -9F, -1F, 2F, 5F, 1F), PartPose.offsetAndRotation(0F, 16F, -1F, 0F, 0.2618F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition enderDragonMask() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        PartDefinition p0 = sides[0].addOrReplaceChild("head", CubeListBuilder.create().texOffs(176, 44).mirror(false).addBox(-6F, -1F, -24F, 12F, 5F, 16F).texOffs(112, 30).mirror(false).addBox(-8F, -8F, -10F, 16F, 16F, 16F).texOffs(0, 0).mirror(true).addBox(-5F, -12F, -4F, 2F, 4F, 6F).texOffs(112, 0).mirror(true).addBox(-5F, -3F, -22F, 2F, 2F, 4F).texOffs(0, 0).mirror(false).addBox(3F, -12F, -4F, 2F, 4F, 6F).texOffs(112, 0).mirror(false).addBox(3F, -3F, -22F, 2F, 2F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        p0.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(176, 65).mirror(false).addBox(-6F, 0F, -16F, 12F, 4F, 16F), PartPose.offsetAndRotation(0F, 4F, -8F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 256, 256);
    }

    private static LayerDefinition wardenEars() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_tendril", CubeListBuilder.create().texOffs(52, 32).mirror(false).addBox(-16F, -13F, 0F, 16F, 16F, 0F), PartPose.offsetAndRotation(-8F, -12F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("left_tendril", CubeListBuilder.create().texOffs(58, 0).mirror(false).addBox(0F, -13F, 0F, 16F, 16F, 0F), PartPose.offsetAndRotation(8F, -12F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static LayerDefinition witchHeart() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(-6F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(-6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition ironGolemHeart() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(-6F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(-6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition creakingHeart() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(-6F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(-6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition wardenHeart() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(4F, -6F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(-6F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(6F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(-6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(6F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition snowGolemIceCore() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-2F, -7F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(-1F, -7F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(0F, -7F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(1F, -7F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(2F, -7F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-2F, -6F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-1F, -6F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(0F, -6F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -6F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -6F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(0F, -5F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-7F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(7F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(-7F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(7F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(-7F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(-6F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(6F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(7F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-7F, 1F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-6F, 1F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(6F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(7F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-7F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-6F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(-5F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 14).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(4, 14).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(8, 14).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(12, 14).mirror(false).addBox(5F, 2F, -0.5F, 1F, 1F, 1F).texOffs(16, 14).mirror(false).addBox(6F, 2F, -0.5F, 1F, 1F, 1F).texOffs(20, 14).mirror(false).addBox(7F, 2F, -0.5F, 1F, 1F, 1F).texOffs(24, 14).mirror(false).addBox(-5F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 14).mirror(false).addBox(-4F, 3F, -0.5F, 1F, 1F, 1F).texOffs(32, 14).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(36, 14).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(40, 14).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(44, 14).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(48, 14).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(52, 14).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(56, 14).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(60, 14).mirror(false).addBox(4F, 3F, -0.5F, 1F, 1F, 1F).texOffs(0, 16).mirror(false).addBox(5F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 16).mirror(false).addBox(-5F, 4F, -0.5F, 1F, 1F, 1F).texOffs(8, 16).mirror(false).addBox(-4F, 4F, -0.5F, 1F, 1F, 1F).texOffs(12, 16).mirror(false).addBox(-3F, 4F, -0.5F, 1F, 1F, 1F).texOffs(16, 16).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(20, 16).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(24, 16).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(28, 16).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 16).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 16).mirror(false).addBox(3F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 16).mirror(false).addBox(4F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 16).mirror(false).addBox(5F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 16).mirror(false).addBox(-5F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 16).mirror(false).addBox(-4F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 16).mirror(false).addBox(-3F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 16).mirror(false).addBox(-2F, 5F, -0.5F, 1F, 1F, 1F).texOffs(0, 18).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(4, 18).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(8, 18).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(12, 18).mirror(false).addBox(2F, 5F, -0.5F, 1F, 1F, 1F).texOffs(16, 18).mirror(false).addBox(3F, 5F, -0.5F, 1F, 1F, 1F).texOffs(20, 18).mirror(false).addBox(4F, 5F, -0.5F, 1F, 1F, 1F).texOffs(24, 18).mirror(false).addBox(5F, 5F, -0.5F, 1F, 1F, 1F).texOffs(28, 18).mirror(false).addBox(-2F, 6F, -0.5F, 1F, 1F, 1F).texOffs(32, 18).mirror(false).addBox(-1F, 6F, -0.5F, 1F, 1F, 1F).texOffs(36, 18).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F).texOffs(40, 18).mirror(false).addBox(1F, 6F, -0.5F, 1F, 1F, 1F).texOffs(44, 18).mirror(false).addBox(2F, 6F, -0.5F, 1F, 1F, 1F).texOffs(48, 18).mirror(false).addBox(-2F, 7F, -0.5F, 1F, 1F, 1F).texOffs(52, 18).mirror(false).addBox(-1F, 7F, -0.5F, 1F, 1F, 1F).texOffs(56, 18).mirror(false).addBox(0F, 7F, -0.5F, 1F, 1F, 1F).texOffs(60, 18).mirror(false).addBox(1F, 7F, -0.5F, 1F, 1F, 1F).texOffs(0, 20).mirror(false).addBox(2F, 7F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition ironGolemIronCore() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("voxels", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-2F, -7F, -0.5F, 1F, 1F, 1F).texOffs(4, 0).mirror(false).addBox(-1F, -7F, -0.5F, 1F, 1F, 1F).texOffs(8, 0).mirror(false).addBox(0F, -7F, -0.5F, 1F, 1F, 1F).texOffs(12, 0).mirror(false).addBox(1F, -7F, -0.5F, 1F, 1F, 1F).texOffs(16, 0).mirror(false).addBox(2F, -7F, -0.5F, 1F, 1F, 1F).texOffs(20, 0).mirror(false).addBox(-2F, -6F, -0.5F, 1F, 1F, 1F).texOffs(24, 0).mirror(false).addBox(-1F, -6F, -0.5F, 1F, 1F, 1F).texOffs(28, 0).mirror(false).addBox(0F, -6F, -0.5F, 1F, 1F, 1F).texOffs(32, 0).mirror(false).addBox(1F, -6F, -0.5F, 1F, 1F, 1F).texOffs(36, 0).mirror(false).addBox(2F, -6F, -0.5F, 1F, 1F, 1F).texOffs(40, 0).mirror(false).addBox(-5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(44, 0).mirror(false).addBox(-4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(48, 0).mirror(false).addBox(-3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(52, 0).mirror(false).addBox(-2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(56, 0).mirror(false).addBox(-1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(60, 0).mirror(false).addBox(0F, -5F, -0.5F, 1F, 1F, 1F).texOffs(0, 2).mirror(false).addBox(1F, -5F, -0.5F, 1F, 1F, 1F).texOffs(4, 2).mirror(false).addBox(2F, -5F, -0.5F, 1F, 1F, 1F).texOffs(8, 2).mirror(false).addBox(3F, -5F, -0.5F, 1F, 1F, 1F).texOffs(12, 2).mirror(false).addBox(4F, -5F, -0.5F, 1F, 1F, 1F).texOffs(16, 2).mirror(false).addBox(5F, -5F, -0.5F, 1F, 1F, 1F).texOffs(20, 2).mirror(false).addBox(-5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(24, 2).mirror(false).addBox(-4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(28, 2).mirror(false).addBox(-3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(32, 2).mirror(false).addBox(-2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(36, 2).mirror(false).addBox(-1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(40, 2).mirror(false).addBox(0F, -4F, -0.5F, 1F, 1F, 1F).texOffs(44, 2).mirror(false).addBox(1F, -4F, -0.5F, 1F, 1F, 1F).texOffs(48, 2).mirror(false).addBox(2F, -4F, -0.5F, 1F, 1F, 1F).texOffs(52, 2).mirror(false).addBox(3F, -4F, -0.5F, 1F, 1F, 1F).texOffs(56, 2).mirror(false).addBox(4F, -4F, -0.5F, 1F, 1F, 1F).texOffs(60, 2).mirror(false).addBox(5F, -4F, -0.5F, 1F, 1F, 1F).texOffs(0, 4).mirror(false).addBox(-5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(4, 4).mirror(false).addBox(-4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(8, 4).mirror(false).addBox(-3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(12, 4).mirror(false).addBox(-2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(16, 4).mirror(false).addBox(-1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(20, 4).mirror(false).addBox(0F, -3F, -0.5F, 1F, 1F, 1F).texOffs(24, 4).mirror(false).addBox(1F, -3F, -0.5F, 1F, 1F, 1F).texOffs(28, 4).mirror(false).addBox(2F, -3F, -0.5F, 1F, 1F, 1F).texOffs(32, 4).mirror(false).addBox(3F, -3F, -0.5F, 1F, 1F, 1F).texOffs(36, 4).mirror(false).addBox(4F, -3F, -0.5F, 1F, 1F, 1F).texOffs(40, 4).mirror(false).addBox(5F, -3F, -0.5F, 1F, 1F, 1F).texOffs(44, 4).mirror(false).addBox(-7F, -2F, -0.5F, 1F, 1F, 1F).texOffs(48, 4).mirror(false).addBox(-6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(52, 4).mirror(false).addBox(-5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(56, 4).mirror(false).addBox(-4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(60, 4).mirror(false).addBox(-3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(0, 6).mirror(false).addBox(-2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(4, 6).mirror(false).addBox(-1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(8, 6).mirror(false).addBox(0F, -2F, -0.5F, 1F, 1F, 1F).texOffs(12, 6).mirror(false).addBox(1F, -2F, -0.5F, 1F, 1F, 1F).texOffs(16, 6).mirror(false).addBox(2F, -2F, -0.5F, 1F, 1F, 1F).texOffs(20, 6).mirror(false).addBox(3F, -2F, -0.5F, 1F, 1F, 1F).texOffs(24, 6).mirror(false).addBox(4F, -2F, -0.5F, 1F, 1F, 1F).texOffs(28, 6).mirror(false).addBox(5F, -2F, -0.5F, 1F, 1F, 1F).texOffs(32, 6).mirror(false).addBox(6F, -2F, -0.5F, 1F, 1F, 1F).texOffs(36, 6).mirror(false).addBox(7F, -2F, -0.5F, 1F, 1F, 1F).texOffs(40, 6).mirror(false).addBox(-7F, -1F, -0.5F, 1F, 1F, 1F).texOffs(44, 6).mirror(false).addBox(-6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(48, 6).mirror(false).addBox(-5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(52, 6).mirror(false).addBox(-4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(56, 6).mirror(false).addBox(-3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(60, 6).mirror(false).addBox(-2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(0, 8).mirror(false).addBox(-1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(4, 8).mirror(false).addBox(0F, -1F, -0.5F, 1F, 1F, 1F).texOffs(8, 8).mirror(false).addBox(1F, -1F, -0.5F, 1F, 1F, 1F).texOffs(12, 8).mirror(false).addBox(2F, -1F, -0.5F, 1F, 1F, 1F).texOffs(16, 8).mirror(false).addBox(3F, -1F, -0.5F, 1F, 1F, 1F).texOffs(20, 8).mirror(false).addBox(4F, -1F, -0.5F, 1F, 1F, 1F).texOffs(24, 8).mirror(false).addBox(5F, -1F, -0.5F, 1F, 1F, 1F).texOffs(28, 8).mirror(false).addBox(6F, -1F, -0.5F, 1F, 1F, 1F).texOffs(32, 8).mirror(false).addBox(7F, -1F, -0.5F, 1F, 1F, 1F).texOffs(36, 8).mirror(false).addBox(-7F, 0F, -0.5F, 1F, 1F, 1F).texOffs(40, 8).mirror(false).addBox(-6F, 0F, -0.5F, 1F, 1F, 1F).texOffs(44, 8).mirror(false).addBox(-5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(48, 8).mirror(false).addBox(-4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(52, 8).mirror(false).addBox(-3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(56, 8).mirror(false).addBox(-2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(60, 8).mirror(false).addBox(-1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(0, 10).mirror(false).addBox(0F, 0F, -0.5F, 1F, 1F, 1F).texOffs(4, 10).mirror(false).addBox(1F, 0F, -0.5F, 1F, 1F, 1F).texOffs(8, 10).mirror(false).addBox(2F, 0F, -0.5F, 1F, 1F, 1F).texOffs(12, 10).mirror(false).addBox(3F, 0F, -0.5F, 1F, 1F, 1F).texOffs(16, 10).mirror(false).addBox(4F, 0F, -0.5F, 1F, 1F, 1F).texOffs(20, 10).mirror(false).addBox(5F, 0F, -0.5F, 1F, 1F, 1F).texOffs(24, 10).mirror(false).addBox(6F, 0F, -0.5F, 1F, 1F, 1F).texOffs(28, 10).mirror(false).addBox(7F, 0F, -0.5F, 1F, 1F, 1F).texOffs(32, 10).mirror(false).addBox(-7F, 1F, -0.5F, 1F, 1F, 1F).texOffs(36, 10).mirror(false).addBox(-6F, 1F, -0.5F, 1F, 1F, 1F).texOffs(40, 10).mirror(false).addBox(-5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(44, 10).mirror(false).addBox(-4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(48, 10).mirror(false).addBox(-3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(52, 10).mirror(false).addBox(-2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(56, 10).mirror(false).addBox(-1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(60, 10).mirror(false).addBox(0F, 1F, -0.5F, 1F, 1F, 1F).texOffs(0, 12).mirror(false).addBox(1F, 1F, -0.5F, 1F, 1F, 1F).texOffs(4, 12).mirror(false).addBox(2F, 1F, -0.5F, 1F, 1F, 1F).texOffs(8, 12).mirror(false).addBox(3F, 1F, -0.5F, 1F, 1F, 1F).texOffs(12, 12).mirror(false).addBox(4F, 1F, -0.5F, 1F, 1F, 1F).texOffs(16, 12).mirror(false).addBox(5F, 1F, -0.5F, 1F, 1F, 1F).texOffs(20, 12).mirror(false).addBox(6F, 1F, -0.5F, 1F, 1F, 1F).texOffs(24, 12).mirror(false).addBox(7F, 1F, -0.5F, 1F, 1F, 1F).texOffs(28, 12).mirror(false).addBox(-7F, 2F, -0.5F, 1F, 1F, 1F).texOffs(32, 12).mirror(false).addBox(-6F, 2F, -0.5F, 1F, 1F, 1F).texOffs(36, 12).mirror(false).addBox(-5F, 2F, -0.5F, 1F, 1F, 1F).texOffs(40, 12).mirror(false).addBox(-4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(44, 12).mirror(false).addBox(-3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(48, 12).mirror(false).addBox(-2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(52, 12).mirror(false).addBox(-1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(56, 12).mirror(false).addBox(0F, 2F, -0.5F, 1F, 1F, 1F).texOffs(60, 12).mirror(false).addBox(1F, 2F, -0.5F, 1F, 1F, 1F).texOffs(0, 14).mirror(false).addBox(2F, 2F, -0.5F, 1F, 1F, 1F).texOffs(4, 14).mirror(false).addBox(3F, 2F, -0.5F, 1F, 1F, 1F).texOffs(8, 14).mirror(false).addBox(4F, 2F, -0.5F, 1F, 1F, 1F).texOffs(12, 14).mirror(false).addBox(5F, 2F, -0.5F, 1F, 1F, 1F).texOffs(16, 14).mirror(false).addBox(6F, 2F, -0.5F, 1F, 1F, 1F).texOffs(20, 14).mirror(false).addBox(7F, 2F, -0.5F, 1F, 1F, 1F).texOffs(24, 14).mirror(false).addBox(-5F, 3F, -0.5F, 1F, 1F, 1F).texOffs(28, 14).mirror(false).addBox(-4F, 3F, -0.5F, 1F, 1F, 1F).texOffs(32, 14).mirror(false).addBox(-3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(36, 14).mirror(false).addBox(-2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(40, 14).mirror(false).addBox(-1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(44, 14).mirror(false).addBox(0F, 3F, -0.5F, 1F, 1F, 1F).texOffs(48, 14).mirror(false).addBox(1F, 3F, -0.5F, 1F, 1F, 1F).texOffs(52, 14).mirror(false).addBox(2F, 3F, -0.5F, 1F, 1F, 1F).texOffs(56, 14).mirror(false).addBox(3F, 3F, -0.5F, 1F, 1F, 1F).texOffs(60, 14).mirror(false).addBox(4F, 3F, -0.5F, 1F, 1F, 1F).texOffs(0, 16).mirror(false).addBox(5F, 3F, -0.5F, 1F, 1F, 1F).texOffs(4, 16).mirror(false).addBox(-5F, 4F, -0.5F, 1F, 1F, 1F).texOffs(8, 16).mirror(false).addBox(-4F, 4F, -0.5F, 1F, 1F, 1F).texOffs(12, 16).mirror(false).addBox(-3F, 4F, -0.5F, 1F, 1F, 1F).texOffs(16, 16).mirror(false).addBox(-2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(20, 16).mirror(false).addBox(-1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(24, 16).mirror(false).addBox(0F, 4F, -0.5F, 1F, 1F, 1F).texOffs(28, 16).mirror(false).addBox(1F, 4F, -0.5F, 1F, 1F, 1F).texOffs(32, 16).mirror(false).addBox(2F, 4F, -0.5F, 1F, 1F, 1F).texOffs(36, 16).mirror(false).addBox(3F, 4F, -0.5F, 1F, 1F, 1F).texOffs(40, 16).mirror(false).addBox(4F, 4F, -0.5F, 1F, 1F, 1F).texOffs(44, 16).mirror(false).addBox(5F, 4F, -0.5F, 1F, 1F, 1F).texOffs(48, 16).mirror(false).addBox(-5F, 5F, -0.5F, 1F, 1F, 1F).texOffs(52, 16).mirror(false).addBox(-4F, 5F, -0.5F, 1F, 1F, 1F).texOffs(56, 16).mirror(false).addBox(-3F, 5F, -0.5F, 1F, 1F, 1F).texOffs(60, 16).mirror(false).addBox(-2F, 5F, -0.5F, 1F, 1F, 1F).texOffs(0, 18).mirror(false).addBox(-1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(4, 18).mirror(false).addBox(0F, 5F, -0.5F, 1F, 1F, 1F).texOffs(8, 18).mirror(false).addBox(1F, 5F, -0.5F, 1F, 1F, 1F).texOffs(12, 18).mirror(false).addBox(2F, 5F, -0.5F, 1F, 1F, 1F).texOffs(16, 18).mirror(false).addBox(3F, 5F, -0.5F, 1F, 1F, 1F).texOffs(20, 18).mirror(false).addBox(4F, 5F, -0.5F, 1F, 1F, 1F).texOffs(24, 18).mirror(false).addBox(5F, 5F, -0.5F, 1F, 1F, 1F).texOffs(28, 18).mirror(false).addBox(-2F, 6F, -0.5F, 1F, 1F, 1F).texOffs(32, 18).mirror(false).addBox(-1F, 6F, -0.5F, 1F, 1F, 1F).texOffs(36, 18).mirror(false).addBox(0F, 6F, -0.5F, 1F, 1F, 1F).texOffs(40, 18).mirror(false).addBox(1F, 6F, -0.5F, 1F, 1F, 1F).texOffs(44, 18).mirror(false).addBox(2F, 6F, -0.5F, 1F, 1F, 1F).texOffs(48, 18).mirror(false).addBox(-2F, 7F, -0.5F, 1F, 1F, 1F).texOffs(52, 18).mirror(false).addBox(-1F, 7F, -0.5F, 1F, 1F, 1F).texOffs(56, 18).mirror(false).addBox(0F, 7F, -0.5F, 1F, 1F, 1F).texOffs(60, 18).mirror(false).addBox(1F, 7F, -0.5F, 1F, 1F, 1F).texOffs(0, 20).mirror(false).addBox(2F, 7F, -0.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition turtleShell() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("shell", CubeListBuilder.create().texOffs(7, 37).mirror(false).addBox(-9.5F, 3F, -10F, 19F, 20F, 6F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    private static LayerDefinition allayWings() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 14).mirror(false).addBox(0F, 1F, 0F, 0F, 5F, 8F), PartPose.offsetAndRotation(-0.5F, 0F, 0.6F, 0.43633F, -0.7854F, 0F));
        sides[0].addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 14).mirror(false).addBox(0F, 1F, 0F, 0F, 5F, 8F), PartPose.offsetAndRotation(0.5F, 0F, 0.6F, 0.43633F, 0.7854F, 0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    private static LayerDefinition vexWings() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("right_wing", CubeListBuilder.create().texOffs(16, 14).mirror(false).addBox(0F, 0F, 0F, 0F, 5F, 8F), PartPose.offsetAndRotation(-0.5F, 1F, 1F, 0.4712F, -0.95F, 0F));
        sides[0].addOrReplaceChild("left_wing", CubeListBuilder.create().texOffs(16, 14).mirror(true).addBox(0F, 0F, 0F, 0F, 5F, 8F), PartPose.offsetAndRotation(0.5F, 1F, 1F, 0.4712F, 0.95F, 0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    private static LayerDefinition shulkerShell() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-8F, -16F, -8F, 16F, 12F, 16F), PartPose.offsetAndRotation(0F, 24F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 28).mirror(false).addBox(-8F, -8F, -8F, 16F, 8F, 16F), PartPose.offsetAndRotation(0F, 24F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition phantomWings() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        PartDefinition p0 = sides[0].addOrReplaceChild("left_wing_base", CubeListBuilder.create().texOffs(23, 12).mirror(false).addBox(0F, 0F, 0F, 6F, 2F, 9F), PartPose.offsetAndRotation(2F, -2F, -8F, 0F, 0F, 0.1F));
        p0.addOrReplaceChild("left_wing_tip", CubeListBuilder.create().texOffs(16, 24).mirror(false).addBox(0F, 0F, 0F, 13F, 1F, 9F), PartPose.offsetAndRotation(6F, 0F, 0F, 0F, 0F, 0.1F));
        PartDefinition p2 = sides[0].addOrReplaceChild("right_wing_base", CubeListBuilder.create().texOffs(23, 12).mirror(true).addBox(-6F, 0F, 0F, 6F, 2F, 9F), PartPose.offsetAndRotation(-3F, -2F, -8F, 0F, 0F, -0.1F));
        p2.addOrReplaceChild("right_wing_tip", CubeListBuilder.create().texOffs(16, 24).mirror(true).addBox(-13F, 0F, 0F, 13F, 1F, 9F), PartPose.offsetAndRotation(-6F, 0F, 0F, 0F, 0F, -0.1F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition beeStinger() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("stinger", CubeListBuilder.create().texOffs(26, 7).mirror(false).addBox(0F, -1F, 5F, 0F, 1F, 2F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("stinger_cross", CubeListBuilder.create().texOffs(26, 7).mirror(false).addBox(0F, -0.5F, 5F, 0F, 1F, 2F), PartPose.offsetAndRotation(0F, -0.5F, 0F, 0F, 0F, 1.5708F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition parrotTail() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("tail", CubeListBuilder.create().texOffs(22, 1).mirror(false).addBox(-1.5F, -1F, -1F, 3F, 4F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 32, 32);
    }

    private static LayerDefinition camelTail() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("tail", CubeListBuilder.create().texOffs(122, 0).mirror(false).addBox(-1.5F, 0F, 0F, 3F, 14F, 0F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static LayerDefinition guardianSpikes() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("spike_0", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(-0F, 0F, -4.5F, -1.5708F, 0F, 0F));
        sides[0].addOrReplaceChild("spike_1", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(-2.89898F, 0F, -3.44179F, -1.5708F, 0.7F, 0F));
        sides[0].addOrReplaceChild("spike_2", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(2.89898F, 0F, -3.44179F, -1.5708F, -0.7F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition shulkerPlates() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-8F, -16F, -8F, 16F, 12F, 16F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition ravagerHide() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("hide", CubeListBuilder.create().texOffs(64, 10).mirror(false).addBox(-4F, 0F, -4F, 8F, 8F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static LayerDefinition silverfishCarapace() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("knee_plate", CubeListBuilder.create().texOffs(20, 0).mirror(false).addBox(-5F, 0F, -1.5F, 10F, 8F, 3F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("shin_plate", CubeListBuilder.create().texOffs(20, 11).mirror(false).addBox(-3F, 0F, -1.5F, 6F, 4F, 3F), PartPose.offsetAndRotation(0F, 8.5F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition ironGolemPlate() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("plate", CubeListBuilder.create().texOffs(37, 4).mirror(false).addBox(-3F, 0F, -2.5F, 6F, 6F, 5F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 128);
    }

    private static LayerDefinition elderGuardianSpikes() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("spike_0", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(-0F, 0F, -11.16667F, -1.5708F, 0F, 0F));
        sides[0].addOrReplaceChild("spike_1", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(-0F, 0F, 11.16667F, -1.5708F, 3.14159F, 0F));
        sides[1].addOrReplaceChild("outer_spike", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(11.16667F, 0F, 0F, -1.5708F, -1.5708F, 0F));
        sides[2].addOrReplaceChild("outer_spike", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-1F, -4.5F, -1F, 2F, 9F, 2F), PartPose.offsetAndRotation(-11.16667F, 0F, 0F, -1.5708F, 1.5708F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition striderFeet() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(0, 65).mirror(false).addBox(-2F, 0F, -2F, 4F, 6F, 4F).texOffs(0, 55).mirror(false).addBox(-2F, 6F, -2F, 4F, 0F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(0, 42).mirror(false).addBox(-2F, 0F, -2F, 4F, 6F, 4F).texOffs(0, 32).mirror(false).addBox(-2F, 6F, -2F, 4F, 0F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 128);
    }

    private static LayerDefinition rabbitFeet() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(26, 24).mirror(false).addBox(-1F, 5.5F, -3.7F, 2F, 1F, 7F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(8, 24).mirror(false).addBox(-1F, 5.5F, -3.7F, 2F, 1F, 7F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition slime() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("outer", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-4F, 16F, -4F, 8F, 8F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[0].addOrReplaceChild("inner", CubeListBuilder.create().texOffs(0, 16).mirror(false).addBox(-3F, 17F, -3F, 6F, 6F, 6F).texOffs(32, 0).mirror(false).addBox(-3.25F, 18F, -3.5F, 2F, 2F, 2F).texOffs(32, 4).mirror(false).addBox(1.25F, 18F, -3.5F, 2F, 2F, 2F).texOffs(32, 8).mirror(false).addBox(0F, 21F, -3.5F, 1F, 1F, 1F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 32);
    }

    private static LayerDefinition breezeWind() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[0].addOrReplaceChild("low_ring", CubeListBuilder.create().texOffs(0, 0).mirror(false).addBox(-3.5F, 0F, -3.5F, 7F, 2F, 7F), PartPose.offsetAndRotation(0F, 11F, 0F, 0F, 0.35F, 0F));
        sides[0].addOrReplaceChild("mid_ring", CubeListBuilder.create().texOffs(0, 10).mirror(false).addBox(-4F, 0F, -4F, 8F, 2F, 8F), PartPose.offsetAndRotation(0F, 8.6F, 0F, 0F, -0.4F, 0F));
        sides[0].addOrReplaceChild("top_ring", CubeListBuilder.create().texOffs(0, 21).mirror(false).addBox(-4.5F, 0F, -4.5F, 9F, 2F, 9F), PartPose.offsetAndRotation(0F, 6.2F, 0F, 0F, 0.75F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition frogLegs() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(2, 32).mirror(false).addBox(-4F, 0.01F, -4F, 8F, 0F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[1].addOrReplaceChild("thigh", CubeListBuilder.create().texOffs(14, 25).mirror(false).addBox(-1.5F, 0F, -2F, 3F, 3F, 4F), PartPose.offsetAndRotation(0F, -3F, 2.6F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("foot", CubeListBuilder.create().texOffs(18, 32).mirror(false).addBox(-4F, 0.01F, -4F, 8F, 0F, 8F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("thigh", CubeListBuilder.create().texOffs(0, 25).mirror(false).addBox(-1.5F, 0F, -2F, 3F, 3F, 4F), PartPose.offsetAndRotation(0F, -3F, 2.6F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 48, 48);
    }

    private static LayerDefinition piglinHooves() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("hoof", CubeListBuilder.create().texOffs(16, 57).mirror(false).addBox(-2F, 0F, -2F, 4F, 3F, 4F).texOffs(16, 48).mirror(false).addBox(-2F, 3F, -2F, 4F, 0F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("hoof", CubeListBuilder.create().texOffs(0, 25).mirror(false).addBox(-2F, 0F, -2F, 4F, 3F, 4F).texOffs(0, 16).mirror(false).addBox(-2F, 3F, -2F, 4F, 0F, 4F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 64, 64);
    }

    private static LayerDefinition hoglinHooves() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("hoof", CubeListBuilder.create().texOffs(41, 52).mirror(false).addBox(-3F, 0F, -3F, 6F, 4F, 6F).texOffs(41, 42).mirror(false).addBox(-3F, 4F, -3F, 6F, 0F, 6F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        sides[2].addOrReplaceChild("hoof", CubeListBuilder.create().texOffs(66, 52).mirror(false).addBox(-3F, 0F, -3F, 6F, 4F, 6F).texOffs(66, 42).mirror(false).addBox(-3F, 4F, -3F, 6F, 0F, 6F), PartPose.offsetAndRotation(0F, 0F, 0F, 0F, 0F, 0F));
        return LayerDefinition.create(mesh, 128, 64);
    }

    private static LayerDefinition spiderLegs() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition[] sides = roots(mesh);
        sides[1].addOrReplaceChild("front_leg", CubeListBuilder.create().texOffs(18, 0).mirror(true).addBox(-1F, -1F, -1F, 16F, 2F, 2F), PartPose.offsetAndRotation(6.66667F, 0F, -1F, 0F, 0.7854F, 0.7854F));
        sides[1].addOrReplaceChild("hind_leg", CubeListBuilder.create().texOffs(18, 0).mirror(true).addBox(-1F, -1F, -1F, 16F, 2F, 2F), PartPose.offsetAndRotation(6.66667F, 0F, 1F, 0F, -0.7854F, 0.7854F));
        sides[2].addOrReplaceChild("front_leg", CubeListBuilder.create().texOffs(18, 0).mirror(false).addBox(-15F, -1F, -1F, 16F, 2F, 2F), PartPose.offsetAndRotation(-6.66667F, 0F, -1F, 0F, -0.7854F, -0.7854F));
        sides[2].addOrReplaceChild("hind_leg", CubeListBuilder.create().texOffs(18, 0).mirror(false).addBox(-15F, -1F, -1F, 16F, 2F, 2F), PartPose.offsetAndRotation(-6.66667F, 0F, 1F, 0F, 0.7854F, -0.7854F));
        return LayerDefinition.create(mesh, 64, 32);
    }
}
