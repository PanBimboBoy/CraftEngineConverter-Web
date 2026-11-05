package fr.robie.craftengineconverter.common.converter;

import fr.robie.craftengineconverter.api.enums.Plugins;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

public interface WorldConverter {

    boolean convertEntity(@NotNull Entity entity);

    @NotNull
    Plugins getPlugin();
}
