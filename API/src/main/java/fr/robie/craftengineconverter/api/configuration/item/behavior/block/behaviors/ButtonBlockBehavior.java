package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class ButtonBlockBehavior implements BlockBehavior {
    private Integer ticksToStayPressed;
    private Boolean canBeActivatedByArrows;
    private String onSound;
    private String offSound;

    public ButtonBlockBehavior() {
    }

    public ButtonBlockBehavior setTicksToStayPressed(int ticksToStayPressed) {
        this.ticksToStayPressed = ticksToStayPressed;
        return this;
    }

    public ButtonBlockBehavior setCanBeActivatedByArrows(boolean canBeActivatedByArrows) {
        this.canBeActivatedByArrows = canBeActivatedByArrows;
        return this;
    }

    public ButtonBlockBehavior setOnSound(String onSound) {
        this.onSound = onSound;
        return this;
    }

    public ButtonBlockBehavior setOffSound(String offSound) {
        this.offSound = offSound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "button_block");
        if (this.ticksToStayPressed != null) {
            data.put("ticks-to-stay-pressed", this.ticksToStayPressed);
        }
        if (this.canBeActivatedByArrows != null) {
            data.put("can-be-activated-by-arrows", this.canBeActivatedByArrows);
        }
        if (this.onSound != null || this.offSound != null) {
            Map<String, Object> sounds = new HashMap<>();
            if (this.onSound != null) {
                sounds.put("on", this.onSound);
            }
            if (this.offSound != null) {
                sounds.put("off", this.offSound);
            }
            data.put("sounds", sounds);
        }
        return data;
    }
}
