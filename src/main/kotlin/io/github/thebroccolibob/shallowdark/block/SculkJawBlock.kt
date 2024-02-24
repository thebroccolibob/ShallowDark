package io.github.thebroccolibob.shallowdark.block

import io.github.thebroccolibob.shallowdark.ShallowDarkTags
import io.github.thebroccolibob.shallowdark.minus
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Blocks
import net.minecraft.block.ShapeContext
import net.minecraft.entity.Entity
import net.minecraft.entity.MovementType
import net.minecraft.particle.BlockStateParticleEffect
import net.minecraft.particle.ParticleTypes
import net.minecraft.server.world.ServerWorld
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.function.BooleanBiFunction
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Box
import net.minecraft.util.math.Direction
import net.minecraft.util.math.Vec3d
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
import net.minecraft.world.World

class SculkJawBlock(settings: Settings) : Block(settings) {
    companion object {
        val TEETH = BooleanProperty.of("teeth")
        val BITE = BooleanProperty.of("bite")

        val RAYCAST_SHAPE: VoxelShape = createCuboidShape(1.0, 0.0, 1.0, 15.0, 16.0, 15.0)

        val COLLISION_SHAPE: VoxelShape = VoxelShapes.combineAndSimplify(
            VoxelShapes.fullCube(), RAYCAST_SHAPE, BooleanBiFunction.ONLY_FIRST
        )

    }

    init {
        defaultState = stateManager.defaultState
            .with(TEETH, false)
            .with(BITE, false)
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(TEETH, BITE)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getRaycastShape(state: BlockState?, world: BlockView?, pos: BlockPos?): VoxelShape {
        return RAYCAST_SHAPE
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getCollisionShape(
        state: BlockState?,
        world: BlockView?,
        pos: BlockPos?,
        context: ShapeContext?
    ): VoxelShape {
        return COLLISION_SHAPE
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun onEntityCollision(state: BlockState, world: World, pos: BlockPos, entity: Entity) {
        if (entity.type.isIn(ShallowDarkTags.JAW_IMMUNE)) return

        if (state.get(BITE)) {
            entity.slowMovement(state, Vec3d(0.7, 0.3, 0.7))
        } else {
            entity.slowMovement(state, Vec3d(0.9, 0.7, 0.9))
        }

        if (state.get(TEETH)) return;
        world.setBlockState(pos, state.with(TEETH, true))
        world.scheduleBlockTick(pos, this, 5)
    }

    override fun onSteppedOn(world: World, pos: BlockPos, state: BlockState, entity: Entity) {
        if (!entity.type.isIn(ShallowDarkTags.JAW_IMMUNE) && entity.blockPos != pos && entity.blockY > pos.y) {
            entity.move(MovementType.SHULKER, (pos.toCenterPos() - entity.pos).withAxis(Direction.Axis.Y, 0.0).normalize().multiply(0.05))
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random) {
        val entities = world.getOtherEntities(null, Box(pos))
        if (entities.isEmpty() || entities.all { it.type.isIn(ShallowDarkTags.JAW_IMMUNE) }) {
            world.setBlockState(pos, state.with(TEETH, false).with(BITE, false))
            return
        }

        if (state.get(BITE)) {
            world.setBlockState(pos, state.with(BITE, false))
            world.scheduleBlockTick(pos, this, 10)
            return
        }

        world.setBlockState(pos, state.with(BITE, true))
        world.scheduleBlockTick(pos, this, 5)

        // TODO add custom sounds
        world.playSound(null, pos, SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.BLOCKS, 1f, (0.4 * random.nextFloat() + 0.4).toFloat())
        world.playSound(null, pos, SoundEvents.BLOCK_SCULK_STEP, SoundCategory.BLOCKS, 1f, 0.5f)
        val centerPos = pos.toCenterPos()
        world.spawnParticles(BlockStateParticleEffect(ParticleTypes.BLOCK, Blocks.SCULK.defaultState), centerPos.x, centerPos.y + 0.65, centerPos.z, 20, 0.2, 0.0, 0.2, 0.0)

        entities.forEach {
            if (!it.type.isIn(ShallowDarkTags.JAW_IMMUNE)) {
                // TODO add damage source
                it.damage(world.damageSources.generic(), 3f)
            }
        }
    }

}
