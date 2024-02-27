package io.github.thebroccolibob.shallowdark

import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks.SCULK_JAW
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks.SCULK_WART
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.block.Block
import net.minecraft.client.render.RenderLayer

object ShallowDarkClient : ClientModInitializer {
	private fun renderCutout(block: Block) {
		BlockRenderLayerMap.INSTANCE.putBlock(block, RenderLayer.getCutout())
	}

	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		renderCutout(SCULK_JAW) // Doesn't work for some reason?
		renderCutout(SCULK_WART)
	}
}
