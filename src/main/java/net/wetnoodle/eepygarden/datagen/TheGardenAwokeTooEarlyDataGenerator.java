package net.wetnoodle.eepygarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.wetnoodle.eepygarden.datagen.loot.EGBlockLootProvider;
import net.wetnoodle.eepygarden.datagen.recipe.EGRecipeProvider;

public class TheGardenAwokeTooEarlyDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        final FabricDataGenerator.Pack pack = dataGenerator.createPack();
        pack.addProvider(EGBlockLootProvider::new);
        pack.addProvider(EGRecipeProvider::new);
    }
}
