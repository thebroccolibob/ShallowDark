package io.github.thebroccolibob.shallowdark

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.item.Item
import net.minecraft.registry.Registries
import net.minecraft.util.Identifier
import net.minecraft.util.math.Vec3d

fun FabricBlockSettings(init: FabricBlockSettings.() -> Unit): FabricBlockSettings {
    return FabricBlockSettings.create().apply(init)
}

operator fun Vec3d.minus(other: Vec3d): Vec3d = subtract(other)

val Block.registryId
    get() = Registries.BLOCK.getId(this)

val Item.registryId
    get() = Registries.ITEM.getId(this)

operator fun Identifier.plus(pathSuffix: String): Identifier = withSuffixedPath(pathSuffix)
