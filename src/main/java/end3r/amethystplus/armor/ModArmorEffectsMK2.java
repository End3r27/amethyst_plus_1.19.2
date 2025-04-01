package end3r.amethystplus.armor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ModArmorEffectsMK2 {

    public static void registerEffects() {
        // Add a server tick event listener to apply effects on all players
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                // Apply effects to all players in the world
                for (PlayerEntity player : world.getPlayers()) {
                    applyEffects(player);
                }
            }
        });
    }

    // Method to apply effects based on custom armor and energy
    private static void applyEffects(PlayerEntity player) {
        // Check if the player is wearing the full custom armor set
        if (isWearingFullCustomArmor(player)) {
            boolean allPiecesHaveEnergy = true;

            // Iterate through each armor piece
            for (int i = 0; i < player.getInventory().armor.size(); i++) {
                ItemStack armorPiece = player.getInventory().getArmorStack(i);

                // Example custom armor types:
                Item helmet = ModArmors.ENERGIZED_AMETHYST_HELMETMK2;
                Item chestplate = ModArmors.ENERGIZED_AMETHYST_CHESTPLATEMK2;
                Item leggings = ModArmors.ENERGIZED_AMETHYST_LEGGINGSMK2;
                Item boots = ModArmors.ENERGIZED_AMETHYST_BOOTSMK2;

                // Check if the armor piece matches any custom armor item and has energy
                if (isCustomArmor(armorPiece, helmet) || 
                    isCustomArmor(armorPiece, chestplate) ||
                    isCustomArmor(armorPiece, leggings) ||
                    isCustomArmor(armorPiece, boots)) {

                    int currentEnergy = getEnergy(armorPiece);

                    if (currentEnergy > 0) {
                        setEnergy(armorPiece, currentEnergy - 1); // Drain energy by 15
                    } else {
                        allPiecesHaveEnergy = false; // No energy means effects disabled
                    }
                }
            }

            // Apply Speed II effect only if all pieces have energy
            if (allPiecesHaveEnergy) {
                player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SPEED, 22, 1, true, false
                ));
            }
            // Apply Haste II effect only if all pieces have energy
            if (allPiecesHaveEnergy) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.HASTE, 22, 1, true, false
                ));
            }
            // Apply Jump Boost II effect only if all pieces have energy
            if (allPiecesHaveEnergy) {
                player.addStatusEffect(new StatusEffectInstance(
                        StatusEffects.JUMP_BOOST, 22, 1, true, false
                ));
            }

            // Always apply Slow Falling as long as full set is worn
            player.addStatusEffect(new StatusEffectInstance(
                StatusEffects.SLOW_FALLING, 220, 0, true, false
            ));
        }
    }

    // Check if the player is wearing the full custom armor set
    private static boolean isWearingFullCustomArmor(PlayerEntity player) {
        return isCustomArmor(player.getInventory().getArmorStack(3), ModArmors.ENERGIZED_AMETHYST_HELMETMK2) &&
               isCustomArmor(player.getInventory().getArmorStack(2), ModArmors.ENERGIZED_AMETHYST_CHESTPLATEMK2) &&
               isCustomArmor(player.getInventory().getArmorStack(1), ModArmors.ENERGIZED_AMETHYST_LEGGINGSMK2) &&
               isCustomArmor(player.getInventory().getArmorStack(0), ModArmors.ENERGIZED_AMETHYST_BOOTSMK2);
    }

    // Helper method to verify if an armor piece matches the custom armor
    private static boolean isCustomArmor(ItemStack itemStack, Item armorItem) {
        return !itemStack.isEmpty() && itemStack.getItem() == armorItem;
    }

    // Check if all pieces of the custom armor have energy
    private static boolean allArmorPiecesHaveEnergy(PlayerEntity player) {
        for (int i = 0; i < player.getInventory().armor.size(); i++) {
            ItemStack armorPiece = player.getInventory().getArmorStack(i);

            // Example custom armor types:
            Item helmet = ModArmors.ENERGIZED_AMETHYST_HELMETMK2;
            Item chestplate = ModArmors.ENERGIZED_AMETHYST_CHESTPLATEMK2;
            Item leggings = ModArmors.ENERGIZED_AMETHYST_LEGGINGSMK2;
            Item boots = ModArmors.ENERGIZED_AMETHYST_BOOTSMK2;

            // Check if the armor piece matches any custom armor item and has energy
            if ((isCustomArmor(armorPiece, helmet) ||
                 isCustomArmor(armorPiece, chestplate) ||
                 isCustomArmor(armorPiece, leggings) ||
                 isCustomArmor(armorPiece, boots)) && 
                getEnergy(armorPiece) <= 0) {

                return false; // If any piece has no energy, return false
            }
        }
        return true; // All armor pieces have energy
    }

    // Retrieve energy from an armor piece via its NBT
    private static int getEnergy(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains("Energy") ? stack.getNbt().getInt("Energy") : 0;
    }

    // Set energy for an armor piece via its NBT
    private static void setEnergy(ItemStack stack, int energy) {
        stack.getOrCreateNbt().putInt("Energy", Math.max(0, energy)); // Clamped to zero
    }
}