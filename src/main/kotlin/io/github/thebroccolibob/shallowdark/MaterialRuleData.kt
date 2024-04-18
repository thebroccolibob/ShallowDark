package io.github.thebroccolibob.shallowdark

import net.minecraft.block.Block
import net.minecraft.block.Blocks
import net.minecraft.world.gen.surfacebuilder.MaterialRules
import net.minecraft.world.gen.surfacebuilder.MaterialRules.block

object MaterialRuleData {
    fun block(block: Block): MaterialRules.MaterialRule {
        return block(block.defaultState)
    }

    val DIRT: MaterialRules.MaterialRule = block(Blocks.DIRT)
    val GRASS_BLOCK: MaterialRules.MaterialRule = block(Blocks.GRASS_BLOCK)
    val SCULK: MaterialRules.MaterialRule = block(Blocks.SCULK)

    fun makeRules(): MaterialRules.MaterialRule {
        val isAtOrAboveWaterLevel: MaterialRules.MaterialCondition = MaterialRules.water(-1, 0)
        val grassSurface: MaterialRules.MaterialRule = MaterialRules.sequence(
            MaterialRules.condition(isAtOrAboveWaterLevel, GRASS_BLOCK), DIRT
        )

        return MaterialRules.sequence(
            MaterialRules.condition(
                MaterialRules.biome(Biomes().DIM_SHALLOWS),
                MaterialRules.condition(MaterialRules.verticalGradient("dim", { 0 }, { 30 }), SCULK)
            ),

            // Game defaults to a grass and dirt surface
            MaterialRules.condition(MaterialRules.STONE_DEPTH_FLOOR, grassSurface)
        )
    }
}