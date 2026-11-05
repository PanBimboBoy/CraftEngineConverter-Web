package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class LiquidFlowableBlockBehavior implements BlockBehavior {
    public LiquidFlowableBlockBehavior() {
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "liquid_flowable_block");
        return data;
    }
}
