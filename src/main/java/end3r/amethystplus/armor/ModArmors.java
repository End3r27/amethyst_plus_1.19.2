package end3r.amethystplus.armor;


import end3r.amethystplus.AmethystPlus;
import end3r.amethystplus.item.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
 // Adjust location of your CustomArmorMaterial class
// Adjust location of your ModItemGroups class
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.util.registry.Registry;

public class ModArmors {
    // Define custom armor items
    public static final Item AMETHYST_HELMET = registerArmor("amethyst_helmet",
            new CustomArmorMaterial(), EquipmentSlot.HEAD);
    public static final Item AMETHYST_CHESTPLATE = registerArmor("amethyst_chestplate",
            new CustomArmorMaterial(), EquipmentSlot.CHEST);
    public static final Item AMETHYST_LEGGINGS = registerArmor("amethyst_leggings",
            new CustomArmorMaterial(), EquipmentSlot.LEGS);
    public static final Item AMETHYST_BOOTS = registerArmor("amethyst_boots",
            new CustomArmorMaterial(), EquipmentSlot.FEET);

    // Registration method for armor items
    private static Item registerArmor(String name, ArmorMaterial material, EquipmentSlot slot) {
        return Registry.register(Registry.ITEM, new Identifier("amethystplus", name),
                new ArmorItem(material, slot, // Pass ArmorMaterial and EquipmentSlot
                        new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    }

    // Armor registration method to be called during mod initialization
    public static void registerModArmor() {
        System.out.println("Registering Mod Armor for " + AmethystPlus.MOD_ID); // Log for debugging
    }
}
