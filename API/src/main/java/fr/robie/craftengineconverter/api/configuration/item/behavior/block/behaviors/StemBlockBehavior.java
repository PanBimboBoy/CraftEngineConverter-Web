package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class StemBlockBehavior implements BlockBehavior {
    private String fruit;
    private String attachedStem;

    public StemBlockBehavior() {
    }

    public StemBlockBehavior setFruit(String fruit) {
        this.fruit = fruit;
        return this;
    }

    public StemBlockBehavior setAttachedStem(String attachedStem) {
        this.attachedStem = attachedStem;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "stem_block");
        if (this.fruit != null) {
            data.put("fruit", this.fruit);
        }
        if (this.attachedStem != null) {
            data.put("attached-stem", this.attachedStem);
        }
        return data;
    }
}
