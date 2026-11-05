package fr.robie.craftengineconverter.api.configuration.item.models.tints;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomModelDataTintConfiguration implements TintConfiguration {
    private final int index;
    private final Object defaultValue;

    public CustomModelDataTintConfiguration(int index, @Nullable Object defaultValue) {
        this.index = index;
        this.defaultValue = defaultValue;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:custom_model_data");
        map.put("index", this.index);
        if (this.defaultValue != null) {
            map.put("default", this.defaultValue);
        }
        return map;
    }
}
