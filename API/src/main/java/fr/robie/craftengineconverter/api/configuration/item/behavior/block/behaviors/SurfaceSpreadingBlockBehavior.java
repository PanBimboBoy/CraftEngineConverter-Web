package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class SurfaceSpreadingBlockBehavior implements BlockBehavior {
    private Integer requiredLight;
    private String baseBlock;

    public SurfaceSpreadingBlockBehavior() {
    }

    public SurfaceSpreadingBlockBehavior setRequiredLight(int requiredLight) {
        this.requiredLight = requiredLight;
        return this;
    }

    public SurfaceSpreadingBlockBehavior setBaseBlock(String baseBlock) {
        this.baseBlock = baseBlock;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "surface_spreading_block");
        if (this.requiredLight != null) {
            data.put("required-light", this.requiredLight);
        }
        if (this.baseBlock != null) {
            data.put("base-block", this.baseBlock);
        }
        return data;
    }
}
