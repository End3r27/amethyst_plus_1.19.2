package end3r.amethystplus.block;

import end3r.amethystplus.armor.EnergizedAmethystArmor;
import end3r.amethystplus.armor.EnergizedAmethystArmorMK2;
import end3r.amethystplus.armor.EnergizedAmethystArmorMK3;
import end3r.amethystplus.item.EffectEnergizedStaff;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

import java.util.Collections;
import java.util.List;

public class ChargingStationBlock extends Block {
    // Optional property to visually indicate "charging"
    public static final BooleanProperty ACTIVE = Properties.LIT;

    public ChargingStationBlock(Settings settings) {
        super(settings);
        // Initialize block state properties
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE); // Add the 'ACTIVE' property to the state manager
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (!world.isClient) { // Run only on the server side
            ItemStack heldItem = player.getStackInHand(hand); // Get the item held by the player
            boolean didCharge = false; // Tracks if any item was charged

            // 1. Charge Energized Amethyst Armor
            for (ItemStack armorStack : player.getArmorItems()) {
                if (armorStack.getItem() instanceof EnergizedAmethystArmor energizedArmor) {
                    int currentEnergy = energizedArmor.getEnergy(armorStack);

                    // Charge the armor if not fully charged
                    if (currentEnergy < EnergizedAmethystArmor.MAX_ENERGY) {
                        int newEnergy = Math.min(currentEnergy + 500, EnergizedAmethystArmor.MAX_ENERGY);
                        energizedArmor.setEnergy(armorStack, newEnergy); // Update the armor's energy
                        didCharge = true; // Indicate that something was charged
                    }
                }
            }
            // 1. Charge Energized Amethyst Armor MK2
            for (ItemStack armorStack : player.getArmorItems()) {
                if (armorStack.getItem() instanceof EnergizedAmethystArmorMK2 energizedArmor) {
                    int currentEnergy = energizedArmor.getEnergy(armorStack);

                    // Charge the armor if not fully charged
                    if (currentEnergy < EnergizedAmethystArmorMK2.MAX_ENERGY) {
                        int newEnergy = Math.min(currentEnergy + 500, EnergizedAmethystArmorMK2.MAX_ENERGY);
                        energizedArmor.setEnergy(armorStack, newEnergy); // Update the armor's energy
                        didCharge = true; // Indicate that something was charged
                    }
                }
            }

            // 1. Charge Energized Amethyst Armor MK3
            for (ItemStack armorStack : player.getArmorItems()) {
                if (armorStack.getItem() instanceof EnergizedAmethystArmorMK3 energizedArmor) {
                    int currentEnergy = energizedArmor.getEnergy(armorStack);

                    // Charge the armor if not fully charged
                    if (currentEnergy < EnergizedAmethystArmorMK3.MAX_ENERGY) {
                        int newEnergy = Math.min(currentEnergy + 500, EnergizedAmethystArmorMK3.MAX_ENERGY);
                        energizedArmor.setEnergy(armorStack, newEnergy); // Update the armor's energy
                        didCharge = true; // Indicate that something was charged
                    }
                }
            }

            // Provide feedback for armor charging
            if (didCharge) {
                player.sendMessage(
                    Text.literal("Your Energized Armor pieces are being charged!").formatted(Formatting.GREEN),
                    true // Action bar message
                );
                world.playSound(
                    null, pos, SoundEvents.BLOCK_BEACON_ACTIVATE, SoundCategory.BLOCKS, 0.8F, 1.0F
                );
            }

            // 2. Charge the Energized Staff
            if (heldItem.getItem() instanceof EffectEnergizedStaff energizedStaff) {
                int currentEnergy = energizedStaff.getEnergy(heldItem);

                // Charge the staff if not fully charged
                if (currentEnergy < EffectEnergizedStaff.MAX_ENERGY) {
                    int newEnergy = Math.min(currentEnergy + 500, EffectEnergizedStaff.MAX_ENERGY);
                    energizedStaff.setEnergy(heldItem, newEnergy); // Update the staff's NBT
                    didCharge = true;

                    // Provide feedback for staff charging
                    player.sendMessage(
                        Text.literal("Your Energized Staff is being charged!").formatted(Formatting.AQUA),
                        true
                    );
                    world.playSound(
                        null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F
                    );
                }
            }
            // Check if the held item is an amethyst shard
            if (heldItem.getItem() == Registry.ITEM.get(new Identifier("minecraft:amethyst_shard"))) {
                if (!world.isClient) { // Run only on the server
                    heldItem.decrement(1); // Remove one amethyst shard from the stack
                    player.setStackInHand(hand, new ItemStack(Registry.ITEM.get(new Identifier("amethystplus:charged_amethyst_shard")))); // Add the charged shard
                    world.playSound(
                            null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F
                    ); // Play sound
                    player.sendMessage(
                            Text.literal("Your shard has been charged!").formatted(Formatting.AQUA),
                            true // Sends as an action bar message
                    ); // Notify the player
                    world.setBlockState(pos, state.with(ACTIVE, true)); // Set the active state
                }
                return ActionResult.SUCCESS;
        }

            // 3. Fallback behavior: No items charged
            if (!didCharge) {
                player.sendMessage(
                    Text.literal("The Charging Station hums softly. Nothing happens.").formatted(Formatting.YELLOW),
                    true
                );
            }

            // Change block state to 'active' if any item was charged
            if (didCharge && !state.get(ACTIVE)) {
                world.setBlockState(pos, state.with(ACTIVE, true));
            }
        }

        // Always return SUCCESS for interaction
        return ActionResult.SUCCESS;
    }
    @Override
    public List<ItemStack> getDroppedStacks(BlockState state, net.minecraft.loot.context.LootContext.Builder builder) {
        return Collections.singletonList(new ItemStack(this));
    }

    @Override
    public void onBreak(World world, BlockPos pos, BlockState state, PlayerEntity player) {
        super.onBreak(world, pos, state, player);
        if (!world.isClient) {
            dropStacks(state, world, pos, null, player, player.getMainHandStack());
        }
    }
}
