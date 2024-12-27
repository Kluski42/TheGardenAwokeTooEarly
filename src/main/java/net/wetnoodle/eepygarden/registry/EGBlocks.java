package net.wetnoodle.eepygarden.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.BlockFamilies;
import net.minecraft.data.BlockFamily;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.wetnoodle.eepygarden.EGConstants;
import net.wetnoodle.eepygarden.block.EyeblossomBlock;
import net.wetnoodle.eepygarden.block.ResinClumpBlock;

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

    private static final SoundType RESIN = SoundType.AMETHYST;
    private static final SoundType SoundTypeRESIN_BRICKS = SoundType.DEEPSLATE_BRICKS;

    public static final Block RESIN_CLUMP = register(
            "resin_clump",
            ResinClumpBlock::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .replaceable()
                    .noCollission()
                    .sound(RESIN)
                    .ignitedByLava()
                    .pushReaction(PushReaction.DESTROY)
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );

    public static final Block RESIN_BLOCK = register(
            "resin_block",
            Block::new,
            BlockBehaviour.Properties.of().mapColor(MapColor.TERRACOTTA_ORANGE).instrument(NoteBlockInstrument.BASEDRUM).sound(RESIN)
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );
    public static final Block RESIN_BRICKS = register(
            "resin_bricks",
            Block::new,
            BlockBehaviour.Properties.of()
                    .mapColor(MapColor.TERRACOTTA_ORANGE)
                    .instrument(NoteBlockInstrument.BASEDRUM)
                    .requiresCorrectToolForDrops()
                    .sound(SoundTypeRESIN_BRICKS)
                    .strength(1.5F, 6.0F)
                    .requiredFeatures(FeatureFlags.WINTER_DROP)
    );
    public static final Block RESIN_BRICK_STAIRS = register(
            "resin_brick_stairs",
            properties -> new StairBlock(RESIN_BRICKS.defaultBlockState(), properties),
            BlockBehaviour.Properties.ofFullCopy(RESIN_BRICKS)
    );
    public static final Block RESIN_BRICK_SLAB = register(
            "resin_brick_slab",
            SlabBlock::new,
            BlockBehaviour.Properties.ofFullCopy(RESIN_BRICKS)
    );
    public static final Block RESIN_BRICK_WALL = register(
            "resin_brick_wall",
            WallBlock::new,
            BlockBehaviour.Properties.ofFullCopy(RESIN_BRICKS)
    );
    public static final Block CHISELED_RESIN_BRICKS = register(
            "chiseled_resin_bricks",
            Block::new,
            BlockBehaviour.Properties.ofFullCopy(RESIN_BRICKS)
    );

    public static final BlockFamily FAMILY_RESIN_BRICKS = BlockFamilies.familyBuilder(RESIN_BRICKS)
            .wall(RESIN_BRICK_WALL)
            .stairs(RESIN_BRICK_STAIRS)
            .slab(RESIN_BRICK_SLAB)
            .chiseled(CHISELED_RESIN_BRICKS)
            .getFamily();

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
