package fr.robie.craftengineconverter.api.configuration.item.data;

import net.momirealms.craftengine.core.util.Key;
import org.jetbrains.annotations.Nullable;

public record AttributeModifier(String type, net.momirealms.craftengine.core.attribute.AttributeModifier.Slot slot,
                                Key id, double amount,
                                net.momirealms.craftengine.core.attribute.AttributeModifier.Operation operation,
                                @Nullable Display display) {
    public AttributeModifier(String type, net.momirealms.craftengine.core.attribute.AttributeModifier.Slot slot, Key id, double amount, net.momirealms.craftengine.core.attribute.AttributeModifier.Operation operation, @Nullable Display display) {
        this.amount = amount;
        this.display = display;
        this.id = id;
        this.operation = operation;
        this.slot = slot;
        this.type = type;
    }

    public record Display(net.momirealms.craftengine.core.attribute.AttributeModifier.Display.Type type, String value) {
    }
}
