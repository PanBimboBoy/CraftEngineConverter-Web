package fr.robie.craftengineconverter.common;

import fr.robie.craftengineconverter.api.CraftEngineConverterPluginInterface;
import fr.robie.craftengineconverter.api.profile.ServerProfile;
import fr.robie.craftengineconverter.api.tag.ITagResolver;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class CraftEngineConverterPlugin extends JavaPlugin implements CraftEngineConverterPluginInterface {
    protected CraftEnginePlacementTracker placementTracker = new CraftEnginePlacementTracker();

    public abstract ITagResolver getTagResolver();

    public CraftEnginePlacementTracker getPlacementTracker() {
        return this.placementTracker;
    }

    public abstract ServerProfile getServerProfile();
}
