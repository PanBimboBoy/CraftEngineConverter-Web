package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class BedSpecialModel implements SpecialModel {
    private final String part;
    private final String texture;

    public BedSpecialModel(@Nullable String part, @NotNull String texture) {
        this.part = part;
        this.texture = texture;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:bed");
        if (this.part != null) {
            map.put("part", this.part);
        }
        map.put("texture", this.texture);
        return map;
    }
}
