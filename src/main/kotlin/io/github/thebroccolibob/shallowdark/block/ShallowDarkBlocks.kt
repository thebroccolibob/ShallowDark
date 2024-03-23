package io.github.thebroccolibob.shallowdark.block

import io.github.thebroccolibob.shallowdark.FabricBlockSettings
import io.github.thebroccolibob.shallowdark.ShallowDark
import net.minecraft.block.Block
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object ShallowDarkBlocks {
    private fun register(id: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier(ShallowDark.modId, id), block)
    }

    @JvmField
    val SCULK_WART = register("sculk_wart", SculkWartBlock(FabricBlockSettings {
        sounds(BlockSoundGroup.SCULK)
        nonOpaque()
        noCollision()
        ticksRandomly()
        breakInstantly()
        luminance(7)
        emissiveLighting { _, _, _ ->  true}
    }))

    @JvmField
    val SCULK_JAW = register("sculk_jaw", SculkJawBlock(FabricBlockSettings {
        sounds(BlockSoundGroup.SCULK_SENSOR)
    }))

    @JvmField
    val SCULK_BONE_SPIKE = register("sculk_bone_spike", BoneSpikeBlock(FabricBlockSettings {
        sounds(BlockSoundGroup.BONE)
    }))

    fun register() {}


}
