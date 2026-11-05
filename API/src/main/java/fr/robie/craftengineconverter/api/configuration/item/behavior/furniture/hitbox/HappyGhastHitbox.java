package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.hitbox;

import java.util.HashMap;
import java.util.Map;

public class HappyGhastHitbox extends CollidableHitbox {
    private boolean hardCollision = true;
    private float scale = 0.25f;

    public HappyGhastHitbox() { super(true, true, true); }

    public void setHardCollision(boolean hardCollision) { this.hardCollision = hardCollision; }
    public void setScale(float scale) { this.scale = scale; }

    @Override protected boolean getDefaultCanUseItemOn() { return true; }
    @Override protected boolean getDefaultCanBeHitByProjectile() { return true; }
    @Override protected boolean getDefaultBlocksBuilding() { return true; }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "happy_ghast");
        super.serialize(data);
        if (!this.hardCollision) data.put("hard-collision", false);
        if (this.scale != 0.25f) data.put("scale", this.scale);
        return data;
    }
}
