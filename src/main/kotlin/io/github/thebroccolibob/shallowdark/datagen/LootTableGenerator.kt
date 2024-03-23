package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.block.SculkWartBlock
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import io.github.thebroccolibob.shallowdark.item.ShallowDarkItems
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.loot.FabricBlockLootTableGenerator
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider
import net.minecraft.data.server.loottable.BlockLootTableGenerator
import net.minecraft.item.Item
import net.minecraft.loot.LootPool
import net.minecraft.loot.LootTable
import net.minecraft.loot.condition.BlockStatePropertyLootCondition
import net.minecraft.loot.entry.AlternativeEntry
import net.minecraft.loot.entry.ItemEntry
import net.minecraft.loot.function.SetCountLootFunction
import net.minecraft.loot.provider.number.ConstantLootNumberProvider
import net.minecraft.loot.provider.number.UniformLootNumberProvider
import net.minecraft.predicate.StatePredicate
import net.minecraft.resource.featuretoggle.FeatureSet

class LootTableGenerator(dataOutput: FabricDataOutput) :
    FabricBlockLootTableProvider(dataOutput) {
    override fun generate() {
        addDrop(ShallowDarkBlocks.SCULK_WART, LootTable.Builder().pool(
            LootPool.builder().rolls(ConstantLootNumberProvider.create(1f)).with(
                AlternativeEntry.builder(
                    ItemEntry.builder(ShallowDarkItems.SCULK_WART)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2f, 4f)))
                        .conditionally(BlockStatePropertyLootCondition.builder(ShallowDarkBlocks.SCULK_WART).properties(
                            StatePredicate.Builder.create()
                                .exactMatch(SculkWartBlock.AGE, 3)
                        )),
                    ItemEntry.builder(ShallowDarkItems.SCULK_WART)
                        .apply(SetCountLootFunction.builder(ConstantLootNumberProvider.create(1f)))
                )
            )
        ))
    }
}