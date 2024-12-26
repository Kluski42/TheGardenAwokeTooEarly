package net.wetnoodle.eepygarden.datagen.tag;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.tags.ItemTags;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class EGItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public EGItemTagProvider(@NotNull FabricDataOutput output, @NotNull CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        this.getOrCreateTagBuilder(ItemTags.FLOWERS)
                .add(EGBlocks.CLOSED_EYEBLOSSOM.asItem())
                .add(EGBlocks.OPEN_EYEBLOSSOM.asItem());
        this.getOrCreateTagBuilder(ItemTags.SMALL_FLOWERS)
                .add(EGBlocks.CLOSED_EYEBLOSSOM.asItem())
                .add(EGBlocks.OPEN_EYEBLOSSOM.asItem());
    }
}
