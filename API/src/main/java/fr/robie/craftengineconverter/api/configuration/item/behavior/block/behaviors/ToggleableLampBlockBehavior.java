package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class ToggleableLampBlockBehavior implements BlockBehavior {
    private Boolean canToggleWithHand;

    public ToggleableLampBlockBehavior() {
    }

    public ToggleableLampBlockBehavior setCanToggleWithHand(boolean canToggleWithHand) {
        this.canToggleWithHand = canToggleWithHand;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "toggleable_lamp_block");
        if (this.canToggleWithHand != null) {
            data.put("can-toggle-with-hand", this.canToggleWithHand);
        }
        return data;
    }
}
