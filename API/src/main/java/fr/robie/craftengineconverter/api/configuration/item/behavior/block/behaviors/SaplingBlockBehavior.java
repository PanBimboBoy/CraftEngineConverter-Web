package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class SaplingBlockBehavior implements BlockBehavior {
    private String feature;
    private Double boneMealSuccessChance;
    private Double growSpeed;

    public SaplingBlockBehavior() {
    }

    public SaplingBlockBehavior setFeature(String feature) {
        this.feature = feature;
        return this;
    }

    public SaplingBlockBehavior setBoneMealSuccessChance(double boneMealSuccessChance) {
        this.boneMealSuccessChance = boneMealSuccessChance;
        return this;
    }

    public SaplingBlockBehavior setGrowSpeed(double growSpeed) {
        this.growSpeed = growSpeed;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "sapling_block");
        if (this.feature != null) {
            data.put("feature", this.feature);
        }
        if (this.boneMealSuccessChance != null) {
            data.put("bone-meal-success-chance", this.boneMealSuccessChance);
        }
        if (this.growSpeed != null) {
            data.put("grow-speed", this.growSpeed);
        }
        return data;
    }
}
