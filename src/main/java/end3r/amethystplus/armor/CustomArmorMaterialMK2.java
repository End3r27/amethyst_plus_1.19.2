package end3r.amethystplus.armor;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

public class CustomArmorMaterialMK2 implements ArmorMaterial {
    // The base durability values for different slots
    private static final int[] BASE_DURABILITY = new int[]{13, 15, 16, 11}; // Head, Chest, Legs, Feet
    // The protection values for each armor piece
    private static final int[] PROTECTION_VALUES = new int[]{4, 8, 7, 3}; // Head, Chest, Legs, Feet

    @Override
    public int getDurability(EquipmentSlot slot) {
        return BASE_DURABILITY[slot.getEntitySlotId()] * 25; // Modify multiplier for durability
    }

    @Override
    public int getProtectionAmount(EquipmentSlot slot) {
        return PROTECTION_VALUES[slot.getEntitySlotId()];
    }

    @Override
    public int getEnchantability() {
        return 25; // A number between 1-30 (higher means more enchantability)
    }

    @Override
    public SoundEvent getEquipSound() {
        return SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND; // Sound when equipped
    }

    @Override
    public Ingredient getRepairIngredient() {
        return Ingredient.ofItems(Items.AMETHYST_SHARD); // Item used for repairing the armor
    }



    public String getName() {
        String namemk2 = "amethystplus:energized_amethystmk2";
        return namemk2;
    }

    @Override
    public float getToughness() {
        return 2.5F; // Diamond-like toughness
    }

    @Override
    public float getKnockbackResistance() {
        return 0.5F; // Adds knockback resistance
    }


}
