package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ShulkerBoxSpecialModel implements SpecialModel {
    private final String texture;
    private final Float openness;
    private final String orientation;

    public ShulkerBoxSpecialModel(@NotNull String texture, @Nullable Float openness, @Nullable String orientation) {
        this.texture = texture;
        this.openness = openness;
        this.orientation = orientation;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:shulker_box");
        map.put("texture", this.texture);
        if (this.openness != null) {
            map.put("openness", this.openness);
        }
        if (this.orientation != null) {
            map.put("orientation", this.orientation);
        }
        return map;
    }
}
