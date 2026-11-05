package fr.robie.craftengineconverter.api.tag;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Utility interface for resolving custom tags in text strings using registered {@link TagProcessor}s.
 * <p>
 * This interface provides methods to initialize tag processors and resolve tags within messages,
 * supporting player-specific context for dynamic content.
 * </p>
 */
public interface ITagResolver {

    /**
     * Registers a new custom tag processor.
     *
     * @param processor The tag processor to register
     */
    void registerTagProcessor(@NotNull TagProcessor processor);

    /**
     * Resolves all custom tags in the given message using registered tag processors.
     * <p>
     * This method processes the input message through all registered tag processors,
     * replacing recognized tags with their corresponding values. The player parameter
     * can be used for context-specific replacements (e.g., player name, permissions).
     * </p>
     * <p>
     * Tags are processed sequentially by each registered processor. If multiple processors
     * match overlapping patterns, the order of processing determines the final result.
     * </p>
     *
     * @param message The message containing tags to resolve (e.g., "Hello {@code <glyph:heart>}")
     * @param player The player for whom to resolve tags, may be {@code null} for context-free resolution
     * @return An {@link Optional} containing the resolved message if any tags were processed,
     *         or {@link Optional#empty()} if no tags were found or resolved
     */
    Optional<String> resolveTags(@NotNull String message,@NotNull Player player);
}
