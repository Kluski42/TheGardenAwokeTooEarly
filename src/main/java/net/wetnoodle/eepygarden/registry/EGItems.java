package net.wetnoodle.eepygarden.registry;

import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.wetnoodle.eepygarden.EGConstants;
import org.jetbrains.annotations.NotNull;

import java.util.function.Function;

public class EGItems {
    public static final Item RESIN_BRICK = register("resin_brick");

    public static void init() {
        EGConstants.log("Registering items for The Garden Awoke Too Early", EGConstants.UNSTABLE_LOGGING);
    }

    private static @NotNull Item register(String name) {
        return register(name, Item::new);
    }

    private static @NotNull Item register(String name, @NotNull Function<Item.Properties, Item> function) {
        return register(name, function, new Item.Properties());
    }

    private static @NotNull Item register(String name, @NotNull Function<Item.Properties, Item> function, Item.@NotNull Properties properties) {
        return Items.registerItem(ResourceKey.create(Registries.ITEM, EGConstants.id(name)), function, properties);
    }
}
