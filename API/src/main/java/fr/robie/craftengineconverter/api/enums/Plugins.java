package fr.robie.craftengineconverter.api.enums;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum Plugins {
    NEXO("Nexo"),
    CRAFTENGINE("CraftEngine"),
    PACKET_EVENTS("packetevents"),
    PLACEHOLDER_API("PlaceholderAPI"),
    ITEMS_ADDER("ItemsAdder");
    private static final Map<Plugins, Boolean> presenceCache = new ConcurrentHashMap<>();

    private final String pluginName;

    Plugins(String pluginName) {
        this.pluginName = pluginName;
    }

    public boolean isPresent() {
        return presenceCache.computeIfAbsent(this, plugin -> {
            Plugin bukkitPlugin = Bukkit.getServer().getPluginManager().getPlugin(this.pluginName);
            return bukkitPlugin != null;
        });
    }

    public boolean isEnabled() {
        Plugin bukkitPlugin = Bukkit.getServer().getPluginManager().getPlugin(this.pluginName);
        return bukkitPlugin != null && bukkitPlugin.isEnabled();
    }
}
