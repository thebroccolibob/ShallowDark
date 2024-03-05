package io.github.thebroccolibob.shallowdark.item

import io.github.thebroccolibob.shallowdark.ShallowDark
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemGroups
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.util.Identifier

object ShallowDarkItems {
    // Creates Item from Block
    private fun register(block: Block, settings: FabricItemSettings = FabricItemSettings()): Item {
        return Registry.register(Registries.ITEM, Registries.BLOCK.getId(block), BlockItem(block, settings))
    }

    // Creates Item
    private fun register(id: String, item: Item): Item {
        return Registry.register(Registries.ITEM, Identifier(ShallowDark.modId, id), item)
    }

    val SCULK_WART = register(ShallowDarkBlocks.SCULK_WART)
    val SCULK_JAW = register(ShallowDarkBlocks.SCULK_JAW)
    val SCULK_BONE_SPIKE = register(ShallowDarkBlocks.SCULK_BONE_SPIKE)

    fun register() {
        // Add to Creative Inventory
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.NATURAL).register {
            it.add(SCULK_WART)
            it.add(SCULK_JAW)
            it.add(SCULK_BONE_SPIKE)
        }
    }
}
