package com.nanonaitor.forgedarmor.mixin;

import com.nanonaitor.forgedarmor.AttachmentData;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Player.class)
public abstract class PlayerElytraMixin {
    @Inject(method = "tryToStartFallFlying", at = @At("HEAD"), cancellable = true)
    private void forgedArmor$startAttachedElytra(CallbackInfoReturnable<Boolean> cir) {
        Player player = (Player) (Object) this;
        ItemStack chest = player.getItemBySlot(EquipmentSlot.CHEST);
        if (!AttachmentData.canFlyWith(chest)) return;
        if (!player.onGround() && !player.isFallFlying() && !player.isInWater()
                && !player.hasEffect(MobEffects.LEVITATION)) {
            player.startFallFlying();
            cir.setReturnValue(true);
        }
    }
}
