package io.github.thebroccolibob.shallowdark.datagen

import io.github.thebroccolibob.shallowdark.block.ShallowDarkBlocks
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider

class EnglishLangGenerator(dataOutput: FabricDataOutput) : FabricLanguageProvider(dataOutput) {
    override fun generateTranslations(translationBuilder: TranslationBuilder) {
        translationBuilder.apply {
            add(ShallowDarkBlocks.SCULK_WART, "Sculk Wart")
            add(ShallowDarkBlocks.SCULK_JAW, "Sculk Jaw")
            add(ShallowDarkBlocks.SCULK_BONE_SPIKE, "Sculk Bone Spike")
        }
    }
}