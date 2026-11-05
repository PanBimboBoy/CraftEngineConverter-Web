package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class HangableBlockBehavior implements BlockBehavior {
    public HangableBlockBehavior() {
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "hangable_block");
        return data;
    }
}
