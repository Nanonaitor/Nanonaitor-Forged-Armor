package com.nanonaitor.forgedarmor.mixin;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityFluidMixin {
    @Inject(method = "canStandOnFluid", at = @At("HEAD"), cancellable = true)
    private void forgedArmor$standOnLava(FluidState fluid, CallbackInfoReturnable<Boolean> cir) {
        if ((Object) this instanceof Player player && fluid.is(FluidTags.LAVA)
                && AttachmentData.equipped(player).contains(AttachmentType.STRIDER_FEET))
            cir.setReturnValue(true);
    }
}
