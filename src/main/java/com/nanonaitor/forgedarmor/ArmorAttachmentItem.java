package com.nanonaitor.forgedarmor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public final class ArmorAttachmentItem extends Item {
    private final AttachmentType type;

    public ArmorAttachmentItem(AttachmentType type, Properties properties) {
        super(properties);
        this.type = type;
    }

    public AttachmentType type() { return type; }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.translatable("attachment.nanonaitor_forged_armor." + type.id() + ".description")
                .withStyle(ChatFormatting.GRAY));
    }
}
