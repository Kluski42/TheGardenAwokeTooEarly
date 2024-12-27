package net.wetnoodle.eepygarden.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.BlockTags;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EGBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public EGBlockTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(EGBlocks.RESIN_BRICKS)
                .add(EGBlocks.RESIN_BRICK_SLAB)
                .add(EGBlocks.RESIN_BRICK_STAIRS)
                .add(EGBlocks.RESIN_BRICK_WALL)
                .add(EGBlocks.CHISELED_RESIN_BRICKS);

        this.getOrCreateTagBuilder(BlockTags.FLOWERS)
                .add(EGBlocks.CLOSED_EYEBLOSSOM)
                .add(EGBlocks.OPEN_EYEBLOSSOM);
        this.getOrCreateTagBuilder(BlockTags.SMALL_FLOWERS)
                .add(EGBlocks.CLOSED_EYEBLOSSOM)
                .add(EGBlocks.OPEN_EYEBLOSSOM);

        this.getOrCreateTagBuilder(BlockTags.COMBINATION_STEP_SOUND_BLOCKS)
                .add(EGBlocks.RESIN_CLUMP);
        this.getOrCreateTagBuilder(BlockTags.WALLS)
                .add(EGBlocks.RESIN_BRICK_WALL);
    }
}
