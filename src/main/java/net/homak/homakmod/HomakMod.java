package net.homak.homakmod;

import net.fabricmc.api.ModInitializer;
import net.homak.homakmod.block.ModBlocks;
import net.homak.homakmod.component.ModDataComponentTypes;
import net.homak.homakmod.effect.ModEffects;
import net.homak.homakmod.item.ModItems;
import net.homak.homakmod.util.handler.ServerTickHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomakMod implements ModInitializer {
	public static final String MOD_ID = "homakmod";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
	@Override
	public void onInitialize() {
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEffects.registerEffects();
		ModDataComponentTypes.registerDataComponentTypes();
		ServerTickHandler.register();
		HomakMod.LOGGER.info("Hello from Soulmade!");
	}
}