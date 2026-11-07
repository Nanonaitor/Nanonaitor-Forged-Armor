package com.nanonaitor.forgedarmor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

/**
 * Low-resolution, deliberately Minecraft-shaped first-pass geometry for every
 * forged attachment. Each attachment is a separate child so it can be tuned or
 * replaced without changing saved armor data.
 */
public final class AttachmentModel {
    public static final ModelLayerLocation LAYER = new ModelLayerLocation(
            new ResourceLocation(ForgedArmor.ID, "armor_attachments"), "main");
    private static final CubeDeformation OVERLAY = new CubeDeformation(0.15F);
    private final ModelPart root;

    public AttachmentModel(ModelPart root) { this.root = root; }

    public void render(AttachmentType type, PoseStack pose, VertexConsumer consumer,
                       int light, int overlay, float red, float green, float blue, float alpha) {
        ModelPart part = root.getChild(type.id());
        part.render(pose, consumer, light, overlay, red, green, blue, alpha);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition mesh = new MeshDefinition();
        PartDefinition root = mesh.getRoot();

        // Helmet: two tapered block-built horns or ears.
        horns(root, AttachmentType.RAVAGER_HORN, 2.1F, 1.8F, true);
        horns(root, AttachmentType.GOAT_HORN, 1.5F, 2.8F, true);
        nose(root, AttachmentType.PILLAGER_NOSE);
        mask(root, AttachmentType.ZOMBIE_MASK, false);
        mask(root, AttachmentType.SKELETON_MASK, false);
        mask(root, AttachmentType.CREEPER_MASK, true);
        mask(root, AttachmentType.SPIDER_MASK, false);
        horns(root, AttachmentType.HOGLIN_HORN, 2.4F, 2.3F, false);
        ears(root, AttachmentType.BUNNY_EARS, 2.0F, 6.0F);
        dragonMask(root);
        wardenEars(root);

        // Chest and back pieces.
        heart(root, AttachmentType.WITCH_HEART, 2.8F, 3.0F);
        heart(root, AttachmentType.IRON_GOLEM_HEART, 3.5F, 3.5F);
        turtleShell(root);
        heart(root, AttachmentType.CREAKING_HEART, 3.2F, 4.0F);
        wings(root, AttachmentType.ALLAY_WINGS, 5.0F, 5.0F, false);
        wings(root, AttachmentType.VEX_WINGS, 4.2F, 6.0F, true);
        shell(root, AttachmentType.SHULKER_SHELL);
        heart(root, AttachmentType.WARDEN_HEART, 3.6F, 4.0F);
        phantomWings(root);
        core(root, AttachmentType.SNOW_GOLEM_ICE_CORE, 3.0F);
        core(root, AttachmentType.IRON_GOLEM_IRON_CORE, 3.6F);

        // Leggings: tails use the torso anchor; knee pieces are drawn on both legs.
        stinger(root);
        kneeSpikes(root, AttachmentType.GUARDIAN_SPIKES, 1.8F);
        featherTail(root);
        tail(root, AttachmentType.DONKEY_TAIL, 1.5F, 7.0F);
        tail(root, AttachmentType.CAMEL_TAIL, 1.8F, 6.0F);
        kneePlate(root, AttachmentType.SHULKER_PLATES, 4.4F, 2.2F);
        kneePlate(root, AttachmentType.RAVAGER_HIDE, 4.8F, 2.5F);
        kneePlate(root, AttachmentType.SILVERFISH_CARAPACE, 4.2F, 2.0F);
        kneePlate(root, AttachmentType.IRON_GOLEM_PLATE, 4.8F, 3.0F);

        // Boots: one model is rendered once for each leg.
        bootSpikes(root);
        foot(root, AttachmentType.STRIDER_FEET, 4.8F, 2.0F);
        foot(root, AttachmentType.RABBIT_FEET, 4.0F, 2.8F);
        foot(root, AttachmentType.SLIME, 4.6F, 1.6F);
        breeze(root);
        frogLeg(root);
        hoof(root, AttachmentType.PIGLIN_HOOVES, 3.8F);
        hoof(root, AttachmentType.HOGLIN_HOOVES, 4.5F);
        spiderLegs(root);

        return LayerDefinition.create(mesh, 64, 64);
    }

    private static PartDefinition part(PartDefinition root, AttachmentType type) {
        return root.addOrReplaceChild(type.id(), CubeListBuilder.create(), PartPose.ZERO);
    }

    private static void horns(PartDefinition root, AttachmentType type, float width, float height, boolean upright) {
        PartDefinition p = part(root, type);
        float z = upright ? 0.0F : -1.0F;
        p.addOrReplaceChild("left_base", CubeListBuilder.create().texOffs(0, 0)
                .addBox(2.7F, -9.6F, z - 1, width, height, 2), PartPose.rotation(0, 0, -0.22F));
        p.addOrReplaceChild("left_tip", CubeListBuilder.create().texOffs(8, 0)
                .addBox(3.4F, -11.2F, z - .65F, width * .55F, height, 1.3F), PartPose.rotation(0, 0, -0.32F));
        p.addOrReplaceChild("right_base", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.7F - width, -9.6F, z - 1, width, height, 2), PartPose.rotation(0, 0, 0.22F));
        p.addOrReplaceChild("right_tip", CubeListBuilder.create().texOffs(8, 0)
                .addBox(-3.4F - width * .55F, -11.2F, z - .65F, width * .55F, height, 1.3F), PartPose.rotation(0, 0, 0.32F));
    }

    private static void ears(PartDefinition root, AttachmentType type, float width, float height) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0)
                .addBox(1.2F, -14.0F, -1.0F, width, height, 2), PartPose.rotation(0, 0, 0.08F));
        p.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.2F - width, -14.0F, -1.0F, width, height, 2), PartPose.rotation(0, 0, -0.08F));
    }

    private static void nose(PartDefinition root, AttachmentType type) {
        part(root, type).addOrReplaceChild("nose", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.0F, -4.8F, -6.0F, 2, 2, 3), PartPose.ZERO);
    }

    private static void mask(PartDefinition root, AttachmentType type, boolean tall) {
        float h = tall ? 9.0F : 8.0F;
        part(root, type).addOrReplaceChild("mask", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.0F, -8.0F, -4.0F, 8, h, 8, OVERLAY), PartPose.ZERO);
    }

    private static void dragonMask(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.ENDER_DRAGON_MASK);
        p.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.5F, -8.5F, -4.5F, 9, 8, 9, OVERLAY), PartPose.ZERO);
        p.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(0, 18)
                .addBox(-3.0F, -4.5F, -8.0F, 6, 3, 4), PartPose.ZERO);
        p.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(20, 18)
                .addBox(2.2F, -11.0F, -1.0F, 2, 4, 2), PartPose.rotation(0, 0, -.18F));
        p.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(20, 18)
                .addBox(-4.2F, -11.0F, -1.0F, 2, 4, 2), PartPose.rotation(0, 0, .18F));
    }

    private static void wardenEars(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.WARDEN_EARS);
        for (int side : new int[]{-1, 1}) {
            String s = side < 0 ? "left" : "right";
            p.addOrReplaceChild(s + "_stem", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(side < 0 ? -7 : 5, -8, -1, 2, 5, 2), PartPose.rotation(0, 0, side * .45F));
            p.addOrReplaceChild(s + "_tip", CubeListBuilder.create().texOffs(8, 0)
                    .addBox(side < 0 ? -9 : 6, -10, -1, 3, 3, 2), PartPose.rotation(0, 0, side * .35F));
        }
    }

    private static void heart(PartDefinition root, AttachmentType type, float width, float height) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-width, 2.2F, -3.4F, width, height, 2.4F), PartPose.rotation(0, 0, .18F));
        p.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0)
                .addBox(0, 2.2F, -3.4F, width, height, 2.4F), PartPose.rotation(0, 0, -.18F));
        p.addOrReplaceChild("point", CubeListBuilder.create().texOffs(0, 8)
                .addBox(-1.6F, 4.2F, -3.2F, 3.2F, 3.2F, 2), PartPose.rotation(0, 0, .78F));
    }

    private static void core(PartDefinition root, AttachmentType type, float size) {
        part(root, type).addOrReplaceChild("core", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-size / 2, 3.2F, -3.2F, size, size, 2.2F), PartPose.rotation(0, 0, .78F));
    }

    private static void turtleShell(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.TURTLE_SHELL);
        p.addOrReplaceChild("shell", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-5.0F, 0.8F, 1.5F, 10, 10, 3), PartPose.ZERO);
        p.addOrReplaceChild("ridge", CubeListBuilder.create().texOffs(0, 14)
                .addBox(-3.5F, 2.0F, 4.0F, 7, 7, 1), PartPose.ZERO);
    }

    private static void wings(PartDefinition root, AttachmentType type, float span, float height, boolean swept) {
        PartDefinition p = part(root, type);
        float angle = swept ? .42F : .18F;
        p.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0)
                .addBox(3.0F, 1.0F, 2.0F, span, height, 1), PartPose.rotation(0, .12F, -angle));
        p.addOrReplaceChild("left_tip", CubeListBuilder.create().texOffs(0, 8)
                .addBox(6.0F, 4.0F, 2.1F, span, height * .65F, 1), PartPose.rotation(0, .1F, angle));
        p.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-3.0F - span, 1.0F, 2.0F, span, height, 1), PartPose.rotation(0, -.12F, angle));
        p.addOrReplaceChild("right_tip", CubeListBuilder.create().texOffs(0, 8)
                .addBox(-6.0F - span, 4.0F, 2.1F, span, height * .65F, 1), PartPose.rotation(0, -.1F, -angle));
    }

    private static void phantomWings(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.PHANTOM_WINGS);
        p.addOrReplaceChild("left", CubeListBuilder.create().texOffs(0, 0)
                .addBox(3.0F, 1.0F, 1.8F, 9, 1, 5), PartPose.rotation(.1F, -.15F, -.12F));
        p.addOrReplaceChild("right", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-12.0F, 1.0F, 1.8F, 9, 1, 5), PartPose.rotation(.1F, .15F, .12F));
    }

    private static void shell(PartDefinition root, AttachmentType type) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4.5F, 2.0F, 1.8F, 9, 5, 3), PartPose.ZERO);
        p.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 10)
                .addBox(-4.5F, -1.0F, 1.8F, 9, 4, 3), PartPose.rotation(-.2F, 0, 0));
    }

    private static void stinger(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.BEE_STINGER);
        p.addOrReplaceChild("base", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.5F, 9.0F, 2.0F, 3, 3, 3), PartPose.rotation(.35F, 0, 0));
        p.addOrReplaceChild("tip", CubeListBuilder.create().texOffs(0, 6)
                .addBox(-.7F, 11.0F, 4.0F, 1.4F, 1.4F, 4), PartPose.rotation(.55F, 0, 0));
    }

    private static void featherTail(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.PARROT_TAIL);
        p.addOrReplaceChild("middle", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-1.0F, 8.0F, 2.0F, 2, 9, 1), PartPose.rotation(.25F, 0, 0));
        p.addOrReplaceChild("left", CubeListBuilder.create().texOffs(5, 0)
                .addBox(-2.5F, 8.0F, 2.0F, 1.5F, 7, 1), PartPose.rotation(.2F, 0, .12F));
        p.addOrReplaceChild("right", CubeListBuilder.create().texOffs(5, 0)
                .addBox(1.0F, 8.0F, 2.0F, 1.5F, 7, 1), PartPose.rotation(.2F, 0, -.12F));
    }

    private static void tail(PartDefinition root, AttachmentType type, float width, float length) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("upper", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-width / 2, 8.0F, 2.0F, width, length * .55F, width), PartPose.rotation(.4F, 0, 0));
        p.addOrReplaceChild("lower", CubeListBuilder.create().texOffs(6, 0)
                .addBox(-width * .65F, 11.0F, 4.0F, width * 1.3F, length * .55F, width * 1.3F), PartPose.rotation(.15F, 0, 0));
    }

    private static void kneePlate(PartDefinition root, AttachmentType type, float width, float height) {
        part(root, type).addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-width / 2, 4.0F, -2.8F, width, height, 1.5F), PartPose.ZERO);
    }

    private static void kneeSpikes(PartDefinition root, AttachmentType type, float length) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("plate", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.2F, 4.0F, -2.8F, 4.4F, 2.5F, 1.3F), PartPose.ZERO);
        p.addOrReplaceChild("spike", CubeListBuilder.create().texOffs(0, 6)
                .addBox(-.6F, 4.7F, -2.8F - length, 1.2F, 1.2F, length), PartPose.rotation(-.18F, 0, 0));
    }

    private static void bootSpikes(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.ELDER_GUARDIAN_SPIKES);
        p.addOrReplaceChild("cuff", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.4F, 8.0F, -2.4F, 4.8F, 2.2F, 4.8F), PartPose.ZERO);
        p.addOrReplaceChild("spike", CubeListBuilder.create().texOffs(0, 10)
                .addBox(-.6F, 7.3F, -5.0F, 1.2F, 1.2F, 3), PartPose.rotation(-.25F, 0, 0));
    }

    private static void foot(PartDefinition root, AttachmentType type, float width, float height) {
        part(root, type).addOrReplaceChild("foot", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-width / 2, 9.5F, -3.5F, width, height, 6), PartPose.ZERO);
    }

    private static void hoof(PartDefinition root, AttachmentType type, float width) {
        PartDefinition p = part(root, type);
        p.addOrReplaceChild("hoof", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-width / 2, 9.5F, -3.2F, width, 2.7F, 5.5F), PartPose.ZERO);
        p.addOrReplaceChild("split", CubeListBuilder.create().texOffs(0, 9)
                .addBox(-.3F, 10.2F, -3.6F, .6F, 2, 1), PartPose.ZERO);
    }

    private static void breeze(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.BREEZE_WIND);
        p.addOrReplaceChild("ring", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-3.0F, 7.8F, -3.0F, 6, 1, 6), PartPose.rotation(0, .35F, 0));
        p.addOrReplaceChild("gust", CubeListBuilder.create().texOffs(0, 8)
                .addBox(-2.2F, 9.0F, -2.2F, 4.4F, 1, 4.4F), PartPose.rotation(0, -.35F, 0));
    }

    private static void frogLeg(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.FROG_LEGS);
        p.addOrReplaceChild("ankle", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-2.3F, 8.0F, -2.2F, 4.6F, 3, 4.4F), PartPose.ZERO);
        p.addOrReplaceChild("webbed_foot", CubeListBuilder.create().texOffs(0, 9)
                .addBox(-3.0F, 10.0F, -5.0F, 6, 1.5F, 7), PartPose.ZERO);
    }

    private static void spiderLegs(PartDefinition root) {
        PartDefinition p = part(root, AttachmentType.SPIDER_LEGS);
        for (int side : new int[]{-1, 1}) {
            String s = side < 0 ? "left" : "right";
            p.addOrReplaceChild(s + "_upper", CubeListBuilder.create().texOffs(0, 0)
                    .addBox(side < 0 ? -5 : 2, 8.0F, -1.0F, 3, 1, 1), PartPose.rotation(0, 0, side * .45F));
            p.addOrReplaceChild(s + "_lower", CubeListBuilder.create().texOffs(0, 3)
                    .addBox(side < 0 ? -6 : 5, 9.0F, -1.0F, 1, 4, 1), PartPose.rotation(0, 0, side * -.25F));
        }
    }
}
