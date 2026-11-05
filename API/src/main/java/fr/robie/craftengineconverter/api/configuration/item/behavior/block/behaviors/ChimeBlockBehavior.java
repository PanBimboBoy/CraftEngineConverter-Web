package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class ChimeBlockBehavior implements BlockBehavior {
    private String projectileHitSound;
    private String chimeSound;

    public ChimeBlockBehavior() {
    }

    public ChimeBlockBehavior setProjectileHitSound(String projectileHitSound) {
        this.projectileHitSound = projectileHitSound;
        return this;
    }

    public ChimeBlockBehavior setChimeSound(String chimeSound) {
        this.chimeSound = chimeSound;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "chime_block");
        if (this.projectileHitSound != null || this.chimeSound != null) {
            Map<String, Object> sounds = new HashMap<>();
            if (this.projectileHitSound != null) {
                sounds.put("projectile-hit", this.projectileHitSound);
            }
            if (this.chimeSound != null) {
                sounds.put("chime", this.chimeSound);
            }
            data.put("sounds", sounds);
        }
        return data;
    }
}
