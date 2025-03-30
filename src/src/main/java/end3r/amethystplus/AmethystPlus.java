package end3r.amethystplus;

import end3r.amethystplus.item.ModItemGroups;
import end3r.amethystplus.item.ModItems;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AmethystPlus implements ModInitializer {
	public static final String MOD_ID = "amethystplus";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {

		ModItems.registerModItems();
		ModItemGroups.registerItemGroups();




		LOGGER.info("loading " + AmethystPlus.MOD_ID);
	}
}