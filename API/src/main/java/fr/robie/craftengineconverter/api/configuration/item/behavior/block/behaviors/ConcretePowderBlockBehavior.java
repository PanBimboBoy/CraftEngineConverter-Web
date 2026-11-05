package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class ConcretePowderBlockBehavior implements BlockBehavior {
    private final String solidBlock;

    public ConcretePowderBlockBehavior(String solidBlock) {
        this.solidBlock = solidBlock;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "concrete_powder_block");
        data.put("solid-block", this.solidBlock);
        return data;
    }
}
