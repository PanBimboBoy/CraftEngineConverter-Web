package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashMap;
import java.util.Map;

public class CopperGolemStatueSpecialModel implements SpecialModel {
    private final String pose;
    private final String texture;

    public CopperGolemStatueSpecialModel(@NotNull String pose, @NotNull String texture) {
        this.pose = pose;
        this.texture = texture;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:copper_golem_statue");
        map.put("pose", this.pose);
        map.put("texture", this.texture);
        return map;
    }
}
