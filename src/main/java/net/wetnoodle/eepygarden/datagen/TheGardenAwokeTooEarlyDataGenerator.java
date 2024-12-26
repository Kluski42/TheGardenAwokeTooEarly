package net.wetnoodle.eepygarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.wetnoodle.eepygarden.datagen.loot.EGBlockLootProvider;
import net.wetnoodle.eepygarden.datagen.recipe.EGRecipeProvider;
import net.wetnoodle.eepygarden.datagen.tag.EGBlockTagProvider;
import net.wetnoodle.eepygarden.datagen.tag.EGItemTagProvider;

public class TheGardenAwokeTooEarlyDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {
        final FabricDataGenerator.Pack pack = dataGenerator.createPack();
        pack.addProvider(EGBlockLootProvider::new);
        pack.addProvider(EGRecipeProvider::new);
        pack.addProvider(EGBlockTagProvider::new);
        pack.addProvider(EGItemTagProvider::new);
    }
}
