package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.entity.LivingEntity;

/** Uses Minecraft's animated Elytra model while the wings are forged into a chestplate. */
public final class ForgedElytraLayer<T extends LivingEntity, M extends HumanoidModel<T>> extends ElytraLayer<T, M> {
    public ForgedElytraLayer(RenderLayerParent<T, M> parent,
                             EntityModelSet models) {
        super(parent, models);
    }

    @Override
    public boolean shouldRender(ItemStack chest, T entity) {
        return AttachmentData.has(chest, AttachmentType.ELYTRA);
    }
}
