package io.github.thebroccolibob.shallowdark.block

import net.minecraft.block.*
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.World

class SculkWartBlock(settings: Settings) : PlantBlock(settings) {
    // Step 1: Add Block State
    companion object {
        val AGE = Properties.AGE_3!!
        val AGE_TO_SHAPE = arrayOf(
            createCuboidShape(1.0, 0.0 , 1.0, 15.0, 7.0, 15.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 11.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0),
            createCuboidShape(0.0, 0.0, 0.0, 16.0, 14.0, 16.0)
        )
    }

    // Step 2: Set Default State
    init {
        defaultState = stateManager.defaultState.with(AGE, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    override fun hasRandomTicks(state: BlockState): Boolean {
        return state[AGE] < 3
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext?
    ): VoxelShape {
        return AGE_TO_SHAPE[state[AGE]]
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
       state[AGE].let {
           if (it < 3)
               world.setBlockState(pos, state.with(AGE, it + 1), 2) //Ignore the 2
       }
    }

    override fun canPlantOnTop(floor: BlockState, world: BlockView?, pos: BlockPos?): Boolean {
        return floor.isOf(Blocks.SCULK) || floor.isOf(Blocks.SCULK_CATALYST)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onEntityCollision(state: BlockState?, world: World?, pos: BlockPos?, entity: Entity?) {
        if(entity is PlayerEntity) {
            entity.addStatusEffect(StatusEffectInstance(StatusEffects.DARKNESS, 100, 0, false, false))
        }
    }
}
