package com.fightclass3.event;

import com.fightclass3.FightClass3Mod;
import com.fightclass3.client.FightKeys;
import com.fightclass3.gui.StatMenuScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FightClass3Mod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ClientEventHandler {

    /** Called from FightClass3Mod — nothing to register without Player Animator. */
    public static void registerAnimations() {}

    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;

        if (FightKeys.OPEN_STATS.consumeClick()) {
            mc.setScreen(new StatMenuScreen());
        }
    }

    /**
     * When the player attacks with the PunchItem, trigger the vanilla
     * arm-swing animation (already plays automatically on attack, but
     * this ensures it fires even when hitting air).
     */
    @SubscribeEvent
    public static void onAttackInput(InputEvent.InteractionKeyMappingTriggered event) {
        if (!event.isAttack()) return;
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;
        if (!(mc.player.getMainHandItem().getItem()
                instanceof com.fightclass3.items.PunchItem)) return;

        // Trigger the vanilla punch swing
        mc.player.swing(InteractionHand.MAIN_HAND);
        event.setCanceled(true);
    }
}
