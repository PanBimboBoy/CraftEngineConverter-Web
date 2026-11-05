package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class BouncingBlockBehavior implements BlockBehavior {
    private Double bounceHeight;
    private Boolean syncPlayerPosition;
    private Double fallDamageMultiplier;

    public BouncingBlockBehavior() {
    }

    public BouncingBlockBehavior setBounceHeight(double bounceHeight) {
        this.bounceHeight = bounceHeight;
        return this;
    }

    public BouncingBlockBehavior setSyncPlayerPosition(boolean syncPlayerPosition) {
        this.syncPlayerPosition = syncPlayerPosition;
        return this;
    }

    public BouncingBlockBehavior setFallDamageMultiplier(double fallDamageMultiplier) {
        this.fallDamageMultiplier = fallDamageMultiplier;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "bouncing_block");
        if (this.bounceHeight != null) {
            data.put("bounce-height", this.bounceHeight);
        }
        if (this.syncPlayerPosition != null) {
            data.put("sync-player-position", this.syncPlayerPosition);
        }
        if (this.fallDamageMultiplier != null) {
            data.put("fall-damage-multiplier", this.fallDamageMultiplier);
        }
        return data;
    }
}
