package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class StairsBlockBehavior implements BlockBehavior {
    public StairsBlockBehavior() {
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "stairs_block");
        return data;
    }
}
