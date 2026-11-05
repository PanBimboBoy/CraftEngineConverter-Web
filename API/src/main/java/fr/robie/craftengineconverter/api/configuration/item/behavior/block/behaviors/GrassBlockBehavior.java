package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class GrassBlockBehavior implements BlockBehavior {
    private String feature;

    public GrassBlockBehavior() {
    }

    public GrassBlockBehavior setFeature(String feature) {
        this.feature = feature;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "grass_block");
        if (this.feature != null) {
            data.put("feature", this.feature);
        }
        return data;
    }
}
