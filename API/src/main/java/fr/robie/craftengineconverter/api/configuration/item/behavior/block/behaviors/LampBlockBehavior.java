package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class LampBlockBehavior implements BlockBehavior {
    public LampBlockBehavior() {
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "lamp_block");
        return data;
    }
}
