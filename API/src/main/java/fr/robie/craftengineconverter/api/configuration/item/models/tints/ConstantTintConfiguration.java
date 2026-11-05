package fr.robie.craftengineconverter.api.configuration.item.models.tints;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class ConstantTintConfiguration implements TintConfiguration {
    private final Object value;

    public ConstantTintConfiguration(@NotNull Object value) {
        this.value = value;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:constant");
        map.put("value", this.value);
        return map;
    }
}
