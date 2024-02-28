package io.github.thebroccolibob.shallowdark.block

import net.minecraft.block.*
import net.minecraft.fluid.Fluids
import net.minecraft.item.ItemPlacementContext
import net.minecraft.server.world.ServerWorld
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.state.property.DirectionProperty
import net.minecraft.state.property.EnumProperty
import net.minecraft.state.property.Properties
import net.minecraft.util.StringIdentifiable
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.math.random.Random
import net.minecraft.util.shape.VoxelShape
import net.minecraft.world.BlockView
import net.minecraft.world.WorldAccess
import net.minecraft.world.WorldView

class BoneSpikeBlock(settings: Settings) : Block(settings), Waterloggable {
    enum class Thickness(private val stringName: String): StringIdentifiable {
        TIP("tip"),
        MIDDLE("middle"),
        BASE("base");

        override fun asString(): String {
            return stringName
        }
    }

    companion object {
        val THICKNESS: EnumProperty<Thickness> = EnumProperty.of("thickness", Thickness::class.java)
        val FACING: DirectionProperty = Properties.FACING
        val WATERLOGGED: BooleanProperty = Properties.WATERLOGGED

        val SHAPES = mapOf(
            Thickness.TIP to shapeRotations(6.0, 0.0, 6.0, 10.0, 10.0, 10.0),
            Thickness.MIDDLE to shapeRotations(5.0, 0.0, 5.0, 11.0, 16.0, 11.0),
            Thickness.BASE to shapeRotations(4.0, 0.0, 4.0, 12.0, 16.0, 12.0)
        )

        private fun shapeRotations(minX: Double, minY: Double, minZ: Double, maxX: Double, maxY: Double, maxZ: Double): Map<Direction, VoxelShape> {
            return mapOf(
                Direction.UP to createCuboidShape(minX, minY, minZ, maxX, maxY, maxZ),
                Direction.DOWN to createCuboidShape(minX, 16-maxY, 16-maxZ, maxX, 16-minY, 16-minZ),
                Direction.NORTH to createCuboidShape(minX, minZ, 16-maxY, maxX, maxZ, 16-minY),
                Direction.SOUTH to createCuboidShape(16-maxX, minZ, minY, 16-minX, maxZ, maxY),
                Direction.EAST to createCuboidShape(minY, minZ, minX, maxY, maxZ, maxX),
                Direction.WEST to createCuboidShape(16-maxY, minZ, 16-maxX, 16-minY, maxZ, 16-minX),
            )
        }
    }

    override fun appendProperties(builder: StateManager.Builder<Block, BlockState>) {
        builder.add(THICKNESS, FACING, WATERLOGGED)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun canPlaceAt(state: BlockState, world: WorldView, pos: BlockPos): Boolean {
        val facing = state[FACING]
        val basePos = pos.offset(facing.opposite)
        val baseState = world.getBlockState(basePos)
        return baseState.isSideSolid(world, basePos, facing, SideShapeType.CENTER) ||
                (baseState.isOf(this) && baseState[FACING] == facing && !BlockPos.iterate(basePos, basePos.offset(facing.opposite, 2)).all { world.getBlockState(it).let { it.isOf(this) && it[FACING] == facing }})
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState? {
        return defaultState
            .with(FACING, ctx.side)
            .with(THICKNESS, Thickness.TIP)
            .with(WATERLOGGED, ctx.world.getFluidState(ctx.blockPos).fluid === Fluids.WATER)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getStateForNeighborUpdate(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        world: WorldAccess,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        world.getBlockState(pos.offset(state[FACING].opposite)).let {
            if (!canPlaceAt(state, world, pos)) {
                world.scheduleBlockTick(pos, state.block, 1)
                return state
            }
        }

        world.getBlockState(pos.offset(state[FACING])).let {
            if (!it.isOf(this) || it[FACING] != state[FACING]) return state.with(THICKNESS, Thickness.TIP)

            return when (it[THICKNESS]) {
                Thickness.TIP -> state.with(THICKNESS, Thickness.MIDDLE)
                Thickness.MIDDLE -> state.with(THICKNESS, Thickness.BASE)
                else -> state
            }
        }
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun scheduledTick(state: BlockState, world: ServerWorld, pos: BlockPos, random: Random?) {
        world.breakBlock(pos, true)
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun isShapeFullCube(state: BlockState?, world: BlockView?, pos: BlockPos?): Boolean {
        return false
    }

    @Suppress("OVERRIDE_DEPRECATION")
    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return SHAPES[state[THICKNESS]]!![state[FACING]]!!
    }
}
