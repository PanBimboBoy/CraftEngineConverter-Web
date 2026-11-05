package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WallTorchParticleBlockBehavior implements BlockBehavior {
    private Integer tickInterval;
    private final List<SimpleParticleBlockBehavior.Particle> particles;

    public WallTorchParticleBlockBehavior() {
        this.particles = new ArrayList<>();
    }

    public WallTorchParticleBlockBehavior setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
        return this;
    }

    public WallTorchParticleBlockBehavior addParticle(SimpleParticleBlockBehavior.Particle particle) {
        this.particles.add(particle);
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "wall_torch_particle_block");
        if (this.tickInterval != null) {
            data.put("tick-interval", this.tickInterval);
        }
        if (!this.particles.isEmpty()) {
            List<Map<String, Object>> serializedParticles = new ArrayList<>();
            for (SimpleParticleBlockBehavior.Particle particle : this.particles) {
                serializedParticles.add(particle.serialize());
            }
            data.put("particles", serializedParticles);
        }
        return data;
    }
}
