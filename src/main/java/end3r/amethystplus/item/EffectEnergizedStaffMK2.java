package end3r.amethystplus.item;

import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class EffectEnergizedStaffMK2 extends Item {
    private static final int MAX_ENERGY = 50000; // Maximum energy the item can hold
    private static final int ENERGY_PER_HIT = 250; // Energy cost per hit

    public EffectEnergizedStaffMK2(Settings settings) {
        super(settings);
    }

    // Retrieve the stored energy from NBT
    public int getEnergy(ItemStack stack) {
        // Default to 0 energy if no NBT data is present
        return stack.getOrCreateNbt().getInt("Energy");
    }

    // Set the stored energy in NBT
    public void setEnergy(ItemStack stack, int energy) {
        // Restricting the energy between 0 and MAX_ENERGY
        energy = Math.max(0, Math.min(MAX_ENERGY, energy));
        stack.getOrCreateNbt().putInt("Energy", energy);
    }

    // Called when attacking an entity
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        int currentEnergy = getEnergy(stack);

        if (currentEnergy >= ENERGY_PER_HIT) {
            // Deduce energy for the effect
            setEnergy(stack, currentEnergy - ENERGY_PER_HIT);

            // Summon a lightning bolt at the target's position
            World world = target.getWorld();
            if (!world.isClient) { // Ensure this happens only on the server side
                LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, world);
                lightning.setPos(target.getX(), target.getY(), target.getZ());
                world.spawnEntity(lightning);
            }
        }

        return super.postHit(stack, target, attacker); // Call the superclass implementation
    }

    // Makes the item start with 0 energy when crafted
    @Override
    public void onCraft(ItemStack stack, World world, PlayerEntity player) {
        setEnergy(stack, 0); // Initialize energy to 0
    }

    // Override to display the purple energy bar
    @Override
    public boolean isItemBarVisible(ItemStack stack) {
        // The bar is visible only when energy is greater than 0
        return true;
    }

    @Override
    public int getItemBarStep(ItemStack stack) {
        // Calculate energy bar progress based on current energy and max energy
        int energy = getEnergy(stack);
        return (int) (13.0 * energy / MAX_ENERGY); // Scale between 0 and 13
    }

    @Override
    public int getItemBarColor(ItemStack stack) {
        // Provide a consistent purple color HEX value for the energy bar
        return 0xAA00FF; // HEX color for purple
    }

    @Override
    public boolean hasRecipeRemainder() {
        return true; // Indicates the item has a remainder after crafting
    }

    @Override
    public ItemStack getRecipeRemainder(ItemStack stack) {
        ItemStack remainder = new ItemStack(this);
        setEnergy(remainder, getEnergy(stack)); // Preserve the energy in the remainder
        return remainder;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        int energy = getEnergy(stack);
        MutableText energyText = Text.literal(energy + "AE / " + MAX_ENERGY + "AE").formatted(Formatting.LIGHT_PURPLE);
        tooltip.add(energyText);
    }

}