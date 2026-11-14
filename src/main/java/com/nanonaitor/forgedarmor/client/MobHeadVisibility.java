package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentSlot;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderLivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.EnumSet;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Set;

/**
 * A mob-head attachment replaces the wearer's head: while one is equipped the player's head and hat layer are hidden
 * here, and the helmet itself is skipped by {@code HumanoidArmorLayerMixin}.
 */
@Mod.EventBusSubscriber(modid = ForgedArmor.ID, value = Dist.CLIENT)
public final class MobHeadVisibility {
    private static final Set<AttachmentType> MOB_HEADS = EnumSet.of(AttachmentType.ZOMBIE_MASK,
            AttachmentType.SKELETON_MASK, AttachmentType.CREEPER_MASK, AttachmentType.SPIDER_MASK,
            AttachmentType.ENDER_DRAGON_MASK);
    /** Hat visibility from before we hid it, so it can be put back after the entity renders. */
    private static final Map<HumanoidModel<?>, Boolean> HIDDEN = new IdentityHashMap<>();

    private MobHeadVisibility() {
    }

    public static boolean wearsMobHead(LivingEntity entity) {
        AttachmentType type = AttachmentType.byId(
                AttachmentData.getId(entity.getItemBySlot(EquipmentSlot.HEAD), AttachmentSlot.FACE));
        return type != null && MOB_HEADS.contains(type);
    }

    @SubscribeEvent
    public static void hideHead(RenderLivingEvent.Pre<?, ?> event) {
        if (!(event.getRenderer().getModel() instanceof HumanoidModel<?> model)) return;
        if (!wearsMobHead(event.getEntity())) return;
        HIDDEN.put(model, model.hat.visible);
        model.head.visible = false;
        model.hat.visible = false;
    }

    @SubscribeEvent
    public static void restoreHead(RenderLivingEvent.Post<?, ?> event) {
        if (!(event.getRenderer().getModel() instanceof HumanoidModel<?> model)) return;
        Boolean hat = HIDDEN.remove(model);
        if (hat == null) return;
        model.head.visible = true;
        model.hat.visible = hat;
    }
}
