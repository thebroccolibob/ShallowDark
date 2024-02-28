package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.block.BoneSpikeBlock
import io.github.thebroccolibob.shallowdark.block.SculkJawBlock
import io.github.thebroccolibob.shallowdark.block.SculkWartBlock
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import io.github.thebroccolibob.shallowdark.item.ShallowDarkItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Blocks
import net.minecraft.data.client.*
import net.minecraft.util.Identifier
import net.minecraft.util.math.Direction

class ModelGenerator(output: FabricDataOutput) : FabricModelProvider(output) {

    override fun generateBlockStateModels(blockStateModelGenerator: BlockStateModelGenerator) {
        val sculkJawModels = object {
            private fun upload(suffix: String) = blockStateModelGenerator.upload(Models.CUBE_BOTTOM_TOP, ShallowDarkBlocks.SCULK_JAW.modelId(suffix)) {
                TextureKey.SIDE to Blocks.SCULK.textureId
                TextureKey.TOP to ShallowDarkBlocks.SCULK_JAW.textureId("_top$suffix")
                TextureKey.BOTTOM to ShallowDarkBlocks.SCULK_JAW.textureId("_top")
            }

            val DEFAULT = upload("")
            val TEETH = upload("_teeth")
            val BITE = upload("_bite")
        }

        blockStateModelGenerator.registerCrop(ShallowDarkBlocks.SCULK_WART, SculkWartBlock.AGE, 0, 1, 2, 3)

        blockStateModelGenerator.registerVariantStates(ShallowDarkBlocks.SCULK_JAW, SculkJawBlock.TEETH, SculkJawBlock.BITE) { teeth, bite ->
            val model = when {
                bite -> sculkJawModels.BITE
                teeth -> sculkJawModels.TEETH
                else -> sculkJawModels.DEFAULT
            }

            listOf(
                BlockStateVariant.create().put(VariantSettings.MODEL, model),
                BlockStateVariant.create()
                    .put(VariantSettings.MODEL, model)
                    .put(VariantSettings.Y, VariantSettings.Rotation.R90)
            )
        }

        val sculkBoneSpikeModels = object {
            private fun upload(suffix: String): Identifier {
                return blockStateModelGenerator.upload(Models.CROSS, ShallowDarkBlocks.SCULK_BONE_SPIKE.modelId(suffix)) {
                    TextureKey.CROSS to ShallowDarkBlocks.SCULK_BONE_SPIKE.textureId(suffix)
                }
            }

            val BASE = upload("_base")
            val MIDDLE = upload("_middle")
            val TIP = upload("_tip")
        }

        blockStateModelGenerator.registerStates(ShallowDarkBlocks.SCULK_BONE_SPIKE, BoneSpikeBlock.THICKNESS, BoneSpikeBlock.FACING) { thickness, facing ->
            BlockStateVariant.create()
                .put(VariantSettings.MODEL, when (thickness!!) {
                    BoneSpikeBlock.Thickness.BASE -> sculkBoneSpikeModels.BASE
                    BoneSpikeBlock.Thickness.MIDDLE -> sculkBoneSpikeModels.MIDDLE
                    BoneSpikeBlock.Thickness.TIP -> sculkBoneSpikeModels.TIP
                })
                .put(VariantSettings.Y, when (facing!!) {
                    Direction.UP, Direction.DOWN, Direction.NORTH -> VariantSettings.Rotation.R0
                    Direction.EAST -> VariantSettings.Rotation.R90
                    Direction.SOUTH -> VariantSettings.Rotation.R180
                    Direction.WEST -> VariantSettings.Rotation.R270
                })
                .put(VariantSettings.X, when (facing) {
                    Direction.UP -> VariantSettings.Rotation.R0
                    Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST -> VariantSettings.Rotation.R90
                    Direction.DOWN -> VariantSettings.Rotation.R180
                })
        }
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator) {
        itemModelGenerator.register(ShallowDarkItems.SCULK_BONE_SPIKE, Models.GENERATED)
    }
}
