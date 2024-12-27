package net.wetnoodle.eepygarden.mixin;

import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.CreakingHeartBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.wetnoodle.eepygarden.block.ResinClumpBlock;
import net.wetnoodle.eepygarden.registry.EGBlocks;
import net.wetnoodle.eepygarden.registry.EGSounds;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.commons.lang3.tuple.Pair;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayDeque;
import java.util.Optional;
import java.util.Queue;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

@Mixin(CreakingHeartBlockEntity.class)
public abstract class CreakingHeartBlockEntityMixin extends BlockEntity {

    @Shadow
    private int emitter;

    public CreakingHeartBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Definition(id = "ServerLevel", type = ServerLevel.class)
    @Expression("? instanceof ServerLevel")
    @ModifyExpressionValue(method = "creakingHurt", at = @At("MIXINEXTRAS:EXPRESSION"))
    private boolean eepyGarden$instanceOf(boolean isServerLevel) {
        return (isServerLevel && emitter <= 0);
    }

    @Inject(method = "creakingHurt", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/entity/CreakingHeartBlockEntity;emitParticles(Lnet/minecraft/server/level/ServerLevel;IZ)V"))
    private void eepyGarden$spreadResin(CallbackInfo ci) {
        int i = level.getRandom().nextIntBetweenInclusive(2, 3);

        for (int j = 0; j < i; j++) {
            this.spreadResin().ifPresent(blockPos -> {
                level.playSound(null, blockPos, EGSounds.RESIN_PLACE, SoundSource.BLOCKS, 1.0F, 1.0F);
                level.gameEvent(GameEvent.BLOCK_PLACE, blockPos, GameEvent.Context.of(level.getBlockState(blockPos)));
            });
        }
    }

    @Unique
    private Optional<BlockPos> spreadResin() {
        Mutable<BlockPos> mutable = new MutableObject<>(null);
        breadthFirstTraversal(worldPosition, 2, 64,
                (blockPos, consumer) -> {
                    for (Direction direction : Util.shuffledCopy(Direction.values(), level.random)) {
                        BlockPos blockPos2 = blockPos.relative(direction);
                        if (level.getBlockState(blockPos2).is(BlockTags.PALE_OAK_LOGS)) {
                            consumer.accept(blockPos2);
                        }
                    }
                },
                (blockPos) -> {
                    if (!level.getBlockState(blockPos).is(BlockTags.PALE_OAK_LOGS)) {
                        return true;
                    } else {
                        for (Direction direction : Util.shuffledCopy(Direction.values(), level.random)) {
                            BlockPos blockPos2 = blockPos.relative(direction);
                            BlockState blockState = level.getBlockState(blockPos2);
                            Direction direction2 = direction.getOpposite();
                            if (blockState.isAir()) {
                                blockState = EGBlocks.RESIN_CLUMP.defaultBlockState();
                            } else if (blockState.is(Blocks.WATER) && blockState.getFluidState().isSource()) {
                                blockState = EGBlocks.RESIN_CLUMP.defaultBlockState().setValue(ResinClumpBlock.WATERLOGGED, true);
                            }

                            if (blockState.is(EGBlocks.RESIN_CLUMP) && !ResinClumpBlock.hasFace(blockState, direction2)) {
                                level.setBlock(blockPos2, blockState.setValue(ResinClumpBlock.getFaceProperty(direction2), true), 3);
                                mutable.setValue(blockPos2);
                                return false;
                            }
                        }

                        return true;
                    }
                });
        return Optional.ofNullable(mutable.getValue());
    }


    /**
     * This is modified jank stolen from 1.21.4
     * true = BlockPos.TraversalNodeStatus.ACCEPT
     * false = BlockPos.TraversalNodeStatus.STOP
     *
     * @return
     */
    @Unique
    private static int breadthFirstTraversal(
            BlockPos blockPos, int i, int j, BiConsumer<BlockPos, Consumer<BlockPos>> biConsumer, Predicate<BlockPos> predicate
    ) {
        Queue<Pair<BlockPos, Integer>> queue = new ArrayDeque<>();
        LongSet longSet = new LongOpenHashSet();
        queue.add(Pair.of(blockPos, 0));
        int k = 0;

        while (!queue.isEmpty()) {
            Pair<BlockPos, Integer> pair = queue.poll();
            BlockPos blockPos2 = pair.getLeft();
            int l = pair.getRight();
            long m = blockPos2.asLong();
            if (longSet.add(m)) {
                boolean traversalNodeStatus = predicate.test(blockPos2);
                if (!traversalNodeStatus) {
                    break;
                }

                if (++k >= j) {
                    return k;
                }

                if (l < i) {
                    biConsumer.accept(blockPos2, blockPosx -> queue.add(Pair.of(blockPosx, l + 1)));
                }

            }
        }

        return k;
    }
}