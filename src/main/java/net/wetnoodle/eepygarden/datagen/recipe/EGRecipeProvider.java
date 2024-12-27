package net.wetnoodle.eepygarden.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

import static net.wetnoodle.eepygarden.registry.EGBlocks.*;
import static net.wetnoodle.eepygarden.registry.EGItems.*;

public class EGRecipeProvider extends FabricRecipeProvider {
    public EGRecipeProvider(FabricDataOutput output, CompletableFuture<HolderLookup.Provider> registriesFuture) {
        super(output, registriesFuture);
    }


    @Override
    protected RecipeProvider createRecipeProvider(HolderLookup.Provider registryLookup, RecipeOutput exporter) {
        return new RecipeProvider(registryLookup, exporter) {
            @Override
            public void buildRecipes() {
                HolderLookup.Provider registries = this.registries;
                RecipeOutput output = this.output;
                oneToOneConversionRecipe(Items.GRAY_DYE, CLOSED_EYEBLOSSOM, "gray_dye");
                oneToOneConversionRecipe(Items.ORANGE_DYE, OPEN_EYEBLOSSOM, "orange_dye");
                suspiciousStew(OPEN_EYEBLOSSOM.asItem(), (SuspiciousEffectHolder) OPEN_EYEBLOSSOM);
                suspiciousStew(CLOSED_EYEBLOSSOM.asItem(), (SuspiciousEffectHolder) CLOSED_EYEBLOSSOM);

                nineBlockStorageRecipes(RecipeCategory.MISC, RESIN_CLUMP, RecipeCategory.BUILDING_BLOCKS, RESIN_BLOCK);

                this.shaped(RecipeCategory.MISC, Blocks.CREAKING_HEART)
                        .define('L', Blocks.PALE_OAK_LOG)
                        .define('R', RESIN_BLOCK)
                        .pattern("L")
                        .pattern("R")
                        .pattern("L")
                        .unlockedBy(getHasName(RESIN_BLOCK), has(RESIN_BLOCK))
                        .save(output);

                SimpleCookingRecipeBuilder.smelting(Ingredient.of(RESIN_CLUMP), RecipeCategory.MISC, RESIN_BRICK, 0.1F, 200);

                this.twoByTwoPacker(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICKS, RESIN_BRICK);
                this.slab(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICK_SLAB, RESIN_BRICKS);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICK_SLAB, RESIN_BRICKS, 2);
                build(this, this.stairBuilder(RESIN_BRICK_STAIRS, Ingredient.of(RESIN_BRICKS)), RESIN_BRICKS, output);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICK_STAIRS, RESIN_BRICKS);
                this.wall(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICK_WALL, RESIN_BRICKS);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, RESIN_BRICK_WALL, RESIN_BRICKS);
                this.chiseled(RecipeCategory.BUILDING_BLOCKS, CHISELED_RESIN_BRICKS, RESIN_BRICK_SLAB);
                this.stonecutterResultFromBase(RecipeCategory.BUILDING_BLOCKS, CHISELED_RESIN_BRICKS, RESIN_BRICKS);
            }
        };
    }

    private void build(RecipeProvider recipeProvider, RecipeBuilder recipeBuilder, ItemLike itemLike2, RecipeOutput output) {
        recipeBuilder.unlockedBy(RecipeProvider.getHasName(itemLike2), recipeProvider.has(itemLike2)).save(output);
    }

    @Override
    @NotNull
    public String getName() {
        return "The Garden Awoke Too Early Recipes";
    }
}
