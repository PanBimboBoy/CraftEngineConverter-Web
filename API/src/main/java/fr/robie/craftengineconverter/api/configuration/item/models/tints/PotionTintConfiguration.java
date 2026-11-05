package fr.robie.craftengineconverter.api.configuration.item.models.tints;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class PotionTintConfiguration implements TintConfiguration {
    private final Object defaultValue;

    public PotionTintConfiguration(@Nullable Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:potion");
        if (this.defaultValue != null) {
            map.put("default", this.defaultValue);
        }
        return map;
    }
}
