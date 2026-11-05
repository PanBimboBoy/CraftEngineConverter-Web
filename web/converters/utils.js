'use strict';

/**
 * Utility functions shared across all converters
 */

/**
 * Convert a Nexo/IA/Oraxen namespace:path or bare path to a proper namespaced path
 */
function ensureNamespace(path, defaultNamespace) {
  if (!path) return path;
  if (path.includes(':')) return path;
  return `${defaultNamespace}:${path}`;
}

/**
 * Strip common texture prefixes that ItemsAdder uses
 */
function cleanTexturePath(path) {
  if (!path) return path;
  // Remove leading slash
  return path.replace(/^\//, '');
}

/**
 * Convert a Bukkit material name to lowercase CraftEngine material
 */
function convertMaterial(material) {
  if (!material) return 'paper';
  return material.toLowerCase().replace(/_/g, '_');
}

/**
 * Convert Nexo/Oraxen color to CraftEngine dyed_color format
 */
function convertColor(color) {
  if (!color) return null;
  // Handle hex color: #RRGGBB or RRGGBB
  if (typeof color === 'string') {
    const hex = color.replace('#', '');
    if (hex.length === 6) {
      const r = parseInt(hex.substring(0, 2), 16);
      const g = parseInt(hex.substring(2, 4), 16);
      const b = parseInt(hex.substring(4, 6), 16);
      return { r, g, b };
    }
  }
  // Handle integer color
  if (typeof color === 'number') {
    const r = (color >> 16) & 0xFF;
    const g = (color >> 8) & 0xFF;
    const b = color & 0xFF;
    return { r, g, b };
  }
  return null;
}

/**
 * Deep merge two objects
 */
function deepMerge(target, source) {
  const result = { ...target };
  for (const key of Object.keys(source)) {
    if (source[key] && typeof source[key] === 'object' && !Array.isArray(source[key])) {
      result[key] = deepMerge(result[key] || {}, source[key]);
    } else {
      result[key] = source[key];
    }
  }
  return result;
}

/**
 * Convert ItemsAdder/Nexo recipe ingredient format to CraftEngine
 */
function convertIngredient(ingredient, pluginType) {
  if (!ingredient) return null;
  if (typeof ingredient === 'string') {
    // Could be a vanilla material or namespaced item
    if (ingredient.includes(':') && !ingredient.startsWith('minecraft:')) {
      return { craftengine: ingredient.toLowerCase() };
    }
    return { vanilla: ingredient.toLowerCase().replace('minecraft:', '') };
  }
  if (typeof ingredient === 'object') {
    const mat = ingredient.material || ingredient.type;
    if (mat) {
      if (mat.includes(':') && !mat.startsWith('minecraft:')) {
        return { craftengine: mat.toLowerCase() };
      }
      return { vanilla: mat.toLowerCase().replace('minecraft:', '') };
    }
  }
  return null;
}

/**
 * Sanitize item ID to valid CraftEngine format
 */
function sanitizeItemId(id) {
  return id.toLowerCase().replace(/[^a-z0-9_.-]/g, '_');
}

module.exports = {
  ensureNamespace,
  cleanTexturePath,
  convertMaterial,
  convertColor,
  deepMerge,
  convertIngredient,
  sanitizeItemId,
};
