package io.github.thebroccolibob.shallowdark.block

import io.github.thebroccolibob.shallowdark.FabricBlockSettings
import io.github.thebroccolibob.shallowdark.ShallowDark
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.registry.Registry
import net.minecraft.registry.Registries
import net.minecraft.block.Block
import net.minecraft.block.CropBlock
import net.minecraft.util.Identifier

object ShallowDarkBlocks {
    // ignore that we'll add more blocks
    private fun register(id: String, block: Block): Block {
        return Registry.register(Registries.BLOCK, Identifier(ShallowDark.modId, id), block)
    }

    val SCULK_WART = register("sculk_wart", SculkWartBlock(FabricBlockSettings {
        breakInstantly()
        luminance(1)
    }))

    fun register() {}


}