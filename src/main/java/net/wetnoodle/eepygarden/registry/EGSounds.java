package net.wetnoodle.eepygarden.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.wetnoodle.eepygarden.EGConstants;
import org.jetbrains.annotations.NotNull;

public class EGSounds {
    public static final SoundEvent EYEBLOSSOM_OPEN_LONG = register("block.eyeblossom.open_long");
    public static final SoundEvent EYEBLOSSOM_OPEN = register("block.eyeblossom.open");
    public static final SoundEvent EYEBLOSSOM_CLOSE_LONG = register("block.eyeblossom.close_long");
    public static final SoundEvent EYEBLOSSOM_CLOSE = register("block.eyeblossom.close");
    public static final SoundEvent EYEBLOSSOM_IDLE = register("block.eyeblossom.idle");

    public static void init() {}

    @NotNull
    private static SoundEvent register(@NotNull String string) {
        ResourceLocation resourceLocation = EGConstants.id(string);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
