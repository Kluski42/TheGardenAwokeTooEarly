package net.wetnoodle.eepygarden.datagen.recipe;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.SuspiciousEffectHolder;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

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
                oneToOneConversionRecipe(Items.GRAY_DYE, EGBlocks.CLOSED_EYEBLOSSOM, "gray_dye");
                oneToOneConversionRecipe(Items.ORANGE_DYE, EGBlocks.OPEN_EYEBLOSSOM, "orange_dye");
                suspiciousStew(EGBlocks.OPEN_EYEBLOSSOM.asItem(), (SuspiciousEffectHolder) EGBlocks.OPEN_EYEBLOSSOM);
                suspiciousStew(EGBlocks.CLOSED_EYEBLOSSOM.asItem(), (SuspiciousEffectHolder) EGBlocks.CLOSED_EYEBLOSSOM);
            }
        };
    }

    @Override
    @NotNull
    public String getName() {
        return "The Garden Awoke Too Early Recipes";
    }
}
