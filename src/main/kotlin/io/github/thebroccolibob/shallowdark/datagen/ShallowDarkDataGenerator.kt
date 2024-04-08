package io.github.thebroccolibob.shallowdark.datagen

import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator

object ShallowDarkDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.createPack().apply {
			addProvider(TagGenerators::Entities)
			addProvider(TagGenerators::Blocks)
			addProvider(::ModelGenerator)
			addProvider(::LootTableGenerator)
			addProvider(::EnglishLangGenerator)
		}


	}
}
