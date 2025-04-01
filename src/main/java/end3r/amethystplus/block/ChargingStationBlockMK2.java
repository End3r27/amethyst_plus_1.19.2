package end3r.amethystplus.block;

import end3r.amethystplus.armor.EnergizedAmethystArmor;
import end3r.amethystplus.item.EffectEnergizedStaff;
import end3r.amethystplus.item.EffectEnergizedStaffMK2;
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
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ChargingStationBlockMK2 extends Block {
    // Optional property to visually indicate "charging"
    public static final BooleanProperty ACTIVE = Properties.LIT;

    public ChargingStationBlockMK2(Settings settings) {
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
                        int newEnergy = Math.min(currentEnergy + 1000, EnergizedAmethystArmor.MAX_ENERGY);
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
            if (heldItem.getItem() instanceof EffectEnergizedStaffMK2 energizedStaff) {
                int currentEnergy = energizedStaff.getEnergy(heldItem);

                // Charge the staff if not fully charged
                if (currentEnergy < EffectEnergizedStaffMK2.MAX_ENERGY) {
                    int newEnergy = Math.min(currentEnergy + 1000, EffectEnergizedStaffMK2.MAX_ENERGY);
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
            if (heldItem.getItem() == Registry.ITEM.get(new Identifier("amethystplus:charged_amethyst_shard"))) {
                if (!world.isClient) { // Ensure this logic runs only on the server side
                    // Decrement the number of amethyst shards in the stack
                    heldItem.decrement(1); // Removes one shard from the stack

                    // Add the charged shard as a new item to the player's inventory
                    ItemStack chargedShard = new ItemStack(Registry.ITEM.get(new Identifier("amethystplus:overcharged_amethyst_shard")));
                    if (!player.getInventory().insertStack(chargedShard)) {
                        // If the player's inventory is full, drop the charged shard into the world
                        player.dropItem(chargedShard, false);
                    }

                    // Play a sound to indicate success
                    world.playSound(null, pos, SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.BLOCKS, 1.0F, 1.0F);

                    // Send feedback to the player (action bar notification)
                    player.sendMessage(Text.literal("Your shard has been charged!").formatted(Formatting.AQUA), true);

                    // (Optional) If the block has a visual indicator, turn it on
                    world.setBlockState(pos, state.with(ACTIVE, true));
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
}