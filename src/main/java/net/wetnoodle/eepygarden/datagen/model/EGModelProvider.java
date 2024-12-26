package net.wetnoodle.eepygarden.datagen.model;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.model.ModelTemplates;
import net.wetnoodle.eepygarden.registry.EGBlocks;

public class EGModelProvider extends FabricModelProvider {
    public EGModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.family(EGBlocks.RESIN_BRICKS).generateFor(EGBlocks.FAMILY_RESIN_BRICKS);
        generator.createTrivialCube(EGBlocks.RESIN_BLOCK);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(EGBlocks.RESIN_CLUMP.asItem(), ModelTemplates.FLAT_ITEM);
    }
}
