package end3r.amethystplus.item;

import end3r.amethystplus.AmethystPlus;
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModItemGroups {
    public static final ItemGroup ENERGIZED_STAFF_GROUP = FabricItemGroupBuilder.build(
            new Identifier(AmethystPlus.MOD_ID, "energized_staff"), () -> new ItemStack(ModItems.ENERGIZED_STAFF));

    public static void registerItemGroups() {
        AmethystPlus.LOGGER.info("Registering Item Groups for " + AmethystPlus.MOD_ID);
    }
}
