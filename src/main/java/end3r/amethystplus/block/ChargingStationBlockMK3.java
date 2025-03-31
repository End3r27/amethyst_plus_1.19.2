package end3r.amethystplus.block;

import end3r.amethystplus.item.EffectEnergizedStaffMK2;
import end3r.amethystplus.item.EffectEnergizedStaffMK3;
import end3r.amethystplus.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ChargingStationBlockMK3 extends Block {
    // Optional property to visually indicate "charging"
    public static final BooleanProperty ACTIVE = Properties.LIT;

    public ChargingStationBlockMK3(Settings settings) {
        super(settings);
        // Initialize the default block state with ACTIVE = false
        this.setDefaultState(this.stateManager.getDefaultState().with(ACTIVE, false));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder) {
        builder.add(ACTIVE); // Add the ACTIVE property
    }

    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        // Ensure we're on the server side
        if (!world.isClient) {
            // Get the item in the player's hand
            ItemStack heldItem = player.getStackInHand(hand);

            // Check if the player is holding the energized staff
            if (heldItem.getItem() == ModItems.ENERGIZED_STAFFMK3) {
                EffectEnergizedStaffMK3 staff = (EffectEnergizedStaffMK3) heldItem.getItem();

                // Get the current energy using getEnergy
                int currentEnergy = staff.getEnergy(heldItem);

                // Define the max energy (you can also call `staff.MAX_ENERGY` directly if it's accessible)
                int maxEnergy = 100000;

                // Add 2000 energy but ensure it doesn't exceed the max
                if (currentEnergy < maxEnergy) {
                    int newEnergy = Math.min(currentEnergy + 10000, maxEnergy);

                    // Use setEnergy to update the NBT
                    staff.setEnergy(heldItem, newEnergy);

                    // Provide feedback to the player
                    world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.5F, 0.5F);

                    // Optionally set the block state to ACTIVE temporarily for visual feedback
                    world.setBlockState(pos, state.with(ACTIVE, true));
                    world.createAndScheduleBlockTick(pos, this, 20); // Reset after 20 ticks (1 second)

                    return ActionResult.SUCCESS;
                } else {
                    // Notify the player if the staff is already fully charged
                    player.sendMessage(Text.translatable("item.amethystplus.energized_staff.full"), true);
                    return ActionResult.PASS;
                }
            }

            // Check if the player is holding an amethyst shard (keep original behavior)
            if (heldItem.getItem() == ModItems.OVERCHARGED_AMETHYST_SHARD) {
                // Decrease the item stack by 1
                heldItem.decrement(1);

                // Give the player a charged amethyst shard
                if (!player.giveItemStack(new ItemStack(ModItems.HYPERCHARGED_AMETHYST_SHARD))) {
                    // Drop the item in the world if the player's inventory is full
                    player.dropItem(new ItemStack(ModItems.HYPERCHARGED_AMETHYST_SHARD), false);
                }

                // Play a sound effect
                world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                // Optionally set the block state to ACTIVE temporarily (visual effect)
                world.setBlockState(pos, state.with(ACTIVE, true));
                world.createAndScheduleBlockTick(pos, this, 20); // Reset after 20 ticks (1 second)

                return ActionResult.SUCCESS;
            }
        }

        return ActionResult.PASS;
    }

    public void scheduledTick(BlockState state, World world, BlockPos pos, java.util.Random random) {
        // Reset the block state to not active
        if (state.get(ACTIVE)) {
            world.setBlockState(pos, state.with(ACTIVE, false));
        }
    }
}