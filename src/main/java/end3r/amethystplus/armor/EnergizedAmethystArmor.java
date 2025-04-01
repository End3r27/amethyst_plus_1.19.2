package end3r.amethystplus.armor;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import java.util.List;

public class EnergizedAmethystArmor extends ArmorItem {
    public static final int MAX_ENERGY = 20000;           // Maximum energy storage
    private static final int ENERGY_DRAIN_PER_SECOND = 15; // Energy consumed per second

    public EnergizedAmethystArmor(EquipmentSlot slot, Settings settings) {
        super(new CustomArmorMaterial(), slot, settings); // Uses a custom armor material
    }

    // Get energy stored in the armor from NBT
    public int getEnergy(ItemStack stack) {
        return stack.getOrCreateNbt().getInt("Energy"); // Retrieve the "Energy" NBT value, default to 0
    }

    // Set energy in the armor and clamp the value between 0 and MAX_ENERGY
    public void setEnergy(ItemStack stack, int energy) {
        stack.getOrCreateNbt().putInt("Energy", Math.min(MAX_ENERGY, Math.max(0, energy)));
    }

    // Display energy and additional tips in the armor tooltip
    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        super.appendTooltip(stack, world, tooltip, context); // Add the normal tooltip
        int energy = getEnergy(stack);
        tooltip.add(Text.literal(energy + "AE / " + MAX_ENERGY + "AE").formatted(Formatting.AQUA)); // Display energy
        tooltip.add(Text.literal("When fully charged, gain Speed II.").formatted(Formatting.GRAY));
        tooltip.add(Text.literal("Permanent Slow Falling is applied.").formatted(Formatting.GRAY));
    }

    // Check if the energy-based durability bar should be visible
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        return true; // Visible always
    }

    // Specify the color of the durability bar (purple for energy)
    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xAA00FF; // Purple color (hexadecimal)
    }

    // Calculate the progress of the durability bar based on the armor's energy level
    @Override
    public int getItemBarStep(ItemStack stack) {
        int energy = getEnergy(stack);                 // Get current energy level
        float progress = (float) energy / MAX_ENERGY; // Calculate progress as a percentage
        return Math.round(13 * progress);             // Scale progress to bar steps (0-13)
    }

    // Handle energy drain over time and apply effects when worn
// Helper method to check if the player is wearing a specific armor piece
    private boolean isArmorEquipped(PlayerEntity player, ItemStack stack) {
        for (ItemStack armorPiece : player.getArmorItems()) {
            if (ItemStack.areItemsEqual(armorPiece, stack)) {
                return true;
            }
        }
        return false;
    }


    public void inventoryTick(ItemStack stack, World world, PlayerEntity player, int slot, boolean selected) {
        if (!world.isClient) {
            // Check if the player is wearing this specific armor item
            boolean isWorn = isArmorEquipped(player, stack);

            if (isWorn) {
                long gameTime = world.getTime(); // Get the current game time in ticks

                if (gameTime % 20 == 0) { // Runs once every 20 ticks (1 second)
                    int currentEnergy = getEnergy(stack);

                    if (currentEnergy > 0) {
                        // Drain energy by defined amount per second
                        setEnergy(stack, currentEnergy - ENERGY_DRAIN_PER_SECOND);
                    }

                    // Apply effects if the armor still has energy
                    if (currentEnergy > ENERGY_DRAIN_PER_SECOND) {
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 220, 1, true, false));
                        player.addStatusEffect(new StatusEffectInstance(StatusEffects.SLOW_FALLING, 220, 0, true, false));
                    }
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        return "amethystplus:textures/models/armor/energized_amethyst_layer_" + (slot == EquipmentSlot.LEGS ? "2" : "1") + ".png";
    }
}