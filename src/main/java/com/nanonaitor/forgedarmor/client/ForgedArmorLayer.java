package com.nanonaitor.forgedarmor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentSlot;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ItemStack;

import java.util.EnumMap;
import java.util.Map;

/** Renders forged attachments as moving parts of the player's equipped armor. */
public final class ForgedArmorLayer extends RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    private static final Map<AttachmentType, ResourceLocation> TEXTURES = makeTextures();
    private final AttachmentModel model;

    public ForgedArmorLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                            EntityModelSet models) {
        super(parent);
        this.model = new AttachmentModel(models.bakeLayer(AttachmentModel.LAYER));
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffers, int light, AbstractClientPlayer player,
                       float limbSwing, float limbSwingAmount, float partialTick, float age,
                       float netHeadYaw, float headPitch) {
        if (player.isInvisible()) return;
        for (AttachmentSlot slot : AttachmentSlot.values()) {
            ItemStack armor = player.getItemBySlot(slot.armorSlot());
            AttachmentType type = AttachmentType.byId(AttachmentData.getId(armor, slot));
            if (type != null && type != AttachmentType.ELYTRA) renderPart(type, pose, buffers, light);
        }
    }

    private void renderPart(AttachmentType type, PoseStack pose, MultiBufferSource buffers, int light) {
        VertexConsumer consumer = buffers.getBuffer(RenderType.entityCutoutNoCull(TEXTURES.get(type)));
        pose.pushPose();
        switch (type.slot()) {
            case HORNS, FACE -> getParentModel().head.translateAndRotate(pose);
            case CHEST, BACK, TAIL -> getParentModel().body.translateAndRotate(pose);
            case KNEES -> {
                renderOnLeg(type, getParentModel().leftLeg, pose, consumer, light);
                renderOnLeg(type, getParentModel().rightLeg, pose, consumer, light);
                pose.popPose();
                return;
            }
            case SOLES, TOP -> {
                renderOnLeg(type, getParentModel().leftLeg, pose, consumer, light);
                renderOnLeg(type, getParentModel().rightLeg, pose, consumer, light);
                pose.popPose();
                return;
            }
        }
        model.render(type, pose, consumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        pose.popPose();
    }

    private void renderOnLeg(AttachmentType type, net.minecraft.client.model.geom.ModelPart leg,
                             PoseStack pose, VertexConsumer consumer, int light) {
        pose.pushPose();
        leg.translateAndRotate(pose);
        model.render(type, pose, consumer, light, OverlayTexture.NO_OVERLAY, 1, 1, 1, 1);
        pose.popPose();
    }

    private static Map<AttachmentType, ResourceLocation> makeTextures() {
        Map<AttachmentType, ResourceLocation> m = new EnumMap<>(AttachmentType.class);
        put(m, "ravager/ravager.png", AttachmentType.RAVAGER_HORN, AttachmentType.RAVAGER_HIDE);
        put(m, "goat/goat.png", AttachmentType.GOAT_HORN);
        put(m, "illager/pillager.png", AttachmentType.PILLAGER_NOSE);
        put(m, "zombie/zombie.png", AttachmentType.ZOMBIE_MASK);
        put(m, "skeleton/skeleton.png", AttachmentType.SKELETON_MASK);
        put(m, "creeper/creeper.png", AttachmentType.CREEPER_MASK);
        put(m, "spider/spider.png", AttachmentType.SPIDER_MASK, AttachmentType.SPIDER_LEGS);
        put(m, "hoglin/hoglin.png", AttachmentType.HOGLIN_HORN, AttachmentType.HOGLIN_HOOVES);
        put(m, "rabbit/brown.png", AttachmentType.BUNNY_EARS, AttachmentType.RABBIT_FEET);
        put(m, "enderdragon/dragon.png", AttachmentType.ENDER_DRAGON_MASK);
        put(m, "warden/warden.png", AttachmentType.WARDEN_EARS, AttachmentType.WARDEN_HEART);
        put(m, "witch.png", AttachmentType.WITCH_HEART);
        put(m, "iron_golem/iron_golem.png", AttachmentType.IRON_GOLEM_HEART,
                AttachmentType.IRON_GOLEM_IRON_CORE, AttachmentType.IRON_GOLEM_PLATE);
        put(m, "turtle/big_sea_turtle.png", AttachmentType.TURTLE_SHELL);
        put(m, "cow/brown_mooshroom.png", AttachmentType.CREAKING_HEART);
        put(m, "allay/allay.png", AttachmentType.ALLAY_WINGS);
        put(m, "illager/vex.png", AttachmentType.VEX_WINGS);
        put(m, "shulker/shulker.png", AttachmentType.SHULKER_SHELL, AttachmentType.SHULKER_PLATES);
        put(m, "phantom.png", AttachmentType.PHANTOM_WINGS, AttachmentType.BREEZE_WIND);
        put(m, "snow_golem.png", AttachmentType.SNOW_GOLEM_ICE_CORE);
        put(m, "bee/bee.png", AttachmentType.BEE_STINGER);
        put(m, "guardian.png", AttachmentType.GUARDIAN_SPIKES);
        put(m, "parrot/parrot_blue.png", AttachmentType.PARROT_TAIL);
        put(m, "camel/camel.png", AttachmentType.CAMEL_TAIL);
        put(m, "silverfish.png", AttachmentType.SILVERFISH_CARAPACE);
        put(m, "guardian_elder.png", AttachmentType.ELDER_GUARDIAN_SPIKES);
        put(m, "strider/strider.png", AttachmentType.STRIDER_FEET);
        put(m, "slime/slime.png", AttachmentType.SLIME);
        put(m, "frog/temperate_frog.png", AttachmentType.FROG_LEGS);
        put(m, "piglin/piglin.png", AttachmentType.PIGLIN_HOOVES);
        return m;
    }

    private static void put(Map<AttachmentType, ResourceLocation> map, String path, AttachmentType... types) {
        ResourceLocation texture = new ResourceLocation("minecraft", "textures/entity/" + path);
        for (AttachmentType type : types) map.put(type, texture);
    }
}
