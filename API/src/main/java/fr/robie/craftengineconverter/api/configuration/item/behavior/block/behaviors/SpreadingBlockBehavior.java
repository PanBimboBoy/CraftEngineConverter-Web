package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class SpreadingBlockBehavior implements BlockBehavior {
    private String targetBlock;

    public SpreadingBlockBehavior() {
    }

    public SpreadingBlockBehavior setTargetBlock(String targetBlock) {
        this.targetBlock = targetBlock;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "spreading_block");
        if (this.targetBlock != null) {
            data.put("target-block", this.targetBlock);
        }
        return data;
    }
}
