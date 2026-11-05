package fr.robie.craftengineconverter.api.configuration.item.models.special;

import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class BookSpecialModel implements SpecialModel {
    private final Integer openAngle;
    private final Float page1;
    private final Float page2;

    public BookSpecialModel(@Nullable Integer openAngle, @Nullable Float page1, @Nullable Float page2) {
        this.openAngle = openAngle;
        this.page1 = page1;
        this.page2 = page2;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> map = new LinkedHashMap<>();
        map.put("type", "minecraft:book");
        if (this.openAngle != null) {
            map.put("open-angle", this.openAngle);
        }
        if (this.page1 != null) {
            map.put("page1", this.page1);
        }
        if (this.page2 != null) {
            map.put("page2", this.page2);
        }
        return map;
    }
}
