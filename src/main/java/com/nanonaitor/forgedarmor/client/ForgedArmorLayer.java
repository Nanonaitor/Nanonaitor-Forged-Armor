package com.nanonaitor.forgedarmor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentSlot;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;

/** Renders forged attachments as moving parts of the player's equipped armor. */
public final class ForgedArmorLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends RenderLayer<T, M> {
    private final AttachmentModel model;

    public ForgedArmorLayer(RenderLayerParent<T, M> parent,
                            EntityModelSet models) {
        super(parent);
        this.model = new AttachmentModel(models);
    }

    @Override
    public void render(PoseStack pose, MultiBufferSource buffers, int light, T entity,
                       float limbSwing, float limbSwingAmount, float partialTick, float age,
                       float netHeadYaw, float headPitch) {
        if (entity.isInvisible() && !(entity instanceof ArmorStand)) return;
        for (AttachmentSlot slot : AttachmentSlot.values()) {
            ItemStack armor = entity.getItemBySlot(slot.armorSlot());
            AttachmentType type = AttachmentType.byId(AttachmentData.getId(armor, slot));
            AttachmentRigs.Rig rig = type == null ? null : AttachmentRigs.RIGS.get(type);
            if (rig != null) renderRig(rig, pose, buffers, light);
        }
    }

    private void renderRig(AttachmentRigs.Rig rig, PoseStack pose, MultiBufferSource buffers, int light) {
        M parent = getParentModel();
        switch (rig.anchor()) {
            case HEAD -> draw(rig, parent.head, AttachmentModel.Side.NONE, pose, buffers, light);
            case BODY -> draw(rig, parent.body, AttachmentModel.Side.NONE, pose, buffers, light);
            case LEGS -> {
                draw(rig, parent.leftLeg, AttachmentModel.Side.LEFT, pose, buffers, light);
                draw(rig, parent.rightLeg, AttachmentModel.Side.RIGHT, pose, buffers, light);
            }
        }
    }

    private void draw(AttachmentRigs.Rig rig, ModelPart anchor, AttachmentModel.Side side,
                      PoseStack pose, MultiBufferSource buffers, int light) {
        pose.pushPose();
        anchor.translateAndRotate(pose);
        RenderType type = rig.translucent()
                ? RenderType.entityTranslucent(rig.texture())
                : RenderType.entityCutoutNoCull(rig.texture());
        model.render(rig, side, pose, buffers.getBuffer(type), light, OverlayTexture.NO_OVERLAY);
        if (rig.glow() != null) {
            model.render(rig, side, pose, buffers.getBuffer(RenderType.eyes(rig.glow())),
                    LightTexture.FULL_BRIGHT, OverlayTexture.NO_OVERLAY);
        }
        pose.popPose();
    }
}
