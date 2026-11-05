package fr.robie.craftengineconverter.api.configuration.item.models.range_dispatch;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public class CountRangeDispatchConfiguration extends RangeDispatchModelConfiguration {
    private final boolean normalize;

    public CountRangeDispatchConfiguration(boolean normalize) {
        super("minecraft:count");
        this.normalize = normalize;
    }

    @Override
    public void serialize(@NotNull ConfigurationSection section) {
        super.serialize(section);
        section.set("normalize", this.normalize);
    }
}
