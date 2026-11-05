package fr.robie.craftengineconverter.common;

import fr.robie.craftengineconverter.api.enums.Plugins;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class PluginNameMapper {
    private static final PluginNameMapper INSTANCE = new PluginNameMapper();
    private final Map<String, Map<String, String>> mappings = new ConcurrentHashMap<>();

    private PluginNameMapper() {
    }

    public boolean hasMapping(Plugins plugin, String oldName) {
        Map<String, String> pluginMappings = this.mappings.get(plugin.name());
        return pluginMappings != null && pluginMappings.containsKey(oldName);
    }

    @Nullable
    public String getNewName(Plugins plugin, String oldName) {
        Map<String, String> pluginMappings = this.mappings.get(plugin.name());
        return pluginMappings != null ? pluginMappings.get(oldName) : null;
    }

    public void storeMapping(Plugins plugin, String oldName, String newName) {
        this.mappings.computeIfAbsent(plugin.name(), k -> new ConcurrentHashMap<>())
                .put(oldName, newName);
    }

    public static PluginNameMapper getInstance() {
        return INSTANCE;
    }

    public void clearMappingsForPlugin(Plugins plugins) {
        this.mappings.remove(plugins.name());
    }
}