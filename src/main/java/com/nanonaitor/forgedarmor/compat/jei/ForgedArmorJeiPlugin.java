package com.nanonaitor.forgedarmor.compat.jei;

import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentInfo;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmor;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

/** Loaded by JEI only when it is installed: adds an info page to the forge and to every attachment. */
@JeiPlugin
public final class ForgedArmorJeiPlugin implements IModPlugin {
    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(ForgedArmor.ID, "jei");
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addIngredientInfo(new ItemStack(ForgedArmor.ARMOR_FORGE_ITEM.get()), VanillaTypes.ITEM_STACK,
                Component.translatable("jei.nanonaitor_forged_armor.armor_forge"));
        for (AttachmentType type : AttachmentType.values()) {
            if (!type.enabled()) continue;
            registration.addIngredientInfo(AttachmentData.attachmentStack(type), VanillaTypes.ITEM_STACK,
                    AttachmentInfo.lines(type).toArray(Component[]::new));
        }
    }
}
