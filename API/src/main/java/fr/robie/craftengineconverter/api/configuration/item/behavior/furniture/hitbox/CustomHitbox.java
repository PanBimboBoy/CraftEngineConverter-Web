package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.hitbox;

import org.bukkit.entity.EntityType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CustomHitbox extends BaseHitbox {
    private float scale = 5;
    private EntityType entityType = EntityType.SLIME;

    public void setScale(float scale) { this.scale = scale; }
    public void setEntityType(@NotNull EntityType entityType) {
        this.entityType = entityType;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "custom");
        super.serialize(data);
        if (this.scale != 5) data.put("scale", this.scale);
        if (this.entityType != EntityType.SLIME) data.put("entity-type", this.entityType);
        return data;
    }
}
