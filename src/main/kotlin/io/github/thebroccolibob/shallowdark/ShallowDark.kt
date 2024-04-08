package io.github.thebroccolibob.shallowdark

import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import io.github.thebroccolibob.shallowdark.item.ShallowDarkItems
import net.fabricmc.api.ModInitializer
import org.slf4j.LoggerFactory
import terrablender.api.TerraBlenderApi

object ShallowDark : ModInitializer, TerraBlenderApi {
	val modId = "shallowdark"
    val logger = LoggerFactory.getLogger(modId)

	override fun onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		logger.info("Hello Fabric world!")
		ShallowDarkBlocks.register()
		ShallowDarkItems.register()
	}
}
