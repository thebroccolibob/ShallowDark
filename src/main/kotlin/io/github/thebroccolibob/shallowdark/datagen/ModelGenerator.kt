package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.block.SculkJawBlock
import io.github.thebroccolibob.shallowdark.block.SculkWartBlock
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider
import net.minecraft.block.Blocks
import net.minecraft.data.client.*

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
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {

    }
}
