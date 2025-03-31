package end3r.amethystplus;

import end3r.amethystplus.client.RenderEnergyBar;
import end3r.amethystplus.item.EffectEnergizedStaff;
import end3r.amethystplus.item.EffectEnergizedStaffMK2;
import end3r.amethystplus.item.EffectEnergizedStaffMK3;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;

public class AmethystPlusClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        // Register a HUD render event
        HudRenderCallback.EVENT.register((matrixStack, tickDelta) -> {
            MinecraftClient client = MinecraftClient.getInstance();

            // Ensure the player is holding a relevant staff
            if (client.player != null) {
                ItemStack heldItem = client.player.getMainHandStack();
                if (!(heldItem.getItem() instanceof EffectEnergizedStaff
                        || heldItem.getItem() instanceof EffectEnergizedStaffMK2
                        || heldItem.getItem() instanceof EffectEnergizedStaffMK3)) {
                    return; // Not holding one of the custom staff types
                }

                // Call the energy bar renderer
                RenderEnergyBar.renderEnergyBar(
                        matrixStack,
                        tickDelta,
                        client.getWindow().getScaledWidth(),
                        client.getWindow().getScaledHeight(),
                        heldItem
                );
            }
        });
    }
}