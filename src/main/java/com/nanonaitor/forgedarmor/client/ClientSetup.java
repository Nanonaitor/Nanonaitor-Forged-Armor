package com.nanonaitor.forgedarmor.client;

import com.nanonaitor.forgedarmor.ForgedArmor;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.lwjgl.glfw.GLFW;
import com.nanonaitor.forgedarmor.AttachmentData;
import com.nanonaitor.forgedarmor.AttachmentType;
import com.nanonaitor.forgedarmor.ForgedArmorNetwork;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = ForgedArmor.ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientSetup {
    private static final KeyMapping OPEN_STORAGE = new KeyMapping("key.nanonaitor_forged_armor.open_storage",
            InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, "key.categories.inventory");
    @SubscribeEvent
    public static void setup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            MenuScreens.register(ForgedArmor.ARMOR_FORGE_MENU.get(), ArmorForgeScreen::new);
            MinecraftForge.EVENT_BUS.register(new InputHandler());
        });
    }

    @SubscribeEvent public static void keys(RegisterKeyMappingsEvent event) { event.register(OPEN_STORAGE); }

    private static final class InputHandler {
        private boolean jumpWasDown, forwardWasDown, backWasDown, leftWasDown, rightWasDown;
        private long forwardTap = -20, backTap = -20, leftTap = -20, rightTap = -20;

        @SubscribeEvent public void tick(TickEvent.ClientTickEvent event) {
            if (event.phase != TickEvent.Phase.END) return;
            Minecraft mc = Minecraft.getInstance();
            if (mc.player == null || mc.screen != null) return;
            long tick = mc.player.tickCount;
            boolean jump = mc.options.keyJump.isDown();
            if (jump && !jumpWasDown && !mc.player.onGround()
                    && (AttachmentData.equipped(mc.player).contains(AttachmentType.ALLAY_WINGS)
                    || AttachmentData.equipped(mc.player).contains(AttachmentType.VEX_WINGS)))
                ForgedArmorNetwork.send(ForgedArmorNetwork.AbilityAction.DOUBLE_JUMP);
            if (jump && AttachmentData.equipped(mc.player).contains(AttachmentType.PHANTOM_WINGS)
                    && tick % 2 == 0) ForgedArmorNetwork.send(ForgedArmorNetwork.AbilityAction.GLIDE);
            jumpWasDown = jump;

            forwardTap = tap(mc.options.keyUp.isDown(), forwardWasDown, forwardTap, tick, ForgedArmorNetwork.AbilityAction.DASH_FORWARD);
            backTap = tap(mc.options.keyDown.isDown(), backWasDown, backTap, tick, ForgedArmorNetwork.AbilityAction.DASH_BACK);
            leftTap = tap(mc.options.keyLeft.isDown(), leftWasDown, leftTap, tick, ForgedArmorNetwork.AbilityAction.DASH_LEFT);
            rightTap = tap(mc.options.keyRight.isDown(), rightWasDown, rightTap, tick, ForgedArmorNetwork.AbilityAction.DASH_RIGHT);
            forwardWasDown = mc.options.keyUp.isDown(); backWasDown = mc.options.keyDown.isDown();
            leftWasDown = mc.options.keyLeft.isDown(); rightWasDown = mc.options.keyRight.isDown();
            while (OPEN_STORAGE.consumeClick()) ForgedArmorNetwork.send(ForgedArmorNetwork.AbilityAction.OPEN_STORAGE);
        }

        private long tap(boolean down, boolean wasDown, long last, long tick, ForgedArmorNetwork.AbilityAction action) {
            if (down && !wasDown) {
                if (tick - last <= 7 && Minecraft.getInstance().player != null
                        && AttachmentData.equipped(Minecraft.getInstance().player).contains(AttachmentType.BREEZE_WIND))
                    ForgedArmorNetwork.send(action);
                return tick;
            }
            return last;
        }
    }
}
