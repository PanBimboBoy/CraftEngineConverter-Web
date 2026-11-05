package fr.robie.craftengineconverter.api.configuration.item.behavior.block.behaviors;

import fr.robie.craftengineconverter.api.configuration.item.behavior.block.BlockBehavior;

import java.util.HashMap;
import java.util.Map;

public class DropExpBlockBehavior implements BlockBehavior {
    private String amount;
    private String conditionType;
    private String conditionPredicate;

    public DropExpBlockBehavior() {
    }

    public DropExpBlockBehavior setAmount(String amount) {
        this.amount = amount;
        return this;
    }

    public DropExpBlockBehavior setCondition(String type, String predicate) {
        this.conditionType = type;
        this.conditionPredicate = predicate;
        return this;
    }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "drop_exp_block");
        if (this.amount != null) {
            data.put("amount", this.amount);
        }
        if (this.conditionType != null) {
            Map<String, Object> conditions = new HashMap<>();
            conditions.put("type", this.conditionType);
            conditions.put("predicate", this.conditionPredicate);
            data.put("conditions", conditions);
        }
        return data;
    }
}
