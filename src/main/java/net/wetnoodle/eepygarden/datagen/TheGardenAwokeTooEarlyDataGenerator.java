package net.wetnoodle.eepygarden.datagen;

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.core.registries.Registries;
import net.wetnoodle.eepygarden.EGConstants;
import net.wetnoodle.eepygarden.worldgen.EGFeatureBootstrap;
import org.jetbrains.annotations.NotNull;

public class TheGardenAwokeTooEarlyDataGenerator implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator dataGenerator) {

    }

    @Override
    public void buildRegistry(@NotNull RegistrySetBuilder registryBuilder) {
        EGConstants.log("Building datagen registries for The Garden Awoke Too Early", EGConstants.UNSTABLE_LOGGING);

        registryBuilder.add(Registries.CONFIGURED_FEATURE, EGFeatureBootstrap::bootstrapConfigured);
        registryBuilder.add(Registries.PLACED_FEATURE, EGFeatureBootstrap::bootstrapPlaced);

    }
}
