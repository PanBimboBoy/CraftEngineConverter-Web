package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class BannerSpecialModel implements SpecialModel {
    private final String attachment;
    private final String color;

    public BannerSpecialModel(@Nullable String attachment, @NotNull String color) {
        this.attachment = attachment;
        this.color = color;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:banner");
        if (this.attachment != null) {
            map.put("attachment", this.attachment);
        }
        map.put("color", this.color);
        return map;
    }
}
