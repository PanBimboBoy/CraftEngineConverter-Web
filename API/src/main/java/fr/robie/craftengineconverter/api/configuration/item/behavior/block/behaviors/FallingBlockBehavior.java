package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class FallingBlockBehavior implements BlockBehavior {
    private Integer hurtAmount;
    private Integer maxHurt;
    //Sounds
    private String landSound;
    private String destroySound;

    public FallingBlockBehavior() {
    }

    public FallingBlockBehavior setHurtAmount(int hurtAmount) {
        this.hurtAmount = hurtAmount;
        return this;
    }

    public FallingBlockBehavior setMaxHurt(int maxHurt) {
        this.maxHurt = maxHurt;
        return this;
    }

    public FallingBlockBehavior setLandSound(String landSound) {
        this.landSound = landSound;
        return this;
    }

    public FallingBlockBehavior setDestroySound(String destroySound) {
        this.destroySound = destroySound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "falling_block");
        if (this.hurtAmount != null) {
            data.put("hurt-amount", this.hurtAmount);
        }
        if (this.maxHurt != null) {
            data.put("max-hurt", this.maxHurt);
        }
        if (this.landSound != null || this.destroySound != null) {
            Map<String, Object> soundsData = new HashMap<>();
            if (this.landSound != null) {
                soundsData.put("land", this.landSound);
            }
            if (this.destroySound != null) {
                soundsData.put("destroy", this.destroySound);
            }
            data.put("sounds", soundsData);
        }
        return data;
    }
}
