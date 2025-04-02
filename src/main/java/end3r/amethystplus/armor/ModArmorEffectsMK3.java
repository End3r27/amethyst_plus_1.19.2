package end3r.amethystplus.armor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ModArmorEffectsMK3 {

    public static void registerEffects() {
        // Register a server tick event listener to apply effects
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
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

            // Iterate through each armor piece and ensure all have energy
            for (int i = 0; i < player.getInventory().armor.size(); i++) {
                ItemStack armorPiece = player.getInventory().getArmorStack(i);

                Item helmet = ModArmors.ENERGIZED_AMETHYST_HELMETMK3;
                Item chestplate = ModArmors.ENERGIZED_AMETHYST_CHESTPLATEMK3;
                Item leggings = ModArmors.ENERGIZED_AMETHYST_LEGGINGSMK3;
                Item boots = ModArmors.ENERGIZED_AMETHYST_BOOTSMK3;

                if (isCustomArmor(armorPiece, helmet) ||
                        isCustomArmor(armorPiece, chestplate) ||
                        isCustomArmor(armorPiece, leggings) ||
                        isCustomArmor(armorPiece, boots)) {

                    int currentEnergy = getEnergy(armorPiece);

                    if (currentEnergy > 0) {
                        setEnergy(armorPiece, currentEnergy - 1);
                    } else {
                        allPiecesHaveEnergy = false;
                    }
                }
            }

            // Enable creative flight if all pieces have energy
            if (allPiecesHaveEnergy) {
                player.getAbilities().allowFlying = true;

                // Sync player's abilities to ensure proper toggling
                player.sendAbilitiesUpdate();
            } else {
                // Disable flight if no energy
                disableFlight(player);
            }
        } else {
            // Disable flight if not wearing the full armor set
            disableFlight(player);
        }
    }

    // Helper method to disable flight and sync the player's abilities
    private static void disableFlight(PlayerEntity player) {
        player.getAbilities().allowFlying = false;
        player.getAbilities().flying = false; // Ensure flying is toggled off
        player.sendAbilitiesUpdate();
    }

    // Check if the player is wearing the full custom armor set
    private static boolean isWearingFullCustomArmor(PlayerEntity player) {
        return isCustomArmor(player.getInventory().getArmorStack(3), ModArmors.ENERGIZED_AMETHYST_HELMETMK3) &&
                isCustomArmor(player.getInventory().getArmorStack(2), ModArmors.ENERGIZED_AMETHYST_CHESTPLATEMK3) &&
                isCustomArmor(player.getInventory().getArmorStack(1), ModArmors.ENERGIZED_AMETHYST_LEGGINGSMK3) &&
                isCustomArmor(player.getInventory().getArmorStack(0), ModArmors.ENERGIZED_AMETHYST_BOOTSMK3);
    }

    // Helper method to verify if an armor piece matches the custom armor
    private static boolean isCustomArmor(ItemStack itemStack, Item armorItem) {
        return !itemStack.isEmpty() && itemStack.getItem() == armorItem;
    }

    // Retrieve energy from an armor piece via its NBT
    private static int getEnergy(ItemStack stack) {
        return stack.hasNbt() && stack.getNbt().contains("Energy") ? stack.getNbt().getInt("Energy") : 0;
    }

    // Set energy for an armor piece via its NBT
    private static void setEnergy(ItemStack stack, int energy) {
        stack.getOrCreateNbt().putInt("Energy", Math.max(0, energy));
    }
}