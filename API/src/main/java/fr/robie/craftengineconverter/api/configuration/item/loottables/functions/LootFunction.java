package fr.robie.craftengineconverter.api.configuration.item.loottables.functions;

import fr.robie.craftengineconverter.api.configuration.item.loottables.LootConfiguration;
import fr.robie.craftengineconverter.api.configuration.conditions.Condition;

import java.util.List;

public interface LootFunction extends LootConfiguration {
    void addCondition(Condition condition);

    List<Condition> getConditions();
}
