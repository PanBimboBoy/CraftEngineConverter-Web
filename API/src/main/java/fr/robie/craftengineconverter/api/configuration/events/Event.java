package fr.robie.craftengineconverter.api.configuration.events;

import org.bukkit.configuration.ConfigurationSection;
import org.jetbrains.annotations.NotNull;

public interface Event {
    void serialize(@NotNull ConfigurationSection section);
}
