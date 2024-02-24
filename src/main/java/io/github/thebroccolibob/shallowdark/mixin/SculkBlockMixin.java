package io.github.thebroccolibob.shallowdark.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SculkBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.WorldAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(SculkBlock.class)
public class SculkBlockMixin {
    @WrapOperation(
            method = "getExtraBlockState",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;", ordinal = 1)
    )
    private BlockState addJawChance(Block instance, Operation<BlockState> original, @Local(argsOnly = true) Random random) {
        return random.nextInt(4) == 0 ? ShallowDarkBlocks.SCULK_JAW.getDefaultState() : original.call(instance);
    }

    @WrapOperation(
            method = "shouldNotDecay",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isOf(Lnet/minecraft/block/Block;)Z", ordinal = 1)
    )
    private static boolean checkJaw(BlockState instance, Block block, Operation<Boolean> original) {
        return original.call(instance, block) || instance.isOf(ShallowDarkBlocks.SCULK_JAW);
    }

    @WrapOperation(
            method = "spread",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/WorldAccess;setBlockState(Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/block/BlockState;I)Z")
    )
    private boolean moveJaw(WorldAccess instance, BlockPos blockPos, BlockState blockState, int flags, Operation<Boolean> original) {
        return original.call(instance, blockState.isOf(ShallowDarkBlocks.SCULK_JAW) ? blockPos.down(1) : blockPos, blockState, flags);
    }
}
