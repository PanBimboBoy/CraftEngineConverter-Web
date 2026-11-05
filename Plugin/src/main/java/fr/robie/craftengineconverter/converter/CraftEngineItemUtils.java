package fr.robie.craftengineconverter.converter;

import fr.robie.craftengineconverter.api.utils.ObjectUtils;
import org.bukkit.configuration.ConfigurationSection;

public class CraftEngineItemUtils extends ObjectUtils {
    private final ConfigurationSection craftEngineItemSection;

    public CraftEngineItemUtils(ConfigurationSection craftEngineItemSection){
        this.craftEngineItemSection = craftEngineItemSection;
    }

}
