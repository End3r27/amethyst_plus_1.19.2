package end3r.amethystplus.item;

import java.util.List;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraft.entity.damage.DamageSource;
import org.jetbrains.annotations.Nullable;

public class EffectEnergizedStaff extends Item {
    private static final int MAX_ENERGY = 20000; // Maximum energy the item can hold
    private static final int ENERGY_PER_HIT = 200; // Energy cost per hit

    public EffectEnergizedStaff(Settings settings) {
        super(settings);
    }

    // Retrieve the stored energy from NBT
    public int getEnergy(ItemStack stack) {
        NbtCompound nbt = stack.getOrCreateNbt();
        return nbt.getInt("Energy"); // Defaults to 0 if not set
    }

    // Set the stored energy in NBT
    public void setEnergy(ItemStack stack, int energy) {
        NbtCompound nbt = stack.getOrCreateNbt();
        nbt.putInt("Energy", Math.min(MAX_ENERGY, Math.max(0, energy))); // Ensure energy is between 0 and MAX_ENERGY
    }


    // Called when attacking an entity
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!attacker.getWorld().isClient) { // Server-side only
            // Check if the item has enough energy
            int currentEnergy = getEnergy(stack);
            if (currentEnergy >= ENERGY_PER_HIT) {
                target.setOnFireFor(4); // Sets entity on fire for 4 seconds

                if (attacker instanceof PlayerEntity player) {
                    target.damage(DamageSource.player(player), 5.0F); // Deals 5 extra damage
                }

                // Consume energy
                setEnergy(stack, currentEnergy - ENERGY_PER_HIT);
            } else {
                // Optional: Notify the player they're out of energy
                if (attacker instanceof PlayerEntity player) {
                    player.sendMessage(Text.of("Not enough energy!"), true);
                }
            }
        }
        return true;
    }

    // Makes the item start with 0 energy when crafted
    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        super.onCraft(stack, world, player);
        setEnergy(stack, 0); // Start with 0 energy
    }

    // Override to display the purple energy bar
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        // Always return true to display the energy bar, even if energy is 0
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        return Math.round((float) getEnergy(stack) / MAX_ENERGY * 13); // Scales to 13 steps
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        return 0xAA00FF; // Purple color for the energy bar

    }
    @Override
    public boolean hasRecipeRemainder() {
        // Enable this item to have a remainder (it stays in the crafting grid)
        return true;
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        // Check if the item being used has energy below the max
        int currentEnergy = getEnergy(stack);
        if (currentEnergy < MAX_ENERGY) {
            // Recharge staff with 5000 energy, but do not exceed the max energy
            setEnergy(stack, Math.min(currentEnergy + 5000, MAX_ENERGY));
        }
        return stack; // Return the modified staff
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        // Retrieve the current energy
        int currentEnergy = getEnergy(stack);

        // Create the tooltip text with the energy values
        MutableText energyText = Text.literal(currentEnergy + "AE / " + MAX_ENERGY + "AE")
                .formatted(Formatting.LIGHT_PURPLE); // Formats the text in purple

        // Add the energy text to the tooltip
        tooltip.add(energyText);
    }

}

