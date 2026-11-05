package fr.robie.craftengineconverter.api.configuration.item.models.tints;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MapColorTintConfiguration implements TintConfiguration {
    private final Object defaultValue;

    public MapColorTintConfiguration(@Nullable Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:map_color");
        if (this.defaultValue != null) {
            map.put("default", this.defaultValue);
        }
        return map;
    }
}
