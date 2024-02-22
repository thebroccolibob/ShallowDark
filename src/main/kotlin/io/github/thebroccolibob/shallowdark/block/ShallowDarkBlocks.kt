package io.github.thebroccolibob.shallowdark.block

import io.github.thebroccolibob.shallowdark.FabricBlockSettings
import io.github.thebroccolibob.shallowdark.ShallowDark
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.Identifier

object ShallowDarkBlocks {
    private fun register(id: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier(ShallowDark.modId, id), block)
    }

    val SCULK_WART = register("sculk_wart", SculkWartBlock(FabricBlockSettings {
        sounds(BlockSoundGroup.SCULK)
        noCollision()
        ticksRandomly()
        breakInstantly()
        luminance(7)
    }))

    fun register() {}


}