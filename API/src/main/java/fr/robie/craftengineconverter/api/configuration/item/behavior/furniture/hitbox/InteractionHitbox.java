package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.hitbox;

import java.util.HashMap;
import java.util.Map;

public class InteractionHitbox extends CollidableHitbox {
    private float width = 1;
    private float height = 2;

    public InteractionHitbox() { super(false, false, false); }

    public void setWidth(float width) { this.width = width; }
    public void setHeight(float height) { this.height = height; }

    @Override
    protected boolean getDefaultCanUseItemOn() { return false; }
    @Override
    protected boolean getDefaultCanBeHitByProjectile() { return false; }
    @Override
    protected boolean getDefaultBlocksBuilding() { return false; }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "interaction");
        super.serialize(data);
        if (this.width != 1) data.put("width", this.width);
        if (this.height != 2) data.put("height", this.height);
        return data;
    }
}
