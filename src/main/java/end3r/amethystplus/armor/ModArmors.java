package end3r.amethystplus.armor;

import end3r.amethystplus.AmethystPlus;
import end3r.amethystplus.item.ModItemGroups;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class ModArmors {
    // Define regular armor items
    public static final Item AMETHYST_HELMET = registerArmor("amethyst_helmet",
            new CustomArmorMaterial(), EquipmentSlot.HEAD);
    public static final Item AMETHYST_CHESTPLATE = registerArmor("amethyst_chestplate",
            new CustomArmorMaterial(), EquipmentSlot.CHEST);
    public static final Item AMETHYST_LEGGINGS = registerArmor("amethyst_leggings",
            new CustomArmorMaterial(), EquipmentSlot.LEGS);
    public static final Item AMETHYST_BOOTS = registerArmor("amethyst_boots",
            new CustomArmorMaterial(), EquipmentSlot.FEET);

    // Define energized armor items
    public static final Item ENERGIZED_AMETHYST_HELMET = registerEnergizedArmor("energized_amethyst_helmet",
            new CustomArmorMaterialMK1(), EquipmentSlot.HEAD);
    public static final Item ENERGIZED_AMETHYST_CHESTPLATE = registerEnergizedArmor("energized_amethyst_chestplate",
            new CustomArmorMaterialMK1(), EquipmentSlot.CHEST);
    public static final Item ENERGIZED_AMETHYST_LEGGINGS = registerEnergizedArmor("energized_amethyst_leggings",
            new CustomArmorMaterialMK1(), EquipmentSlot.LEGS);
    public static final Item ENERGIZED_AMETHYST_BOOTS = registerEnergizedArmor("energized_amethyst_boots",
            new CustomArmorMaterialMK1(), EquipmentSlot.FEET);

    // Define energized MK2 armor items
    public static final Item ENERGIZED_AMETHYST_HELMETMK2 = registerEnergizedArmorMK2("energized_amethyst_helmetmk2",
            new CustomArmorMaterialMK2(), EquipmentSlot.HEAD);
    public static final Item ENERGIZED_AMETHYST_CHESTPLATEMK2 = registerEnergizedArmorMK2("energized_amethyst_chestplatemk2",
            new CustomArmorMaterialMK2(), EquipmentSlot.CHEST);
    public static final Item ENERGIZED_AMETHYST_LEGGINGSMK2 = registerEnergizedArmorMK2("energized_amethyst_leggingsmk2",
            new CustomArmorMaterialMK2(), EquipmentSlot.LEGS);
    public static final Item ENERGIZED_AMETHYST_BOOTSMK2 = registerEnergizedArmorMK2("energized_amethyst_bootsmk2",
            new CustomArmorMaterialMK2(), EquipmentSlot.FEET);

    /**
     * Method to register a regular armor item.
     *
     * @param name   The name of the item, like "amethyst_helmet".
     * @param material The armor material to use for this item.
     * @param slot   The equipment slot where this armor piece is worn.
     * @return The registered {@link Item}.
     */
    private static Item registerArmor(String name, ArmorMaterial material, EquipmentSlot slot) {
        return Registry.register(Registry.ITEM, new Identifier("amethystplus", name),
                new ArmorItem(material, slot, // Pass ArmorMaterial and EquipmentSlot
                        new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    }

    /**
     * Method to register an energized armor item.
     *
     * @param name                   The name of the item, like "energized_amethyst_helmet".
     * @param customArmorMaterialMK1
     * @param slot                   The equipment slot where this armor piece is worn.
     * @return The registered {@link Item}.
     */
    private static Item registerEnergizedArmor(String name, CustomArmorMaterialMK1 customArmorMaterialMK1, EquipmentSlot slot) {
        return Registry.register(Registry.ITEM, new Identifier("amethystplus", name),
                new EnergizedAmethystArmor(slot, // Use the custom EnergizedAmethystArmor class
                        new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    }

    private static Item registerEnergizedArmorMK2(String name, CustomArmorMaterialMK2 customArmorMaterialMK2, EquipmentSlot slot) {
        return Registry.register(Registry.ITEM, new Identifier("amethystplus", name),
                new EnergizedAmethystArmorMK2(slot, // Use the custom EnergizedAmethystArmor class
                        new FabricItemSettings().group(ModItemGroups.ENERGIZED_STAFF_GROUP)));
    }

    // Method to register all armor items for the mod - called during initialization
    public static void registerModArmor() {
        System.out.println("Registering Mod Armor for " + AmethystPlus.MOD_ID);
    }
}