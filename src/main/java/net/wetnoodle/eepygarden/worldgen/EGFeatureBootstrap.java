package net.wetnoodle.eepygarden.worldgen;

import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstrapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.placement.*;
import net.wetnoodle.eepygarden.EGConstants;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public class EGFeatureBootstrap {
//    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PALE_GARDEN = FeatureUtils.createKey("flower_pale_garden");

    public static final PlacementModifier HEIGHTMAP_NO_LEAVES = HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES);


    public static final ResourceKey<ConfiguredFeature<?, ?>> FLOWER_PALE_GARDEN = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            EGConstants.id("flower_pale_garden")
    );

    public static final ResourceKey<ConfiguredFeature<?, ?>> PALE_FOREST_FLOWERS = ResourceKey.create(
            Registries.CONFIGURED_FEATURE,
            EGConstants.id("pale_forest_flowers")
    );


    public static void bootstrapConfigured(@NotNull BootstrapContext<ConfiguredFeature<?, ?>> bootstrapContext) {
        final var configuredFeatures = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);
        final var placedFeatures = bootstrapContext.lookup(Registries.PLACED_FEATURE);
        register(
                bootstrapContext,
                FLOWER_PALE_GARDEN,
                Feature.FLOWER,
                new RandomPatchConfiguration(
                        // Note that this doesn't schedule a tick like the vanilla version does
                        1, 0, 0, PlacementUtils.onlyWhenEmpty(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EGBlocks.CLOSED_EYEBLOSSOM)))
                )
        );

        register(
                bootstrapContext,
                PALE_FOREST_FLOWERS,
                Feature.RANDOM_PATCH,
                // Note that this doesn't schedule a tick like the vanilla version does
                FeatureUtils.simplePatchConfiguration(Feature.SIMPLE_BLOCK, new SimpleBlockConfiguration(BlockStateProvider.simple(EGBlocks.CLOSED_EYEBLOSSOM)))
        );
    }

//    public static final ResourceKey<PlacedFeature> PALE_GARDEN_FLOWERS_PLACED = PlacementUtils.createKey("pale_garden_flowers");
//    public static final ResourceKey<PlacedFeature> FLOWER_PALE_GARDEN_PLACED = PlacementUtils.createKey("flower_pale_garden");

    public static final ResourceKey<PlacedFeature> FLOWER_PALE_GARDEN_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            EGConstants.id("flower_pale_garden")
    );
    public static final ResourceKey<PlacedFeature> PALE_GARDEN_FLOWERS_PLACED = ResourceKey.create(
            Registries.PLACED_FEATURE,
            EGConstants.id("pale_garden_flowers")
    );


    public static void bootstrapPlaced(@NotNull BootstrapContext<PlacedFeature> bootstrapContext) {
        final var placedFeatures = bootstrapContext.lookup(Registries.PLACED_FEATURE);
        final var configuredFeatures = bootstrapContext.lookup(Registries.CONFIGURED_FEATURE);

        register(
                bootstrapContext,
                FLOWER_PALE_GARDEN_PLACED,
                // holder26
                configuredFeatures.getOrThrow(FLOWER_PALE_GARDEN),
                RarityFilter.onAverageOnceEvery(32),
                InSquarePlacement.spread(),
                PlacementUtils.HEIGHTMAP,
                BiomeFilter.biome()
        );


        register(
                bootstrapContext,
                PALE_GARDEN_FLOWERS_PLACED,
                // holder31
                configuredFeatures.getOrThrow(PALE_FOREST_FLOWERS),
                RarityFilter.onAverageOnceEvery(8),
                InSquarePlacement.spread(),
                HEIGHTMAP_NO_LEAVES,
                BiomeFilter.biome()
        );

    }

    public static void register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, PlacementModifier... modifiers) {
        register(entries, resourceKey, configuredHolder, Arrays.asList(modifiers));
    }

    private static void register(BootstrapContext<PlacedFeature> entries, ResourceKey<PlacedFeature> resourceKey, Holder<ConfiguredFeature<?, ?>> configuredHolder, List<PlacementModifier> modifiers) {
        PlacementUtils.register(entries, resourceKey, configuredHolder, modifiers);
    }

    private static <FC extends FeatureConfiguration, F extends Feature<FC>> void register(BootstrapContext<ConfiguredFeature<?, ?>> entries, ResourceKey<ConfiguredFeature<?, ?>> resourceKey, F feature, FC featureConfiguration) {
        FeatureUtils.register(entries, resourceKey, feature, featureConfiguration);
    }
}
