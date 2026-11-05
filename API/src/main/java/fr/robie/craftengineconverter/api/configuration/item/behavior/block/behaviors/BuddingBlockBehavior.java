package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuddingBlockBehavior implements BlockBehavior {
    private Double growthChance;
    private List<String> blocks;

    public BuddingBlockBehavior() {
        this.blocks = new ArrayList<>();
    }

    public BuddingBlockBehavior setGrowthChance(double growthChance) {
        this.growthChance = growthChance;
        return this;
    }

    public BuddingBlockBehavior addBlock(String block) {
        this.blocks.add(block);
        return this;
    }

    public BuddingBlockBehavior setBlocks(List<String> blocks) {
        this.blocks = blocks;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "budding_block");
        if (this.growthChance != null) {
            data.put("growth-chance", this.growthChance);
        }
        if (!this.blocks.isEmpty()) {
            data.put("blocks", this.blocks);
        }
        return data;
    }
}
