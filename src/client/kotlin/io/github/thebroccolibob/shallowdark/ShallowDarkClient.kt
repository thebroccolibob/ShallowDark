package io.github.thebroccolibob.shallowdark

import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import net.fabricmc.api.ClientModInitializer
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.minecraft.client.render.RenderLayer

object ShallowDarkClient : ClientModInitializer {
	override fun onInitializeClient() {
		// This entrypoint is suitable for setting up client-specific logic, such as rendering.
		BlockRenderLayerMap.INSTANCE.putBlock(ShallowDarkBlocks.SCULK_JAW, RenderLayer.getCutout());
	}
}
