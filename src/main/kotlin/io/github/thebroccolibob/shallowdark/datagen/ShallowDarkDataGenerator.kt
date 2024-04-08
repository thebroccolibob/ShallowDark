package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.Biomes
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator
import net.minecraft.registry.RegistryBuilder
import net.minecraft.registry.RegistryKeys

object ShallowDarkDataGenerator : DataGeneratorEntrypoint {
	override fun onInitializeDataGenerator(fabricDataGenerator: FabricDataGenerator) {
		fabricDataGenerator.createPack().apply {
			addProvider(TagGenerators::Entities)
			addProvider(TagGenerators::Blocks)
			addProvider(::ModelGenerator)
			addProvider(::LootTableGenerator)
			addProvider(::EnglishLangGenerator)
			addProvider(::ModWorldGenerator)
		}
	}

	override fun buildRegistry(registryBuilder: RegistryBuilder?) {
		registryBuilder?.addRegistry(RegistryKeys.BIOME, Biomes()::boostrap)
	}
}
