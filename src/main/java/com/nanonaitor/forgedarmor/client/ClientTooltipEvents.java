package com.nanonaitor.forgedarmor.client;

import com.mojang.datafixers.util.Either;
import com.nanonaitor.forgedarmor.AttachmentArmorTooltip;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ForgedArmor.ID, value = Dist.CLIENT)
public final class ClientTooltipEvents {
    private ClientTooltipEvents() {
    }

    @SubscribeEvent
    public static void gatherAttachmentLocation(RenderTooltipEvent.GatherComponents event) {
        AttachmentType type = AttachmentData.attachmentType(event.getItemStack());
        if (type == null || !type.enabled()) return;
        int index = Math.min(1, event.getTooltipElements().size());
        event.getTooltipElements().add(index, Either.right(new AttachmentArmorTooltip(type.slot())));
    }
}
