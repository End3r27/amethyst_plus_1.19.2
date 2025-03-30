package end3r.amethystplus.item;

import end3r.amethystplus.AmethystPlus;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup CHARGED_AMETHYST_ITEMS_GROUP = Registry.register(Registries.ITEM_GROUP,
            Identifier.of(AmethystPlus.MOD_ID, "charged_amethyst_items"),
            FabricItemGroup.builder().icon(() -> new ItemStack(ModItems.ENERGIZED_STAFF))
                    .displayName(Text.translatable("itemgroup.amethystplus.charged_amethyst_items"))
                    .entries((displayContext, entries) -> {


                        entries.add(ModItems.IRON_STICK);
                        entries.add(ModItems.CHARGED_AMETHYST_SHARD);
                        entries.add(ModItems.BATTERY);
                        entries.add(ModItems.ENERGIZED_STAFF);


                    }).build());


    public static void registerItemGroups() {
        AmethystPlus.LOGGER.info("Registering Item Groups for " + AmethystPlus.MOD_ID);
    }
}
