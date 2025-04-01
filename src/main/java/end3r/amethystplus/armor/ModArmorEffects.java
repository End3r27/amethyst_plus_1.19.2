package end3r.amethystplus.armor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ModArmorEffects {

    public static void registerEffects() {
        // Server global event at the end of every tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                // Apply logic for each player in the world
                world.getPlayers().forEach(ModArmorEffects::applyEffects);
            }
        });
    }

    // Apply both Slow Falling and Speed effects
    private static void applyEffects(PlayerEntity player) {
        // Ensure this logic runs on the server side only
        if (player.world.isClient() || !(player.world instanceof ServerWorld)) return;

        // Check if the player is wearing the full custom armor set
        if (isWearingFullCustomArmor(player)) {
            // Apply the Slow Falling effect
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SLOW_FALLING,  // Apply Slow Falling
                    220,                        // Duration in ticks (11 seconds)
                    0,                          // Effect level, 0 = level 1
                    true,                       // Particles hidden (true = no particles)
                    false                       // Effect icon hidden
            ));

        }
    }

    // Check if the player is wearing the full custom armor set
    private static boolean isWearingFullCustomArmor(PlayerEntity player) {
        // Get each armor piece from the player's inventory
        ItemStack helmet = player.getInventory().getArmorStack(3);    // Helmet slot
        ItemStack chestplate = player.getInventory().getArmorStack(2); // Chestplate slot
        ItemStack leggings = player.getInventory().getArmorStack(1);  // Leggings slot
        ItemStack boots = player.getInventory().getArmorStack(0);     // Boots slot

        // Verify each piece corresponds to the custom armor
        return isCustomArmor(helmet, ModArmors.AMETHYST_HELMET)
                && isCustomArmor(chestplate, ModArmors.AMETHYST_CHESTPLATE)
                && isCustomArmor(leggings, ModArmors.AMETHYST_LEGGINGS)
                && isCustomArmor(boots, ModArmors.AMETHYST_BOOTS);
    }

    // Helper method to verify if an armor piece matches the custom armor
    private static boolean isCustomArmor(ItemStack itemStack, Item armorItem) {
        return !itemStack.isEmpty() && itemStack.getItem() == armorItem;
    }


}