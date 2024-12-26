package net.wetnoodle.eepygarden.datagen.loot;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.core.HolderLookup;
import net.wetnoodle.eepygarden.registry.EGBlocks;

import java.util.concurrent.CompletableFuture;

public class EGBlockLootProvider extends FabricBlockLootTableProvider {
    public EGBlockLootProvider(FabricDataOutput dataOutput, CompletableFuture<HolderLookup.Provider> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        this.dropSelf(EGBlocks.OPEN_EYEBLOSSOM);
        this.dropSelf(EGBlocks.CLOSED_EYEBLOSSOM);
        this.dropPottedContents(EGBlocks.POTTED_OPEN_EYEBLOSSOM);
        this.dropPottedContents(EGBlocks.POTTED_CLOSED_EYEBLOSSOM);
    }
}
