package io.github.thebroccolibob.shallowdark

import net.minecraft.entity.EntityType
import net.minecraft.registry.RegistryKeys
import net.minecraft.registry.tag.TagKey
import net.minecraft.util.Identifier

object ShallowDarkTags {
    val JAW_IMMUNE: TagKey<EntityType<*>> = TagKey.of(RegistryKeys.ENTITY_TYPE, Identifier(ShallowDark.modId, "jaw_immune"))
}
