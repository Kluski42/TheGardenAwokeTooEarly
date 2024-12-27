package net.wetnoodle.eepygarden.registry;

import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.SoundType;
import net.wetnoodle.eepygarden.EGConstants;
import org.jetbrains.annotations.NotNull;

public class EGSounds {
    // Eyeblossom
    public static final SoundEvent EYEBLOSSOM_OPEN_LONG = register("block.eyeblossom.open_long");
    public static final SoundEvent EYEBLOSSOM_OPEN = register("block.eyeblossom.open");
    public static final SoundEvent EYEBLOSSOM_CLOSE_LONG = register("block.eyeblossom.close_long");
    public static final SoundEvent EYEBLOSSOM_CLOSE = register("block.eyeblossom.close");
    public static final SoundEvent EYEBLOSSOM_IDLE = register("block.eyeblossom.idle");
    // Resin
    public static final SoundEvent RESIN_BREAK = register("block.resin.break");
    public static final SoundEvent RESIN_FALL = register("block.resin.fall");
    public static final SoundEvent RESIN_PLACE = register("block.resin.place");
    public static final SoundEvent RESIN_STEP = register("block.resin.step");

    public static final SoundType RESIN_TYPE = new SoundType(
            1.0F, 1.0F, RESIN_BREAK, RESIN_STEP, RESIN_PLACE, SoundEvents.EMPTY, RESIN_FALL
    );
    // Resin Bricks
    public static final SoundEvent RESIN_BRICKS_BREAK = register("block.resin_bricks.break");
    public static final SoundEvent RESIN_BRICKS_FALL = register("block.resin_bricks.fall");
    public static final SoundEvent RESIN_BRICKS_HIT = register("block.resin_bricks.hit");
    public static final SoundEvent RESIN_BRICKS_PLACE = register("block.resin_bricks.place");
    public static final SoundEvent RESIN_BRICKS_STEP = register("block.resin_bricks.step");

    public static final SoundType RESIN_BRICKS_TYPE = new SoundType(
            1.0F, 1.0F, RESIN_BRICKS_BREAK, RESIN_BRICKS_STEP, RESIN_BRICKS_PLACE, RESIN_BRICKS_HIT, RESIN_BRICKS_FALL
    );

    public static void init() {
    }

    @NotNull
    private static SoundEvent register(@NotNull String string) {
        ResourceLocation resourceLocation = EGConstants.id(string);
        return Registry.register(BuiltInRegistries.SOUND_EVENT, resourceLocation, SoundEvent.createVariableRangeEvent(resourceLocation));
    }
}
