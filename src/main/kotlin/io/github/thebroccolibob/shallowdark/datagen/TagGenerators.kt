package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.ShallowDarkTags
import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider
import net.minecraft.entity.EntityType
import net.minecraft.registry.RegistryWrapper
import net.minecraft.registry.tag.BlockTags
import net.minecraft.registry.tag.EntityTypeTags
import java.util.concurrent.CompletableFuture

class TagGenerators {
    class Blocks(output: FabricDataOutput, registriesFuture: CompletableFuture<RegistryWrapper.WrapperLookup>) :
        FabricTagProvider.BlockTagProvider(output, registriesFuture) {

        override fun configure(arg: RegistryWrapper.WrapperLookup) {
            getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).apply {
                add(ShallowDarkBlocks.SCULK_WART)
            }

        }
    }

    class Entities(
        output: FabricDataOutput?,
        completableFuture: CompletableFuture<RegistryWrapper.WrapperLookup>?
    ) : FabricTagProvider.EntityTypeTagProvider(output, completableFuture) {

        override fun configure(arg: RegistryWrapper.WrapperLookup?) {
            getOrCreateTagBuilder(ShallowDarkTags.JAW_IMMUNE).apply {
                add(EntityType.WARDEN, EntityType.ITEM)
                forceAddTag(EntityTypeTags.IMPACT_PROJECTILES)
            }
        }
    }
}
