package net.wetnoodle.eepygarden.registry;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.wetnoodle.eepygarden.EGConstants;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static net.minecraft.world.item.CreativeModeTabs.*;

public class EGInventorySorting {

    public static void init() {
        EGConstants.log("Adding items for The Garden Awoke Too Early to creative inventory", EGConstants.UNSTABLE_LOGGING);
        initBuildingBlocks();
        initNaturalBlocks();
        initIngredients();
    }

    private static void initBuildingBlocks() {
        addAfter(Blocks.MUD_BRICK_WALL, EGBlocks.RESIN_BRICKS, BUILDING_BLOCKS);
        addAfter(EGBlocks.RESIN_BRICKS, EGBlocks.RESIN_BRICK_STAIRS, BUILDING_BLOCKS);
        addAfter(EGBlocks.RESIN_BRICK_STAIRS, EGBlocks.RESIN_BRICK_SLAB, BUILDING_BLOCKS);
        addAfter(EGBlocks.RESIN_BRICK_SLAB, EGBlocks.RESIN_BRICK_WALL, BUILDING_BLOCKS);
        addAfter(EGBlocks.RESIN_BRICK_WALL, EGBlocks.CHISELED_RESIN_BRICKS, BUILDING_BLOCKS);
    }

    private static void initNaturalBlocks() {
        addBefore(Blocks.WITHER_ROSE, EGBlocks.OPEN_EYEBLOSSOM, NATURAL_BLOCKS);
        addBefore(EGBlocks.OPEN_EYEBLOSSOM, EGBlocks.CLOSED_EYEBLOSSOM, NATURAL_BLOCKS);
        addAfter(Blocks.HONEY_BLOCK, EGBlocks.RESIN_BLOCK, NATURAL_BLOCKS);
    }

    private static void initIngredients() {
        addAfter(Items.HONEYCOMB, EGBlocks.RESIN_CLUMP, INGREDIENTS);
        addAfter(Items.NETHER_BRICK, EGItems.RESIN_BRICK, INGREDIENTS);
    }

    // Copied from FrozenLib

    @SafeVarargs
    public static void add(ItemLike item, @NotNull ResourceKey<CreativeModeTab>... tabs) {
        if (item != null) {
            ResourceKey[] var2 = tabs;
            int var3 = tabs.length;

            for (int var4 = 0; var4 < var3; ++var4) {
                ResourceKey<CreativeModeTab> tab = var2[var4];
                ItemGroupEvents.modifyEntriesEvent(tab).register((entries) -> {
                    ItemStack stack = new ItemStack(item);
                    stack.setCount(1);
                    entries.accept(stack);
                });
            }
        }
    }

    @SafeVarargs
    public static void addBefore(ItemLike comparedItem, ItemLike item, ResourceKey<CreativeModeTab>... tabs) {
        addBefore(comparedItem, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
    }

    @SafeVarargs
    public static void addBefore(ItemLike comparedItem, ItemLike item, CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
        if (comparedItem != null && item != null) {
            ResourceKey[] var4 = tabs;
            int var5 = tabs.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                ResourceKey<CreativeModeTab> tab = var4[var6];
                ItemStack stack = new ItemStack(item);
                stack.setCount(1);
                List<ItemStack> list = List.of(stack);
                ItemGroupEvents.modifyEntriesEvent(tab).register((entries) -> {
                    entries.addBefore(comparedItem, list, tabVisibility);
                });
            }
        }
    }


    @SafeVarargs
    public static void addAfter(ItemLike comparedItem, ItemLike item, ResourceKey<CreativeModeTab>... tabs) {
        addAfter(comparedItem, item, CreativeModeTab.TabVisibility.PARENT_AND_SEARCH_TABS, tabs);
    }

    @SafeVarargs
    public static void addAfter(ItemLike comparedItem, ItemLike item, CreativeModeTab.TabVisibility tabVisibility, @NotNull ResourceKey<CreativeModeTab>... tabs) {
        if (comparedItem != null && item != null) {
            item.asItem();
            ResourceKey[] var4 = tabs;
            int var5 = tabs.length;

            for (int var6 = 0; var6 < var5; ++var6) {
                ResourceKey<CreativeModeTab> tab = var4[var6];
                ItemStack stack = new ItemStack(item);
                stack.setCount(1);
                List<ItemStack> list = List.of(stack);
                ItemGroupEvents.modifyEntriesEvent(tab).register((entries) -> {
                    entries.addAfter(comparedItem, list, tabVisibility);
                });
            }
        }
    }
}
