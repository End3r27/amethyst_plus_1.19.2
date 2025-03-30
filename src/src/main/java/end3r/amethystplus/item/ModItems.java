package end3r.amethystplus.item;
import end3r.amethystplus.item.EffectEnergizedStaff;
import end3r.amethystplus.AmethystPlus;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;

public class ModItems {

    public static final Item IRON_STICK = registerItem("iron_stick", new Item(new Item.Settings()));
    public static final Item CHARGED_AMETHYST_SHARD = registerItem("charged_amethyst_shard", new Item(new Item.Settings().rarity(Rarity.RARE).maxCount(16)));

    public static final Item BATTERY = registerItem("battery", new Item(new Item.Settings().maxCount(1)));

    public static final Item ENERGIZED_STAFF = registerItem("energized_staff", new EffectEnergizedStaff(new Item.Settings().rarity(Rarity.RARE).maxCount(1)));


    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, Identifier.of(AmethystPlus.MOD_ID, name), item);
    }

    public static void registerModItems() {
        AmethystPlus.LOGGER.info("Registering Mod Items for " + AmethystPlus.MOD_ID);

    }
}