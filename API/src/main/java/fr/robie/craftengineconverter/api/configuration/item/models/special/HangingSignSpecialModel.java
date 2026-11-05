package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class HangingSignSpecialModel implements SpecialModel {
    private final String attachment;
    private final String woodType;
    private final String texture;

    public HangingSignSpecialModel(@Nullable String attachment, @Nullable String woodType, @Nullable String texture) {
        this.attachment = attachment;
        this.woodType = woodType;
        this.texture = texture;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:hanging_sign");
        if (this.attachment != null) {
            map.put("attachment", this.attachment);
        }
        if (this.woodType != null) {
            map.put("wood-type", this.woodType);
        }
        if (this.texture != null) {
            map.put("texture", this.texture);
        }
        return map;
    }
}
