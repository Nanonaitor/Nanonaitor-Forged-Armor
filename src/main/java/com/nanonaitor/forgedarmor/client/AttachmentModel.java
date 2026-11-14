package com.nanonaitor.forgedarmor.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.model.geom.ModelPart;
import org.joml.Quaternionf;

import java.util.EnumMap;
import java.util.Map;

/**
 * Baked equipped models for every attachment. Geometry and placement come from {@link AttachmentRigs}, which is
 * generated from the vanilla source-mob models by {@code tools/attachment_rigs.py}.
 */
public final class AttachmentModel {
    /** Which leg a leg-anchored rig is being drawn on; head and body rigs always use {@link #NONE}. */
    public enum Side { NONE, LEFT, RIGHT }

    private final Map<AttachmentType, ModelPart> roots = new EnumMap<>(AttachmentType.class);

    public AttachmentModel(EntityModelSet models) {
        for (AttachmentRigs.Rig rig : AttachmentRigs.RIGS.values()) roots.put(rig.type(), models.bakeLayer(rig.layer()));
    }

    /** Applies the rig's placement inside the already-anchored pose and draws the shared and side-specific parts. */
    public void render(AttachmentRigs.Rig rig, Side side, PoseStack pose, VertexConsumer consumer,
                       int light, int overlay) {
        ModelPart root = roots.get(rig.type());
        if (root == null) return;
        float[] src = rig.src(), dst = rig.dst(), rot = rig.rotation(), scale = rig.scale();
        pose.pushPose();
        pose.translate(dst[0] / 16.0F, dst[1] / 16.0F, dst[2] / 16.0F);
        pose.scale(scale[0], scale[1], scale[2]);
        if (rot[0] != 0 || rot[1] != 0 || rot[2] != 0) pose.mulPose(new Quaternionf().rotationZYX(rot[2], rot[1], rot[0]));
        pose.translate(-src[0] / 16.0F, -src[1] / 16.0F, -src[2] / 16.0F);
        root.getChild("both").render(pose, consumer, light, overlay);
        if (side == Side.LEFT) root.getChild("left").render(pose, consumer, light, overlay);
        if (side == Side.RIGHT) root.getChild("right").render(pose, consumer, light, overlay);
        pose.popPose();
    }
}
