package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.EntityModelSet;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.entity.RenderLayerParent;
import net.minecraft.client.renderer.entity.layers.ElytraLayer;
import net.minecraft.world.item.ItemStack;

/** Uses Minecraft's animated Elytra model while the wings are forged into a chestplate. */
public final class ForgedElytraLayer extends ElytraLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> {
    public ForgedElytraLayer(RenderLayerParent<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parent,
                             EntityModelSet models) {
        super(parent, models);
    }

    @Override
    public boolean shouldRender(ItemStack chest, AbstractClientPlayer player) {
        return AttachmentData.has(chest, AttachmentType.ELYTRA);
    }
}
