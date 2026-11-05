package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class CropBlockBehavior implements BlockBehavior {
    private Double growSpeed;
    private Integer lightRequirement;
    private Boolean isBoneMealTarget;
    private Integer boneMealAgeBonusMin;
    private Integer boneMealAgeBonusMax;

    public CropBlockBehavior() {
    }

    public CropBlockBehavior setGrowSpeed(double growSpeed) {
        this.growSpeed = growSpeed;
        return this;
    }

    public CropBlockBehavior setLightRequirement(int lightRequirement) {
        this.lightRequirement = lightRequirement;
        return this;
    }

    public CropBlockBehavior setBoneMealTarget(boolean boneMealTarget) {
        this.isBoneMealTarget = boneMealTarget;
        return this;
    }

    public CropBlockBehavior setBoneMealAgeBonus(int min, int max) {
        this.boneMealAgeBonusMin = min;
        this.boneMealAgeBonusMax = max;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "crop_block");
        if (this.growSpeed != null) {
            data.put("grow-speed", this.growSpeed);
        }
        if (this.lightRequirement != null) {
            data.put("light-requirement", this.lightRequirement);
        }
        if (this.isBoneMealTarget != null) {
            data.put("is-bone-meal-target", this.isBoneMealTarget);
        }
        if (this.boneMealAgeBonusMin != null && this.boneMealAgeBonusMax != null) {
            Map<String, Object> bonus = new HashMap<>();
            bonus.put("type", "uniform");
            bonus.put("min", this.boneMealAgeBonusMin);
            bonus.put("max", this.boneMealAgeBonusMax);
            data.put("bone-meal-age-bonus", bonus);
        }
        return data;
    }
}
