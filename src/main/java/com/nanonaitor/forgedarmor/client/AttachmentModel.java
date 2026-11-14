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

    /** What the wearer is doing this frame, used to animate wings, tails and wind rings. */
    public record Motion(float age, float limbSwing, float limbSwingAmount, boolean airborne) {
        public static final Motion STILL = new Motion(0, 0, 0, false);
    }

    private final Map<AttachmentType, ModelPart> roots = new EnumMap<>(AttachmentType.class);

    public AttachmentModel(EntityModelSet models) {
        for (AttachmentRigs.Rig rig : AttachmentRigs.RIGS.values()) roots.put(rig.type(), models.bakeLayer(rig.layer()));
    }

    /** Applies the rig's placement inside the already-anchored pose and draws the shared and side-specific parts. */
    public void render(AttachmentRigs.Rig rig, Side side, Motion motion, PoseStack pose, VertexConsumer consumer,
                       int light, int overlay) {
        ModelPart root = roots.get(rig.type());
        if (root == null) return;
        animate(rig.type(), root.getChild("both"), motion);
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

    private static void animate(AttachmentType type, ModelPart both, Motion m) {
        switch (type) {
            case ALLAY_WINGS, VEX_WINGS -> {
                // Slow idle drift on the ground, fast flapping in the air like the mobs themselves.
                float flap = m.airborne() ? (float) Math.sin(m.age() * 0.9F) * 0.5F : (float) Math.sin(m.age() * 0.12F) * 0.1F;
                swing(both, "right_wing", 0, -flap, 0);
                swing(both, "left_wing", 0, flap, 0);
            }
            case PHANTOM_WINGS -> {
                float flap = (float) Math.cos(m.age() * (m.airborne() ? 0.35F : 0.08F)) * (m.airborne() ? 0.35F : 0.08F);
                swing(both, "left_wing_base", 0, 0, flap);
                swing(both, "right_wing_base", 0, 0, -flap);
                if (both.hasChild("left_wing_base")) swing(both.getChild("left_wing_base"), "left_wing_tip", 0, 0, flap);
                if (both.hasChild("right_wing_base")) swing(both.getChild("right_wing_base"), "right_wing_tip", 0, 0, -flap);
            }
            case PARROT_TAIL, CAMEL_TAIL -> {
                float walk = (float) Math.cos(m.limbSwing() * 0.6662F) * 0.5F * m.limbSwingAmount();
                float idle = (float) Math.sin(m.age() * 0.08F) * 0.06F;
                swing(both, "tail", walk + idle, 0, (float) Math.sin(m.age() * 0.05F) * 0.08F);
            }
            case BEE_STINGER -> swing(both, "stinger", (float) Math.sin(m.age() * 0.2F) * 0.08F, 0, 0);
            case BREEZE_WIND -> {
                swing(both, "low_ring", 0, m.age() * 0.25F, 0);
                swing(both, "mid_ring", 0, -m.age() * 0.18F, 0);
                swing(both, "top_ring", 0, m.age() * 0.12F, 0);
            }
            case WARDEN_EARS -> {
                float twitch = (float) Math.sin(m.age() * 0.1F) * 0.05F;
                swing(both, "right_tendril", 0, twitch, 0);
                swing(both, "left_tendril", 0, -twitch, 0);
            }
            default -> {
            }
        }
    }

    /** Sets a child's rotation to its baked pose plus the given offsets (radians). */
    private static void swing(ModelPart parent, String name, float x, float y, float z) {
        if (!parent.hasChild(name)) return;
        ModelPart part = parent.getChild(name);
        part.resetPose();
        part.xRot += x;
        part.yRot += y;
        part.zRot += z;
    }
}
