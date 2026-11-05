package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class LeavesBlockBehavior implements BlockBehavior {
    private final int distance;
    private final boolean persistent;
    private final boolean waterlogged;

    public LeavesBlockBehavior(int distance, boolean persistent, boolean waterlogged) {
        this.distance = distance;
        this.persistent = persistent;
        this.waterlogged = waterlogged;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "leaves_block");
        data.put("distance", this.distance);
        data.put("persistent", this.persistent);
        data.put("waterlogged", this.waterlogged);
        return data;
    }
}
