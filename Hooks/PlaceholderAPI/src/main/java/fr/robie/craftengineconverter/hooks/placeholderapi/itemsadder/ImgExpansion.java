package fr.robie.craftengineconverter.hooks.placeholderapi.itemsadder;

import fr.robie.craftengineconverter.common.CraftEngineConverterPlugin;
import fr.robie.craftengineconverter.common.utils.CraftEngineImageUtils;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ImgExpansion extends PlaceholderExpansion {
    private final CraftEngineConverterPlugin plugin;
    private final String author;

    public ImgExpansion(CraftEngineConverterPlugin plugin) {
        this.plugin = plugin;
        this.author = this.plugin.getFoliaCompatibilityManager().isPaper() ? this.plugin.getPluginMeta().getAuthors().getFirst() : this.plugin.getDescription().getAuthors().getFirst();
    }

    @Override
    public @NotNull String getIdentifier() {
        return "img";
    }

    @Override
    public @NotNull String getAuthor() {
        return this.author;
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        return CraftEngineImageUtils.convert(params).orElse(null);
    }
}
