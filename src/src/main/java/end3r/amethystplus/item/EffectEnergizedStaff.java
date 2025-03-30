package end3r.amethystplus.item;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public class EffectEnergizedStaff extends Item {
    public EffectEnergizedStaff(Settings settings) {
        super(settings);
    }

    // Called when attacking an entity
    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        target.setOnFireFor(4); // Sets entity on fire for 4 seconds
        target.damage(attacker.getDamageSources().playerAttack((PlayerEntity) attacker), 5.0F); // Deals 5 extra damage
        return true;
    }

    // Called when right-clicking an entity
    @Override
    public ActionResult useOnEntity(ItemStack stack, PlayerEntity user, LivingEntity entity, Hand hand) {
        entity.setOnFireFor(4); // Also sets entity on fire on right-click
        entity.damage(user.getDamageSources().playerAttack(user), 5.0F); // Deals 5 extra damage
        return ActionResult.SUCCESS;
    }
}