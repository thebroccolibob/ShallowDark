package io.github.thebroccolibob.shallowdark

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings

fun FabricBlockSettings(init: FabricBlockSettings.() -> Unit): FabricBlockSettings {
    return FabricBlockSettings.create().apply(init)
}