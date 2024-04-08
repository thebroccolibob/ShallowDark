package io.github.thebroccolibob.shallowdark

import com.mojang.datafixers.util.Pair
import net.minecraft.registry.Registry
import net.minecraft.registry.RegistryKey
import net.minecraft.util.Identifier
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.util.MultiNoiseUtil
import terrablender.api.ParameterUtils.Erosion
import terrablender.api.ParameterUtils.Continentalness
import terrablender.api.ParameterUtils.Depth
import terrablender.api.ParameterUtils.Humidity
import terrablender.api.ParameterUtils.ParameterPointListBuilder
import terrablender.api.ParameterUtils.Temperature
import terrablender.api.ParameterUtils.Weirdness
import terrablender.api.Region
import terrablender.api.RegionType
import terrablender.api.VanillaParameterOverlayBuilder
import java.util.function.Consumer

class DimShallowsRegion(name: Identifier, weight: Int) : Region(name, RegionType.OVERWORLD, weight) {
    override fun addBiomes(registry: Registry<Biome>, mapper: Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>>) {
        val builder = VanillaParameterOverlayBuilder()

        ParameterPointListBuilder()
            .temperature(Temperature.span(Temperature.COOL, Temperature.WARM))
            .humidity(Humidity.span(Humidity.DRY, Humidity.HUMID))
            .continentalness(Continentalness.INLAND)
            .erosion(Erosion.EROSION_4, Erosion.EROSION_5)
            .depth(Depth.UNDERGROUND, Depth.FLOOR)
            .weirdness(Weirdness.LOW_SLICE_NORMAL_DESCENDING, Weirdness.VALLEY)
            .build().forEach { point -> builder.add(point, Biomes().DIM_SHALLOWS)}

        builder.build().forEach(mapper::accept)
    }
}