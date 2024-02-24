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
            fun upload(suffix: String) = Models.CUBE_TOP.upload(
                ModelIds.getBlockSubModelId(ShallowDarkBlocks.SCULK_JAW, suffix),
                TextureMap().apply {
                    put(TextureKey.SIDE, TextureMap.getId(Blocks.SCULK))
                    put(TextureKey.TOP, TextureMap.getSubId(ShallowDarkBlocks.SCULK_JAW, "_top$suffix"))
                },
                blockStateModelGenerator.modelCollector
            )

            val DEFAULT = upload("")
            val TEETH = upload("_teeth")
            val BITE = upload("_bite")
        }

        blockStateModelGenerator.registerCrop(ShallowDarkBlocks.SCULK_WART, SculkWartBlock.AGE, 0, 1, 2, 3)

        blockStateModelGenerator.blockStateCollector.accept(
            VariantsBlockStateSupplier.create(ShallowDarkBlocks.SCULK_JAW).coordinate(
                BlockStateVariantMap.create(SculkJawBlock.TEETH, SculkJawBlock.BITE).register { teeth, bite ->
                    val model = when (true) {
                        bite -> sculkJawModels.BITE
                        teeth -> sculkJawModels.TEETH
                        else -> sculkJawModels.DEFAULT
                    }

                    BlockStateVariant.create().put(VariantSettings.MODEL, model)
                }
            )
        )
    }

    override fun generateItemModels(itemModelGenerator: ItemModelGenerator?) {

    }
}
