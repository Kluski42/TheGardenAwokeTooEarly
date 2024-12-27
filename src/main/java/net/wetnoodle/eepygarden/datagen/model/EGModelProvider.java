package net.wetnoodle.eepygarden.datagen.model;

import com.mojang.datafixers.util.Pair;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.Util;
import net.minecraft.core.Direction;
import net.minecraft.data.models.BlockModelGenerators;
import net.minecraft.data.models.ItemModelGenerators;
import net.minecraft.data.models.blockstates.Condition;
import net.minecraft.data.models.blockstates.MultiPartGenerator;
import net.minecraft.data.models.blockstates.Variant;
import net.minecraft.data.models.model.ModelLocationUtils;
import net.minecraft.data.models.model.ModelTemplates;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.MultifaceBlock;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import net.wetnoodle.eepygarden.registry.EGItems;

import java.util.function.Function;

public class EGModelProvider extends FabricModelProvider {
    public EGModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockModelGenerators generator) {
        generator.family(EGBlocks.RESIN_BRICKS).generateFor(EGBlocks.FAMILY_RESIN_BRICKS);
        generator.createTrivialCube(EGBlocks.RESIN_BLOCK);
        createMultifaceWithoutItem(generator, EGBlocks.RESIN_CLUMP);
//        generator.createMultiface(EGBlocks.RESIN_CLUMP);
    }

    @Override
    public void generateItemModels(ItemModelGenerators generator) {
        generator.generateFlatItem(EGBlocks.RESIN_CLUMP.asItem(), ModelTemplates.FLAT_ITEM);
        generator.generateFlatItem(EGItems.RESIN_BRICK, ModelTemplates.FLAT_ITEM);
    }


    // Custom generators

    public final void createMultifaceWithoutItem(BlockModelGenerators generator, Block block) {
        ResourceLocation resourceLocation = ModelLocationUtils.getModelLocation(block);
        MultiPartGenerator multiPartGenerator = MultiPartGenerator.multiPart(block);
        Condition.TerminalCondition terminalCondition = Util.make(
                Condition.condition(),
                terminalConditionx -> BlockModelGenerators.MULTIFACE_GENERATOR.stream().map(Pair::getFirst).map(MultifaceBlock::getFaceProperty).forEach(booleanPropertyx -> {
                    if (block.defaultBlockState().hasProperty(booleanPropertyx)) {
                        terminalConditionx.term(booleanPropertyx, false);
                    }
                })
        );

        for (Pair<Direction, Function<ResourceLocation, Variant>> pair : BlockModelGenerators.MULTIFACE_GENERATOR) {
            BooleanProperty booleanProperty = MultifaceBlock.getFaceProperty(pair.getFirst());
            Function<ResourceLocation, Variant> function = pair.getSecond();
            if (block.defaultBlockState().hasProperty(booleanProperty)) {
                multiPartGenerator.with(Condition.condition().term(booleanProperty, true), function.apply(resourceLocation));
                multiPartGenerator.with(terminalCondition, function.apply(resourceLocation));
            }
        }
        generator.blockStateOutput.accept(multiPartGenerator);
    }
}
