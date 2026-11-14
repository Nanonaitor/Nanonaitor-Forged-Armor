package com.nanonaitor.forgedarmor;

import net.minecraft.world.inventory.tooltip.TooltipComponent;

/** Data sent to the client tooltip renderer for an attachment's armor location. */
public record AttachmentArmorTooltip(AttachmentSlot slot) implements TooltipComponent {
}
