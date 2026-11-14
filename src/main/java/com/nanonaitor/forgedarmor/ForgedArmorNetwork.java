package com.nanonaitor.forgedarmor;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.protocol.game.ClientboundSetEntityMotionPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.Set;
import java.util.function.Supplier;

public final class ForgedArmorNetwork {
    private static final String VERSION = "1";
    private static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(ForgedArmor.ID, "main"), () -> VERSION, VERSION::equals, VERSION::equals);

    public static void init() {
        CHANNEL.messageBuilder(AbilityInput.class, 0, NetworkDirection.PLAY_TO_SERVER)
                .encoder(AbilityInput::encode).decoder(AbilityInput::decode).consumerMainThread(AbilityInput::handle).add();
    }

    public static void send(AbilityAction action) { CHANNEL.sendToServer(new AbilityInput(action)); }

    public enum AbilityAction { DOUBLE_JUMP, DASH_FORWARD, DASH_BACK, DASH_LEFT, DASH_RIGHT, OPEN_STORAGE, GLIDE }

    public record AbilityInput(AbilityAction action) {
        static void encode(AbilityInput message, FriendlyByteBuf buffer) { buffer.writeEnum(message.action); }
        static AbilityInput decode(FriendlyByteBuf buffer) { return new AbilityInput(buffer.readEnum(AbilityAction.class)); }
        static void handle(AbilityInput message, Supplier<NetworkEvent.Context> supplier) {
            ServerPlayer player = supplier.get().getSender();
            if (player == null) return;
            Set<AttachmentType> attachments = AttachmentData.equipped(player);
            switch (message.action) {
                case DOUBLE_JUMP -> {
                    if ((attachments.contains(AttachmentType.ALLAY_WINGS) || attachments.contains(AttachmentType.VEX_WINGS))
                            && !player.onGround() && !player.getPersistentData().getBoolean("ForgedArmorAirJumpUsed")) {
                        player.setDeltaMovement(player.getDeltaMovement().x, 0.55, player.getDeltaMovement().z);
                        player.getPersistentData().putBoolean("ForgedArmorAirJumpUsed", true);
                        player.hasImpulse = true;
                        player.hurtMarked = true;
                        player.connection.send(new ClientboundSetEntityMotionPacket(player));
                    }
                }
                case DASH_FORWARD, DASH_BACK, DASH_LEFT, DASH_RIGHT -> {
                    if (!attachments.contains(AttachmentType.BREEZE_WIND)) break;
                    long now = player.level().getGameTime();
                    if (now < player.getPersistentData().getLong("ForgedArmorDashReady")) break;
                    player.getPersistentData().putLong("ForgedArmorDashReady", now + 20);
                    double yaw = Math.toRadians(player.getYRot());
                    double angle = switch (message.action) {
                        case DASH_BACK -> yaw + Math.PI;
                        case DASH_LEFT -> yaw - Math.PI / 2;
                        case DASH_RIGHT -> yaw + Math.PI / 2;
                        default -> yaw;
                    };
                    player.setDeltaMovement(-Math.sin(angle) * 1.25, Math.max(0.08, player.getDeltaMovement().y), Math.cos(angle) * 1.25);
                    player.hasImpulse = true;
                    player.hurtMarked = true;
                }
                case OPEN_STORAGE -> {
                    if (attachments.contains(AttachmentType.SHULKER_SHELL))
                        player.openMenu(new SimpleMenuProvider((id, inventory, owner) ->
                                ChestMenu.threeRows(id, inventory, player.getEnderChestInventory()),
                                Component.translatable("container.enderchest")));
                }
                case GLIDE -> {
                    if (attachments.contains(AttachmentType.PHANTOM_WINGS) && !player.onGround()
                            && !player.isInWater() && player.getDeltaMovement().y < -0.05) {
                        Vec3 motion = player.getDeltaMovement();
                        player.setDeltaMovement(motion.x * 1.02, Math.max(-0.09, motion.y * 0.55), motion.z * 1.02);
                        player.fallDistance = 0;
                        player.hurtMarked = true;
                    }
                }
            }
        }
    }

    private ForgedArmorNetwork() {}
}
