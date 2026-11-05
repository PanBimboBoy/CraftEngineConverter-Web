package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class HeadSpecialModel implements SpecialModel {
    private final String kind;
    private final String texture;
    private final Float animation;

    public HeadSpecialModel(@NotNull String kind, @Nullable String texture, @Nullable Float animation) {
        this.kind = kind;
        this.texture = texture;
        this.animation = animation;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:head");
        map.put("kind", this.kind);
        if (this.texture != null) {
            map.put("texture", this.texture);
        }
        if (this.animation != null) {
            map.put("animation", this.animation);
        }
        return map;
    }
}
