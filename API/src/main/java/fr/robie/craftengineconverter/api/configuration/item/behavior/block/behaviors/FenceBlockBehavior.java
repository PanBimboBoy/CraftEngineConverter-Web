package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class FenceBlockBehavior implements BlockBehavior {
    private String connectableBlockTag;
    private Boolean canLeash;

    public FenceBlockBehavior() {
    }

    public FenceBlockBehavior setConnectableBlockTag(String connectableBlockTag) {
        this.connectableBlockTag = connectableBlockTag;
        return this;
    }

    public FenceBlockBehavior setCanLeash(boolean canLeash) {
        this.canLeash = canLeash;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "fence_block");
        if (this.connectableBlockTag != null) {
            data.put("connectable-block-tag", this.connectableBlockTag);
        }
        if (this.canLeash != null) {
            data.put("can-leash", this.canLeash);
        }
        return data;
    }
}
