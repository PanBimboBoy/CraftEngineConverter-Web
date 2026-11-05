package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class PressurePlateBlockBehavior implements BlockBehavior {
    private String sensitivity;
    private Integer pressedTime;
    private String onSound;
    private String offSound;

    public PressurePlateBlockBehavior() {
    }

    public PressurePlateBlockBehavior setSensitivity(String sensitivity) {
        this.sensitivity = sensitivity;
        return this;
    }

    public PressurePlateBlockBehavior setPressedTime(int pressedTime) {
        this.pressedTime = pressedTime;
        return this;
    }

    public PressurePlateBlockBehavior setOnSound(String onSound) {
        this.onSound = onSound;
        return this;
    }

    public PressurePlateBlockBehavior setOffSound(String offSound) {
        this.offSound = offSound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "pressure_plate_block");
        if (this.sensitivity != null) {
            data.put("sensitivity", this.sensitivity);
        }
        if (this.pressedTime != null) {
            data.put("pressed-time", this.pressedTime);
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
