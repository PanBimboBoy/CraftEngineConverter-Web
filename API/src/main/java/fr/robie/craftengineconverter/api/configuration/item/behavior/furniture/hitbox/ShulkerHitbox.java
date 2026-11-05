package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.hitbox;

import net.momirealms.craftengine.core.util.Direction;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class ShulkerHitbox extends CollidableHitbox {
    private float scale = 1;
    private int peek = 0;
    private Direction direction = Direction.UP;
    private boolean interactionEntity = true;

    public ShulkerHitbox() { super(true, true, true); }

    public void setScale(float scale) { this.scale = scale; }
    public void setPeek(int peek) { this.peek = peek; }
    public void setDirection(@NotNull Direction direction) { this.direction = direction; }
    public void setInteractionEntity(boolean interactionEntity) { this.interactionEntity = interactionEntity; }

    @Override protected boolean getDefaultCanUseItemOn() { return true; }
    @Override protected boolean getDefaultCanBeHitByProjectile() { return true; }
    @Override protected boolean getDefaultBlocksBuilding() { return true; }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "shulker");
        super.serialize(data);
        if (this.scale != 1) data.put("scale", this.scale);
        if (this.peek != 0) data.put("peek", this.peek);
        if (this.direction != Direction.UP) data.put("direction", this.direction.name());
        if (!this.interactionEntity) data.put("interaction-entity", false);
        return data;
    }
}
