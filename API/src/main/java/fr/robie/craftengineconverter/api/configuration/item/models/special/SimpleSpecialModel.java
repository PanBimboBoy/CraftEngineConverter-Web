package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleSpecialModel implements SpecialModel {
    private final String type;

    public SimpleSpecialModel(@NotNull String type) {
        this.type = type;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", this.type);
        return map;
    }
}
