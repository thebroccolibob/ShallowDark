package io.github.thebroccolibob.shallowdark

import net.minecraft.client.sound.MusicType
import net.minecraft.registry.Registerable
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.RegistryKey
import net.minecraft.sound.BiomeMoodSound
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.BiomeEffects
import net.minecraft.world.biome.GenerationSettings
import net.minecraft.world.biome.SpawnSettings
import net.minecraft.world.gen.feature.DefaultBiomeFeatures

class Biomes {
    val DIM_SHALLOWS = RegistryKey.of(RegistryKeys.BIOME, Identifier(ShallowDark.modId, "dim_shallows"))

    fun boostrap(context: Registerable<Biome>) {
        context.register(DIM_SHALLOWS, dimShallows(context))
    }

    fun globalOverworldGeneration(builder: GenerationSettings.LookupBackedBuilder) {
        DefaultBiomeFeatures.addLandCarvers(builder)
        DefaultBiomeFeatures.addMineables(builder)
        DefaultBiomeFeatures.addSprings(builder)
    }

    private fun dimShallows(context: Registerable<Biome>): Biome {
        val spawnBuilder = SpawnSettings.Builder()

        DefaultBiomeFeatures.addFarmAnimals(spawnBuilder)
        DefaultBiomeFeatures.addBatsAndMonsters(spawnBuilder)

        val biomeBuilder = GenerationSettings.LookupBackedBuilder(
            context.getRegistryLookup(RegistryKeys.PLACED_FEATURE),
            context.getRegistryLookup(RegistryKeys.CONFIGURED_CARVER)
        )

        globalOverworldGeneration(biomeBuilder)
        DefaultBiomeFeatures.addDefaultOres(biomeBuilder)

        return Biome.Builder()
            .precipitation(true)
            .downfall(0.4f)
            .temperature(0.8f)
            .generationSettings(biomeBuilder.build())
            .spawnSettings(spawnBuilder.build())
            .effects(
                BiomeEffects.Builder()
                    .waterColor(4159204)
                    .waterFogColor(329011)
                    .skyColor(7907327)
                    .fogColor(12638463)
                    .moodSound(BiomeMoodSound.CAVE)
                    .music(MusicType.GAME)
                    .build()
            )
            .build()
    }
}