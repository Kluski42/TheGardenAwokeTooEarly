package net.wetnoodle.eepygarden.worldgen;

import net.fabricmc.fabric.api.biome.v1.BiomeModificationContext;
import net.fabricmc.fabric.api.biome.v1.BiomeModifications;
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors;
import net.fabricmc.fabric.api.biome.v1.ModificationPhase;
import net.minecraft.data.worldgen.WinterDropBiomes;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.wetnoodle.eepygarden.EGConstants;

public class EGBiomeModifications {
    public static void init() {
        BiomeModifications.create(EGConstants.id("eyeblossom"))
                .add(
                        ModificationPhase.ADDITIONS,
                        BiomeSelectors.all(),
                        ((biomeSelectionContext, context) -> {
                            BiomeModificationContext.GenerationSettingsContext generationSettings = context.getGenerationSettings();
                            BiomeModificationContext.SpawnSettingsContext spawnSettings = context.getSpawnSettings();

                            if (biomeSelectionContext.getBiomeKey().equals(WinterDropBiomes.PALE_GARDEN)) {
                                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EGFeatureBootstrap.PALE_GARDEN_FLOWERS_PLACED);
                                generationSettings.addFeature(GenerationStep.Decoration.VEGETAL_DECORATION, EGFeatureBootstrap.FLOWER_PALE_GARDEN_PLACED);
                            }
                        })
                );
    }
}
