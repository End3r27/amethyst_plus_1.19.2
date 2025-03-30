package end3r.amethystplus.item;
import end3r.amethystplus.item.EffectEnergizedStaff;
import end3r.amethystplus.AmethystPlus;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.minecraft.util.registry.Registry;

public class ModItems {
    public static final Item IRON_STICK = registerItem("iron_stick",
            new Item(new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    public static final Item CHARGED_AMETHYST_SHARD = registerItem("charged_amethyst_shard",
            new Item(new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    public static final Item BATTERY = registerItem("battery",
            new Item(new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    public static final Item ENERGIZED_STAFF = registerItem("energized_staff",
            new EffectEnergizedStaff(new FabricItemSettings().maxCount(1).group(ModItemGroups.ENERGIZED_STAFF_GROUP)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registry.ITEM, new Identifier(AmethystPlus.MOD_ID, name), item);
    }


    public static void registerModItems() {
        AmethystPlus.LOGGER.info("Registering Mod Items for " + AmethystPlus.MOD_ID);

    }
}