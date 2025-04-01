package end3r.amethystplus.armor;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;

public class ModArmorEffects {

    public static void registerEffects() {
        // Evento globale del server alla fine di ogni tick
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerWorld world : server.getWorlds()) {
                // Esegui logica per ogni giocatore nel mondo
                world.getPlayers().forEach(ModArmorEffects::applySlowFallingEffect);
            }
        });
    }


    private static void applySlowFallingEffect(PlayerEntity player) {
        // Controlliamo il mondo (solo lato server per prestazioni)
        if (player.world.isClient() || !(player.world instanceof ServerWorld)) return;

        // Verifica se il giocatore indossa il set completo
        if (isWearingFullCustomArmor(player)) {
            // Aggiungi l'effetto di Slow Falling al giocatore
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.SLOW_FALLING,  // Effetto Slow Falling
                    220,                        // Durata in tick (11 secondi)
                    0,                          // Livello dell'effetto, 0 = primo livello
                    true,                       // Effetto visibile (true per rimuovere particelle)
                    false                       // Mantieni invisibile l'icona dell'effetto
            ));
        }
    }

    private static boolean isWearingFullCustomArmor(PlayerEntity player) {
        // Verifica ogni pezzo dell'armatura
        ItemStack helmet = player.getInventory().getArmorStack(3); // Slot per elmetto
        ItemStack chestplate = player.getInventory().getArmorStack(2); // Slot per corazza
        ItemStack leggings = player.getInventory().getArmorStack(1); // Slot per gambali
        ItemStack boots = player.getInventory().getArmorStack(0); // Slot per stivali

        // Controlla se ogni pezzo corrisponde all'armatura personalizzata
        return isCustomArmor(helmet, ModArmors.AMETHYST_HELMET)
                && isCustomArmor(chestplate, ModArmors.AMETHYST_CHESTPLATE)
                && isCustomArmor(leggings, ModArmors.AMETHYST_LEGGINGS)
                && isCustomArmor(boots, ModArmors.AMETHYST_BOOTS);
    }

    // Metodo per confrontare un pezzo dell'armatura
    private static boolean isCustomArmor(ItemStack itemStack, Item armorItem) {
        return !itemStack.isEmpty() && itemStack.getItem() == armorItem;
    }
}
