package fr.robie.craftengineconverter.common.converter;

import fr.robie.craftengineconverter.api.enums.Plugins;
import fr.robie.craftengineconverter.api.profile.ServerProfile;
import fr.robie.craftengineconverter.common.CraftEngineConverterPlugin;
import fr.robie.craftengineconverter.common.PluginNameMapper;

public abstract class ObjectConverter {
    protected final CraftEngineConverterPlugin plugin;
    protected final ServerProfile serverProfile;
    protected final Plugins pluginType;
    protected final PluginNameMapper nameMapper;
    protected final int[][] ADJACENT_OFFSETS = {
            {0, 1, 0},   // above
            {0, -1, 0},  // below
            {-1, 0, 0},  // left
            {1, 0, 0},   // right
            {0, 0, 1},   // front
            {0, 0, -1}   // back
    };

    public ObjectConverter(CraftEngineConverterPlugin plugin, Plugins pluginType) {
        this.plugin = plugin;
        this.serverProfile = plugin.getServerProfile();
        this.pluginType = pluginType;
        this.nameMapper = PluginNameMapper.getInstance();
    }

    public String getNewName(String oldName) {
        return this.nameMapper.getNewName(this.pluginType, oldName);
    }

    public abstract boolean isRegistered(String itemId);

    protected static class ConversionCounter {
        private final int maxConversions;
        private int conversions = 0;

        public ConversionCounter(int maxConversions) {
            this.maxConversions = maxConversions;
        }

        void increment() {
            this.conversions++;
        }

        boolean hasReachedLimit() {
            return this.conversions >= this.maxConversions;
        }
    }
}
