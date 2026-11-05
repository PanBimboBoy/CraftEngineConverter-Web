package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class AttachedStemBlockBehavior implements BlockBehavior {
    private final String fruit;
    private final String stem;

    public AttachedStemBlockBehavior(String fruit, String stem) {
        this.fruit = fruit;
        this.stem = stem;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "attached_stem_block");
        data.put("fruit", this.fruit);
        data.put("stem", this.stem);
        return data;
    }
}
