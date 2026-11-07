package com.nanonaitor.forgedarmor;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.AnvilBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.network.NetworkHooks;

public final class ArmorForgeBlock extends AnvilBlock {
    public ArmorForgeBlock(Properties properties) { super(properties); }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
                                 InteractionHand hand, BlockHitResult hit) {
        if (!level.isClientSide && player instanceof ServerPlayer serverPlayer) {
            NetworkHooks.openScreen(serverPlayer, new MenuProvider() {
                @Override public Component getDisplayName() { return Component.translatable("container.nanonaitor_forged_armor.armor_forge"); }
                @Override public AbstractContainerMenu createMenu(int id, Inventory inventory, Player owner) {
                    return new ArmorForgeMenu(id, inventory, pos);
                }
            }, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
