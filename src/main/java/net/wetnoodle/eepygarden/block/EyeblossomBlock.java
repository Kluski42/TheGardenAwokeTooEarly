package net.wetnoodle.eepygarden.block;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.particles.TargetColorParticleOption;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.Difficulty;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.animal.Bee;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.gameevent.GameEvent.Context;
import net.minecraft.world.phys.Vec3;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import net.wetnoodle.eepygarden.registry.EGSounds;

public class EyeblossomBlock extends FlowerBlock {
    public static final MapCodec<EyeblossomBlock> CODEC = RecordCodecBuilder.mapCodec(
            instance -> instance.group(Codec.BOOL.fieldOf("open").forGetter(eyeblossomBlock -> eyeblossomBlock.type.open), propertiesCodec())
                    .apply(instance, EyeblossomBlock::new)
    );
    private static final int EYEBLOSSOM_XZ_RANGE = 3;
    private static final int EYEBLOSSOM_Y_RANGE = 2;
    private final EyeblossomBlock.Type type;

    @Override
    public MapCodec<? extends EyeblossomBlock> codec() {
        return CODEC;
    }

    public EyeblossomBlock(EyeblossomBlock.Type type, BlockBehaviour.Properties properties) {
        super(type.effect, type.effectDuration, properties);
        this.type = type;
    }

    public EyeblossomBlock(boolean bl, BlockBehaviour.Properties properties) {
        super(EyeblossomBlock.Type.fromBoolean(bl).effect, EyeblossomBlock.Type.fromBoolean(bl).effectDuration, properties);
        this.type = EyeblossomBlock.Type.fromBoolean(bl);
    }

    @Override
    public void animateTick(BlockState blockState, Level level, BlockPos blockPos, RandomSource randomSource) {
        if (this.type.emitSounds() && randomSource.nextInt(700) == 0) {
            BlockState blockState2 = level.getBlockState(blockPos.below());
            if (blockState2.is(Blocks.PALE_MOSS_BLOCK)) {
                level.playLocalSound(
                        (double) blockPos.getX(), (double) blockPos.getY(), (double) blockPos.getZ(), EGSounds.EYEBLOSSOM_IDLE, SoundSource.BLOCKS, 1.0F, 1.0F, false
                );
            }
        }
    }

    @Override
    protected void randomTick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.tryChangingState(blockState, serverLevel, blockPos, randomSource)) {
            serverLevel.playSound(null, blockPos, this.type.transform().longSwitchSound, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        super.randomTick(blockState, serverLevel, blockPos, randomSource);
    }

    @Override
    protected void tick(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (this.tryChangingState(blockState, serverLevel, blockPos, randomSource)) {
            serverLevel.playSound(null, blockPos, this.type.transform().shortSwitchSound, SoundSource.BLOCKS, 1.0F, 1.0F);
        }

        super.tick(blockState, serverLevel, blockPos, randomSource);
    }

    private boolean tryChangingState(BlockState blockState, ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
        if (!serverLevel.dimensionType().natural()) {
            return false;
        } else if (serverLevel.isDay() != this.type.open) {
            return false;
        } else {
            EyeblossomBlock.Type type = this.type.transform();
            serverLevel.setBlock(blockPos, type.state(), Block.UPDATE_ALL);
            serverLevel.gameEvent(GameEvent.BLOCK_CHANGE, blockPos, Context.of(blockState));
            type.spawnTransformParticle(serverLevel, blockPos, randomSource);
            BlockPos.betweenClosed(blockPos.offset(-EYEBLOSSOM_XZ_RANGE, -EYEBLOSSOM_Y_RANGE, -EYEBLOSSOM_XZ_RANGE), blockPos.offset(EYEBLOSSOM_XZ_RANGE, EYEBLOSSOM_Y_RANGE, EYEBLOSSOM_XZ_RANGE)).forEach(blockPos2 -> {
                BlockState blockState2 = serverLevel.getBlockState(blockPos2);
                if (blockState2 == blockState) {
                    double d = Math.sqrt(blockPos.distSqr(blockPos2));
                    int i = randomSource.nextIntBetweenInclusive((int) (d * 5.0), (int) (d * 10.0));
                    serverLevel.scheduleTick(blockPos2, blockState.getBlock(), i);
                }
            });
            return true;
        }
    }

    @Override
    protected void entityInside(BlockState blockState, Level level, BlockPos blockPos, Entity entity) {
        if (!level.isClientSide()
                && level.getDifficulty() != Difficulty.PEACEFUL
                && entity instanceof Bee bee
//                && Bee.attractsBees(blockState)
                && !bee.hasEffect(MobEffects.POISON)) {
            bee.addEffect(this.getBeeInteractionEffect());
        }
    }

    //    @Override
    public MobEffectInstance getBeeInteractionEffect() {
        return new MobEffectInstance(MobEffects.POISON, 25);
    }

    public enum Type {
        OPEN(true, MobEffects.BLINDNESS, 11.0F, EGSounds.EYEBLOSSOM_OPEN_LONG, EGSounds.EYEBLOSSOM_OPEN, 16545810),
        CLOSED(false, MobEffects.CONFUSION, 7.0F, EGSounds.EYEBLOSSOM_CLOSE_LONG, EGSounds.EYEBLOSSOM_CLOSE, 6250335);

        final boolean open;
        public final Holder<MobEffect> effect;
        final float effectDuration;
        final SoundEvent longSwitchSound;
        final SoundEvent shortSwitchSound;
        private final int particleColor;

        Type(final boolean open, final Holder<MobEffect> effect, final float effectDuration, final SoundEvent longSwitchSound, final SoundEvent shortSwitchSound, final int particleColor) {
            this.open = open;
            this.effect = effect;
            this.effectDuration = effectDuration;
            this.longSwitchSound = longSwitchSound;
            this.shortSwitchSound = shortSwitchSound;
            this.particleColor = particleColor;
        }

        public Block block() {
            return this.open ? EGBlocks.OPEN_EYEBLOSSOM : EGBlocks.CLOSED_EYEBLOSSOM;
        }

        public BlockState state() {
            return this.block().defaultBlockState();
        }

        public EyeblossomBlock.Type transform() {
            return fromBoolean(!this.open);
        }

        public boolean emitSounds() {
            return this.open;
        }

        public static EyeblossomBlock.Type fromBoolean(boolean bl) {
            return bl ? OPEN : CLOSED;
        }

        public void spawnTransformParticle(ServerLevel serverLevel, BlockPos blockPos, RandomSource randomSource) {
            Vec3 vec3 = blockPos.getCenter();
            double d = 0.5 + randomSource.nextDouble();
            Vec3 vec32 = new Vec3(randomSource.nextDouble() - 0.5, randomSource.nextDouble() + 1.0, randomSource.nextDouble() - 0.5);
            Vec3 vec33 = vec3.add(vec32.scale(d));
            TargetColorParticleOption targetColorParticleOption = new TargetColorParticleOption(vec33, this.particleColor);
            serverLevel.sendParticles(targetColorParticleOption, vec3.x, vec3.y, vec3.z, 1, 0.0, 0.0, 0.0, 0.0);
        }

        public SoundEvent longSwitchSound() {
            return this.longSwitchSound;
        }
    }
}
