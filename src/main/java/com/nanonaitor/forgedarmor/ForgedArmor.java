package com.nanonaitor.forgedarmor;

import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import com.mojang.serialization.Codec;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.registries.ForgeRegistries.Keys;

import java.util.EnumMap;
import java.util.Map;

@Mod(ForgedArmor.ID)
public final class ForgedArmor {
    public static final String ID = "nanonaitor_forged_armor";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ID);
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Registries.MENU, ID);
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, ID);

    public static final RegistryObject<Block> ARMOR_FORGE = BLOCKS.register("armor_forge", () ->
            new ArmorForgeBlock(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_GREEN)
                    .strength(5.0F, 1200.0F).sound(SoundType.ANVIL).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistryObject<Item> ARMOR_FORGE_ITEM = ITEMS.register("armor_forge", () ->
            new BlockItem(ARMOR_FORGE.get(), new Item.Properties()));
    public static final RegistryObject<MenuType<ArmorForgeMenu>> ARMOR_FORGE_MENU = MENUS.register("armor_forge", () ->
            IForgeMenuType.create(ArmorForgeMenu::new));
    public static final RegistryObject<Codec<EndShipMaskLootModifier>> END_SHIP_MASK_LOOT =
            LOOT_MODIFIERS.register("end_ship_mask", () -> EndShipMaskLootModifier.CODEC);
    public static final Map<AttachmentType, RegistryObject<Item>> ATTACHMENTS = new EnumMap<>(AttachmentType.class);

    static {
        for (AttachmentType type : AttachmentType.values()) {
            ATTACHMENTS.put(type, ITEMS.register(type.id(), () -> new ArmorAttachmentItem(type, new Item.Properties())));
        }
    }

    public ForgedArmor() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        BLOCKS.register(modBus);
        ITEMS.register(modBus);
        MENUS.register(modBus);
        LOOT_MODIFIERS.register(modBus);
        modBus.addListener(this::creativeTab);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ForgedArmorConfig.SPEC);
        ForgedArmorNetwork.init();
        MinecraftForge.EVENT_BUS.register(new ForgedArmorEvents());
    }

    private void creativeTab(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) event.accept(ARMOR_FORGE_ITEM.get());
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS)
            for (RegistryObject<Item> item : ATTACHMENTS.values()) event.accept(item.get());
    }
}
