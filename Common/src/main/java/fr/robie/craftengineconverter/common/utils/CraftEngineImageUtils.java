package fr.robie.craftengineconverter.common.utils;

import fr.robie.craftengineconverter.api.cache.SimpleCache;
import fr.robie.craftengineconverter.common.records.ImageConversion;
import net.momirealms.craftengine.bukkit.api.CraftEngineImages;
import net.momirealms.craftengine.core.font.FontManager;
import net.momirealms.craftengine.core.font.Image;
import net.momirealms.craftengine.core.plugin.CraftEngine;
import net.momirealms.craftengine.core.util.Key;

import java.util.Optional;

public class CraftEngineImageUtils {
    private static final SimpleCache<String, ImageConversion> imageConversionCache = new SimpleCache<>();
    private static final FontManager fontManager = CraftEngine.instance().fontManager();

    /**
     * Register an image name conversion from original to converted (with namespace)
     * @param originalName The original name (e.g., "custom_sword")
     * @param imageConversion The converted name with namespace (e.g., "nexo:custom_sword")
     */
    public static void register(String originalName, ImageConversion imageConversion) {
        imageConversionCache.put(originalName, imageConversion);
    }

    /**
     * Get the converted name for an original image name
     * @param originalName The original name
     * @return The converted name with namespace, or null if not registered
     */
    public static ImageConversion getConverted(String originalName) {
        return imageConversionCache.get(originalName);
    }

    /**
     * Check if an image name has been registered
     * @param originalName The original name to check
     * @return true if the name has been registered
     */
    public static boolean isRegistered(String originalName) {
        return imageConversionCache.containsKey(originalName);
    }

    public static Optional<String> convert(String originalName) {
        if (isRegistered(originalName)){
            ImageConversion imageConversion = getConverted(originalName);
            Image bitmapImage = CraftEngineImages.byId(Key.of(imageConversion.convertedName()));
            if (bitmapImage != null){
                return Optional.of(bitmapImage.miniMessageAt(imageConversion.row(), imageConversion.column()));
            }
        }
        return Optional.empty();
    }

    public static String createOffsetImage(int offset){
         return fontManager.createMiniMessageOffsets(offset);
    }

    public static void clearCache() {
        imageConversionCache.clear();
    }
}
