package fr.robie.craftengineconverter.utils;

import fr.robie.craftengineconverter.api.configuration.Configuration;
import fr.robie.craftengineconverter.api.configuration.ConfigurationKey;
import fr.robie.craftengineconverter.api.enums.Plugins;
import fr.robie.craftengineconverter.api.tag.ITagResolver;
import fr.robie.craftengineconverter.api.tag.TagProcessor;
import fr.robie.craftengineconverter.hooks.placeholderapi.tag.PlaceholderAPITag;
import fr.robie.craftengineconverter.tag.GlyphTagProcessor;
import fr.robie.craftengineconverter.tag.IAImageTagProcessor;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TagResolver implements ITagResolver {
    private final List<TagProcessor> tagProcessors = new ArrayList<>();

    public void initTagProcessors() {
        if (Configuration.<Boolean>get(ConfigurationKey.GLYPH_TAG_ENABLED)){
            this.registerTagProcessor(new GlyphTagProcessor());
        }
        if (Configuration.<Boolean>get(ConfigurationKey.IMAGE_TAG_ENABLED)){
            this.registerTagProcessor(new IAImageTagProcessor());
        }
        if (Plugins.PLACEHOLDER_API.isPresent() && Configuration.<Boolean>get(ConfigurationKey.PLACEHOLDER_API_TAG_ENABLED)){
            this.registerTagProcessor(new PlaceholderAPITag());
        }
    }

    @Override
    public void registerTagProcessor(@NotNull TagProcessor processor) {
        this.tagProcessors.add(processor);
    }

    @Override
    public Optional<String> resolveTags(@NotNull String message, @NotNull Player player) {
        String result = message;
        boolean modified = false;

        for (TagProcessor processor : this.tagProcessors) {
            if (!processor.hasTag(result)) {
                continue;
            }
            Optional<String> processed = processor.process(result, player);
            if (processed.isPresent()) {
                result = processed.get();
                modified = true;
            }
        }

        return modified ? Optional.of(result) : Optional.empty();
    }


}
