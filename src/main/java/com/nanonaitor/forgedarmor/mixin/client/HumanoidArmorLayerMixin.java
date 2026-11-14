package com.nanonaitor.forgedarmor.mixin.client;

import com.mojang.blaze3d.vertex.PoseStack;
import com.nanonaitor.forgedarmor.client.MobHeadVisibility;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.HumanoidArmorLayer;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/** Skips drawing the helmet when a mob-head attachment is standing in for the wearer's head. */
@Mixin(HumanoidArmorLayer.class)
public abstract class HumanoidArmorLayerMixin {
    @Inject(method = "renderArmorPiece", at = @At("HEAD"), cancellable = true)
    private void forgedArmor$hideHelmetUnderMobHead(PoseStack pose, MultiBufferSource buffers, LivingEntity entity,
                                                   EquipmentSlot slot, int light, HumanoidModel<?> model,
                                                   CallbackInfo ci) {
        if (slot == EquipmentSlot.HEAD && MobHeadVisibility.wearsMobHead(entity)) ci.cancel();
    }
}
