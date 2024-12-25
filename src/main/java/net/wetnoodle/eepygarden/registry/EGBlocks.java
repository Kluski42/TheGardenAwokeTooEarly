package net.wetnoodle.eepygarden.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerPotBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.PushReaction;
import net.wetnoodle.eepygarden.EGConstants;
import net.wetnoodle.eepygarden.block.EyeblossomBlock;

import java.util.function.Function;

public class EGBlocks {
    public static final Block OPEN_EYEBLOSSOM = register(
            "open_eyeblossom",
            properties -> new EyeblossomBlock(EyeblossomBlock.Type.OPEN, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(Blocks.CREAKING_HEART.defaultMapColor())
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );

    public static final Block CLOSED_EYEBLOSSOM = register(
            "closed_eyeblossom",
            properties -> new EyeblossomBlock(EyeblossomBlock.Type.CLOSED, properties),
            BlockBehaviour.Properties.of()
                    .mapColor(Blocks.PALE_OAK_LEAVES.defaultMapColor())
                    .noCollission()
                    .instabreak()
                    .sound(SoundType.GRASS)
                    .offsetType(BlockBehaviour.OffsetType.XZ)
                    .pushReaction(PushReaction.DESTROY)
                    .randomTicks()
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );

    public static final Block POTTED_OPEN_EYEBLOSSOM = registerWithoutItem(
            "potted_open_eyeblossom", properties -> new FlowerPotBlock(OPEN_EYEBLOSSOM, properties), Blocks.flowerPotProperties().randomTicks()
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );
    public static final Block POTTED_CLOSED_EYEBLOSSOM = registerWithoutItem(
            "potted_closed_eyeblossom", properties -> new FlowerPotBlock(CLOSED_EYEBLOSSOM, properties), Blocks.flowerPotProperties().randomTicks()
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );

    public static void init() {
        EGConstants.log("Registering blocks for The Garden Awoke Too Early", EGConstants.UNSTABLE_LOGGING);
    }

    private static <T extends Block> T registerWithoutItem(String path, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        ResourceLocation id = EGConstants.id(path);
        return doRegister(id, makeBlock(block, properties, id));
    }

    private static <T extends Block> T register(String path, Function<BlockBehaviour.Properties, T> block, BlockBehaviour.Properties properties) {
        T registered = registerWithoutItem(path, block, properties);
        Items.registerBlock(registered);
        return registered;
    }

    private static <T extends Block> T doRegister(ResourceLocation id, T block) {
        if (BuiltInRegistries.BLOCK.getOptional(id).isEmpty()) {
            return Registry.register(BuiltInRegistries.BLOCK, id, block);
        }
        throw new IllegalArgumentException("Block with id " + id + " is already in the block registry.");
    }

    private static <T extends Block> T makeBlock(Function<BlockBehaviour.Properties, T> function, BlockBehaviour.Properties properties, ResourceLocation id) {
        return function.apply(properties.setId(ResourceKey.create(Registries.BLOCK, id)));
    }
}
