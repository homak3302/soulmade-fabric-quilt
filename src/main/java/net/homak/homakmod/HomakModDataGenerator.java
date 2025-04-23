package net.homak.homakmod;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.homak.homakmod.datagen.ModBlockTagProvider;
import net.homak.homakmod.datagen.ModLootTableProvider;
import net.homak.homakmod.datagen.ModModelProvider;

public class HomakModDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();

		pack.addProvider(ModLootTableProvider::new);
		pack.addProvider(ModBlockTagProvider::new);
		pack.addProvider(ModModelProvider::new);

	}
}
