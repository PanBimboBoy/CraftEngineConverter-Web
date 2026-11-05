package fr.robie.craftengineconverter.api.configuration.item.models.tints;

import java.util.LinkedHashMap;
import java.util.Map;

public class GrassTintConfiguration implements TintConfiguration {
    private final float temperature;
    private final float downfall;

    public GrassTintConfiguration(float temperature, float downfall) {
        this.temperature = temperature;
        this.downfall = downfall;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:grass");
        map.put("temperature", this.temperature);
        map.put("downfall", this.downfall);
        return map;
    }
}
