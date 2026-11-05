package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class ChestSpecialModel implements SpecialModel {
    private final String texture;
    private final String chestType;
    private final Float openness;

    public ChestSpecialModel(@NotNull String texture, @Nullable String chestType, @Nullable Float openness) {
        this.texture = texture;
        this.chestType = chestType;
        this.openness = openness;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:chest");
        map.put("texture", this.texture);
        if (this.chestType != null) {
            map.put("chest-type", this.chestType);
        }
        if (this.openness != null) {
            map.put("openness", this.openness);
        }
        return map;
    }
}
