package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class MultiHighBlockBehavior implements BlockBehavior {
    private String property;

    public MultiHighBlockBehavior() {
    }

    public MultiHighBlockBehavior setProperty(String property) {
        this.property = property;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "multi_high_block");
        if (this.property != null) {
            data.put("property", this.property);
        }
        return data;
    }
}
