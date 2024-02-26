package io.github.thebroccolibob.shallowdark.block

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.PlantBlock
import net.minecraft.entity.Entity
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.random.Random
import net.minecraft.world.BlockView
import net.minecraft.world.World

// All blocks need settings, so we just pass it to Block
class SculkWartBlock(settings: Settings) : PlantBlock(settings) {
    // Step 1: add block states
    companion object {
        val AGE = Properties.AGE_3!!
    }

    // Step 2 Set Default State
    init {
        defaultState = stateManager.defaultState.with(AGE, 0)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(AGE)
    }

    // Step 3 random tick growth
    // Every tick, 3 blocks are randomly selected within each sub-chunk (16x16x16)
    // Some blocks have special handling e.g. plant growth
    override fun hasRandomTicks(state: BlockState): Boolean {
        return state[AGE] < 3
    }

    // Certain minecraft functions are marked as deprecated, but it doesn't mean you shouldn't use it, it means something else
    // So to stop gradle/intellij showing warnings we have to suppress them
    @Suppress("OVERRIDE_DEPRECATION")
    override fun randomTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
       state[AGE].let {
           if (it < 3)
               world.setBlockState(pos, state.with(AGE, it + 1), 2) //Ignore the 2
       }
    }

    override fun canPlantOnTop(floor: BlockState, world: BlockView?, pos: BlockPos?): Boolean {
        return floor.isOf(Blocks.SCULK)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onEntityCollision(state: BlockState?, world: World?, pos: BlockPos?, entity: Entity?) {
        if(entity is PlayerEntity) {
            entity.addStatusEffect(StatusEffectInstance(StatusEffects.DARKNESS, 100))
        }
    }
}