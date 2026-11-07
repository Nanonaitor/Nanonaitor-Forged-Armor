package com.nanonaitor.forgedarmor;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.LootModifier;
import org.jetbrains.annotations.NotNull;

public final class EndShipMaskLootModifier extends LootModifier {
    public static final Codec<EndShipMaskLootModifier> CODEC = RecordCodecBuilder.create(instance ->
            codecStart(instance).apply(instance, EndShipMaskLootModifier::new));
    private static final ResourceLocation END_CITY = new ResourceLocation("minecraft", "chests/end_city_treasure");

    public EndShipMaskLootModifier(LootItemCondition[] conditions) { super(conditions); }

    @Override
    protected @NotNull ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> loot, LootContext context) {
        if (ForgedArmorConfig.ENABLE_END_SHIP_MASK.get()
                && END_CITY.equals(context.getQueriedLootTableId())
                && context.getRandom().nextDouble() < ForgedArmorConfig.END_SHIP_MASK_CHANCE.get())
            loot.add(new ItemStack(ForgedArmor.ATTACHMENTS.get(AttachmentType.ENDER_DRAGON_MASK).get()));
        return loot;
    }

    @Override public Codec<? extends net.minecraftforge.common.loot.IGlobalLootModifier> codec() { return CODEC; }
}
