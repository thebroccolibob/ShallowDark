package io.github.thebroccolibob.shallowdark

import io.github.thebroccolibob.shallowdark.ShallowDark.modId
import net.minecraft.util.Identifier
import terrablender.api.Regions
import terrablender.api.SurfaceRuleManager
import terrablender.api.TerraBlenderApi

class ModTerrablenderAPI : TerraBlenderApi{
    override fun onTerraBlenderInitialized() {
        Regions.register(DimShallowsRegion(Identifier(modId, "overworld_1"), 4))

        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, modId, MaterialRuleData.makeRules())
    }
}