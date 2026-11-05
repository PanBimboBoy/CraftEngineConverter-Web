package fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.element;

import fr.robie.craftengineconverter.api.configuration.item.behavior.furniture.ItemElement;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class SimpleItemElement extends ItemElement {
    public SimpleItemElement(@NotNull String item) { super(item); }

    @Override
    public Map<String, Object> serialize() {
        Map<String, Object> data = new HashMap<>();
        data.put("type", "item");
        super.serialize(data);
        return data;
    }
}
