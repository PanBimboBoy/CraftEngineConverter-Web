package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleParticleBlockBehavior implements BlockBehavior {
    private Integer tickInterval;
    private final List<Particle> particles;

    public SimpleParticleBlockBehavior() {
        this.particles = new ArrayList<>();
    }

    public SimpleParticleBlockBehavior setTickInterval(int tickInterval) {
        this.tickInterval = tickInterval;
        return this;
    }

    public SimpleParticleBlockBehavior addParticle(Particle particle) {
        this.particles.add(particle);
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "simple_particle_block");
        if (this.tickInterval != null) {
            data.put("tick-interval", this.tickInterval);
        }
        if (!this.particles.isEmpty()) {
            List<Map<String, Object>> serializedParticles = new ArrayList<>();
            for (Particle particle : this.particles) {
                serializedParticles.add(particle.serialize());
            }
            data.put("particles", serializedParticles);
        }
        return data;
    }

    public static class Particle {
        private final String particle;
        private Double x, y, z;
        private Integer count;
        private Double offsetX, offsetY, offsetZ;
        private Double speed;
        // Specialized arguments
        private String item;
        private String blockState;
        private Double charge;
        private Integer shriek;
        private String color;
        private Double scale;
        private String from, to;
        private Double targetX, targetY, targetZ;
        private Integer arrivalTime;
        private Integer duration;

        public Particle(String particle) {
            this.particle = particle;
        }

        public Particle setPos(double x, double y, double z) {
            this.x = x;
            this.y = y;
            this.z = z;
            return this;
        }

        public Particle setCount(int count) {
            this.count = count;
            return this;
        }

        public Particle setOffset(double x, double y, double z) {
            this.offsetX = x;
            this.offsetY = y;
            this.offsetZ = z;
            return this;
        }

        public Particle setSpeed(double speed) {
            this.speed = speed;
            return this;
        }

        public Particle setItem(String item) {
            this.item = item;
            return this;
        }

        public Particle setBlockState(String blockState) {
            this.blockState = blockState;
            return this;
        }

        public Particle setCharge(double charge) {
            this.charge = charge;
            return this;
        }

        public Particle setShriek(int shriek) {
            this.shriek = shriek;
            return this;
        }

        public Particle setColor(String color) {
            this.color = color;
            return this;
        }

        public Particle setScale(double scale) {
            this.scale = scale;
            return this;
        }

        public Particle setTransition(String from, String to) {
            this.from = from;
            this.to = to;
            return this;
        }

        public Particle setTarget(double x, double y, double z) {
            this.targetX = x;
            this.targetY = y;
            this.targetZ = z;
            return this;
        }

        public Particle setArrivalTime(int arrivalTime) {
            this.arrivalTime = arrivalTime;
            return this;
        }

        public Particle setDuration(int duration) {
            this.duration = duration;
            return this;
        }

        public Map<String, Object> serialize() {
            Map<String, Object> data = new HashMap<>();
            data.put("particle", this.particle);
            if (this.x != null) data.put("x", this.x);
            if (this.y != null) data.put("y", this.y);
            if (this.z != null) data.put("z", this.z);
            if (this.count != null) data.put("count", this.count);
            if (this.offsetX != null) data.put("offset-x", this.offsetX);
            if (this.offsetY != null) data.put("offset-y", this.offsetY);
            if (this.offsetZ != null) data.put("offset-z", this.offsetZ);
            if (this.speed != null) data.put("speed", this.speed);
            if (this.item != null) data.put("item", this.item);
            if (this.blockState != null) data.put("block-state", this.blockState);
            if (this.charge != null) data.put("charge", this.charge);
            if (this.shriek != null) data.put("shriek", this.shriek);
            if (this.color != null) data.put("color", this.color);
            if (this.scale != null) data.put("scale", this.scale);
            if (this.from != null) data.put("from", this.from);
            if (this.to != null) data.put("to", this.to);
            if (this.targetX != null) data.put("target-x", this.targetX);
            if (this.targetY != null) data.put("target-y", this.targetY);
            if (this.targetZ != null) data.put("target-z", this.targetZ);
            if (this.arrivalTime != null) data.put("arrival-time", this.arrivalTime);
            if (this.duration != null) data.put("duration", this.duration);
            return data;
        }
    }
}
