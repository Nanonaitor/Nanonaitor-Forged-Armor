package com.nanonaitor.forgedarmor.mixin;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityElytraMixin {
    @Inject(method = "updateFallFlying", at = @At("HEAD"), cancellable = true)
    private void forgedArmor$tickAttachedElytra(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity) (Object) this;
        if (!(entity instanceof Player player)) return;
        ItemStack chest = entity.getItemBySlot(EquipmentSlot.CHEST);
        if (!entity.isFallFlying() || !AttachmentData.has(chest, AttachmentType.ELYTRA)) return;

        boolean continueFlying = !entity.onGround() && !entity.isPassenger()
                && !entity.hasEffect(MobEffects.LEVITATION) && AttachmentData.canFlyWith(chest);
        if (!continueFlying) {
            player.stopFallFlying();
            ci.cancel();
            return;
        }

        if (!entity.level().isClientSide) {
            int nextFlightTick = entity.getFallFlyingTicks() + 1;
            if (nextFlightTick % 10 == 0) {
                if (nextFlightTick % 20 == 0)
                    chest.hurtAndBreak(1, entity, wearer -> wearer.broadcastBreakEvent(EquipmentSlot.CHEST));
                entity.gameEvent(GameEvent.ELYTRA_GLIDE);
            }
        }
        ci.cancel();
    }
}
