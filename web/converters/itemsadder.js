'use strict';

const yaml = require('js-yaml');
const { convertMaterial, convertColor, sanitizeItemId, ensureNamespace } = require('./utils');

/**
 * Converts an ItemsAdder YAML config file to CraftEngine format.
 * ItemsAdder uses a `info.namespace` + `items.ITEM_ID` structure.
 */

function convertIAItem(rawItemId, itemData, namespace) {
  const itemId = sanitizeItemId(rawItemId);
  const result = {};

  // --- Material ---
  const resource = itemData.resource;
  if (resource && resource.material) {
    result.material = convertMaterial(resource.material);
  } else {
    result.material = 'paper';
  }

  // --- Display name ---
  const displayName = itemData.display_name || itemData.name || itemData.displayname;
  if (displayName) {
    // l10n key passthrough
    if (displayName.startsWith('display-name-')) {
      result.display_name = `<l10n:${displayName}>`;
    } else {
      result.display_name = displayName;
    }
  }

  // --- Lore ---
  if (Array.isArray(itemData.lore) && itemData.lore.length > 0) {
    result.lore = itemData.lore.map(line => {
      if (typeof line === 'string' && line.startsWith('lore-')) {
        return `<l10n:${line}>`;
      }
      return line;
    });
  }

  // --- Max stack size ---
  if (itemData.max_stacksize != null) {
    result.max_stack_size = itemData.max_stacksize;
  }

  // --- Unbreakable ---
  const durability = itemData.durability;
  if (durability && durability.unbreakable === true) {
    result.unbreakable = true;
  }

  // --- Dyed color ---
  const color = itemData['graphics'] && itemData['graphics']['color'];
  if (color != null) {
    const converted = convertColor(color);
    if (converted) result.dyed_color = converted;
  }

  // --- Custom model data ---
  if (resource && resource.custom_model_data != null) {
    result.custom_model_data = resource.custom_model_data;
  }

  // --- Model ---
  if (resource) {
    result.model = convertIAModel(rawItemId, resource, namespace);
  }

  // --- Item flags ---
  const itemFlags = itemData.item_flags;
  if (Array.isArray(itemFlags) && itemFlags.length > 0) {
    const flagMap = {
      HIDE_ENCHANTS: 'hide_enchantments',
      HIDE_ATTRIBUTES: 'hide_attributes',
      HIDE_UNBREAKABLE: 'hide_unbreakable',
      HIDE_DESTROYS: 'hide_can_destroy',
      HIDE_PLACED_ON: 'hide_can_place_on',
      HIDE_POTION_EFFECTS: 'hide_additional_tooltip',
    };
    const converted = itemFlags.map(f => flagMap[f.toUpperCase()] || f.toLowerCase()).filter(Boolean);
    if (converted.length > 0) result.hide_tooltip_components = converted;
  }

  // --- Mechanics / Behavior ---
  const mechanics = itemData.Mechanics || itemData.mechanics;
  if (mechanics) {
    const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
    const behavior = convertIAMechanics(mechanics, finalId);
    if (behavior) result.behavior = behavior;
  }

  // --- Enchantment glow ---
  if (itemData.glow === true) {
    result.enchantment_glint_override = true;
  }

  // --- Food ---
  if (itemData.food != null) {
    result.food = {
      nutrition: itemData.food.nutrition || 4,
      saturation: itemData.food.saturation_modifier || 0.3,
    };
  }

  return result;
}

function convertIAModel(rawItemId, resource, namespace) {
  const itemId = sanitizeItemId(rawItemId);
  const model = {
    type: 'craftengine:simple',
    path: `${namespace}:item/${itemId}`,
  };

  // If a model_path is specified
  if (resource.model_path) {
    model.path = ensureNamespace(resource.model_path, namespace);
  }

  // If generate is true, add generation block
  if (resource.generate !== false) {
    const parentModel = resource.parent || resource.parent_model || 'item/generated';
    const generation = {
      parent: parentModel,
      textures: {},
    };

    const textures = resource.textures;
    if (Array.isArray(textures) && textures.length > 0) {
      generation.textures.layer0 = ensureNamespace(textures[0], namespace);
      for (let i = 1; i < textures.length; i++) {
        generation.textures[`layer${i}`] = ensureNamespace(textures[i], namespace);
      }
    } else if (typeof textures === 'string') {
      generation.textures.layer0 = ensureNamespace(textures, namespace);
    } else if (!textures && resource.texture) {
      generation.textures.layer0 = ensureNamespace(resource.texture, namespace);
    }

    if (Object.keys(generation.textures).length > 0) {
      model.generation = generation;
    }
  }

  return model;
}

function convertIAMechanics(mechanics, itemId) {
  // Custom block
  const customBlock = mechanics.custom_block || mechanics.real_note;
  if (customBlock) {
    return convertIACustomBlock(customBlock);
  }

  // Furniture (armor stand or display entity based)
  const furniture = mechanics.furniture || mechanics.real_furniture;
  if (furniture) {
    return convertIAFurniture(furniture, itemId);
  }

  // Liquid blocks
  const customFire = mechanics.custom_fire;
  if (customFire) {
    return { type: 'craftengine:custom_fire' };
  }

  return null;
}

function convertIACustomBlock(block) {
  const behavior = {};

  // Determine block type
  const type = block.type;
  if (type) {
    const t = type.toUpperCase();
    if (t.includes('NOTE') || t === 'REAL') {
      behavior.type = 'craftengine:noteblock';
    } else if (t.includes('MUSHROOM') || t.includes('REAL_BLOCK')) {
      behavior.type = 'craftengine:mushroom';
    } else if (t.includes('CHORUS')) {
      behavior.type = 'craftengine:chorusplant';
    } else if (t.includes('WIRE') || t.includes('TRIPWIRE')) {
      behavior.type = 'craftengine:tripwire';
    } else {
      behavior.type = 'craftengine:noteblock';
    }
  } else {
    behavior.type = 'craftengine:noteblock';
  }

  // Block settings
  const settings = {};
  if (block.hardness != null) settings.hardness = block.hardness;
  if (block.blast_resistance != null) settings.resistance = block.blast_resistance;
  if (block.light_level != null) settings.light_emission = block.light_level;
  if (block.speed_reduction != null) settings.speed_factor = 1.0 - block.speed_reduction;
  if (Object.keys(settings).length > 0) behavior.settings = settings;

  // Directional
  const directional = block.directional;
  if (directional) {
    behavior.block_state = { type: 'directional' };
  }

  // Loot
  if (block.drop) {
    const drop = block.drop;
    if (drop.name) {
      behavior.loot_table = {
        pools: [{
          rolls: 1,
          entries: [{
            type: 'craftengine:item',
            item: { id: drop.name.toLowerCase() },
          }],
        }],
      };
    }
  }

  return behavior;
}

function convertIAFurniture(furniture, itemId) {
  // --- entity type ---
  const entity = (furniture.entity || 'ITEM_FRAME').toUpperCase();
  const isArmorStand = entity === 'ARMOR_STAND';

  // --- placements ---
  const placementKeys = [];
  const placeableOn = furniture.placeable_on;
  if (placeableOn) {
    if (placeableOn.floor   !== false) placementKeys.push('ground');
    if (placeableOn.ceiling !== false) placementKeys.push('ceiling');
    if (placeableOn.wall    !== false) placementKeys.push('wall');
  }
  if (placementKeys.length === 0) placementKeys.push('ground', 'wall', 'ceiling');

  // --- display_transformation ---
  let displayTransform = 'none';
  let translation = [0, 0, 0];
  let scale = [1, 1, 1];

  const dtSection = furniture.display_transformation;
  if (dtSection) {
    if (dtSection.transform) displayTransform = dtSection.transform.toLowerCase();
    if (dtSection.translation) {
      translation = [dtSection.translation.x || 0, dtSection.translation.y || 0, dtSection.translation.z || 0];
    }
    if (dtSection.scale) {
      scale = [dtSection.scale.x || 1, dtSection.scale.y || 1, dtSection.scale.z || 1];
    }
  }

  // En IA: small:false en el YAML → setSmall(true) en el elemento (armor stand pequeño)
  //        small:true (default)  → armor stand normal (no setSmall)
  const setArmorStandSmall = (furniture.small === false);

  // hitbox height for vertical centering
  const hitboxHeight = (furniture.hitbox && furniture.hitbox.height) ? furniture.hitbox.height : 1;

  // --- per-placement translation offsets (matches Java logic) ---
  const placementTranslations = {
    ground:  [0,  hitboxHeight / 2, 0],
    wall:    [0, -1,               0.5],
    ceiling: [0, -hitboxHeight / 2, 0],
  };

  // --- build element per placement ---
  const furnitureObj = {};
  furnitureObj.settings = { item: itemId };

  // loot
  furnitureObj.loot = {
    pools: [{ rolls: 1, conditions: [{ type: 'minecraft:survives_explosion' }], entries: [{ type: 'craftengine:furniture', id: itemId }] }],
  };

  // light level on hitbox
  const lightLevel = furniture.light_level != null ? furniture.light_level : -1;

  const placementObj = {};
  for (const pk of placementKeys) {
    const pt = placementTranslations[pk];
    const entry = {};

    // element
    if (isArmorStand) {
      const el = { type: 'armor_stand', item: itemId };
      if (setArmorStandSmall) el.small = true;
      if (scale.some(v => v !== 1)) el.scale = scale.join(',');
      entry.elements = [el];
    } else {
      const el = { type: 'item_display', item: itemId };
      if (displayTransform !== 'none') el['display-transform'] = displayTransform;
      // combine placement offset + display_transformation translation
      const tx = (translation[0] || 0) + pt[0];
      const ty = (translation[1] || 0) + pt[1];
      const tz = (translation[2] || 0) + pt[2];
      if (tx !== 0 || ty !== 0 || tz !== 0) el.translation = `${tx},${ty},${tz}`;
      if (scale.some(v => v !== 1)) el.scale = scale.join(',');
      if (lightLevel >= 0) el['brightness'] = { 'block-light': lightLevel };
      entry.elements = [el];
    }

    // hitboxes
    const hitboxes = buildIAHitboxes(furniture.hitbox, pk);
    if (hitboxes.length > 0) {
      // for wall: offset hitboxes by placement translation
      if (pk === 'wall') {
        for (const hb of hitboxes) {
          const pos = hb.position ? hb.position.split(',').map(Number) : [0, 0, 0];
          hb.position = `${pos[0] + pt[0]},${pos[1] + pt[1]},${pos[2] + pt[2]}`;
        }
      }
      entry.hitboxes = hitboxes;
    }

    placementObj[pk] = entry;
  }
  furnitureObj.placement = placementObj;

  return {
    type: 'furniture_item',
    settings: { item: itemId },
    furniture: furnitureObj,
  };
}

/**
 * Build hitbox list from ItemsAdder hitbox section for a given placement
 */
function buildIAHitboxes(hitboxSection, placement) {
  if (!hitboxSection) return [];
  const hitboxes = [];
  const width  = hitboxSection.width  || 1;
  const height = hitboxSection.height || 1;
  // IA hitbox is width×height, mapped to a single shulker hitbox centered
  const hb = { type: 'shulker', position: '0,0,0' };
  if (width  !== 1) hb.scale = width;
  hitboxes.push(hb);
  return hitboxes;
}

/**
 * Convert a full ItemsAdder YAML file (info section + items section)
 * @param {string} yamlContent - Raw YAML string
 * @param {string} overrideNamespace - Override namespace (optional)
 * @returns {{ items: object, namespace: string, errors: string[] }}
 */
function convertIAConfig(yamlContent, overrideNamespace = null) {
  const errors = [];
  let parsed;

  try {
    parsed = yaml.load(yamlContent);
  } catch (e) {
    return { items: {}, namespace: 'craftengine', errors: [`YAML parse error: ${e.message}`] };
  }

  if (!parsed || typeof parsed !== 'object') {
    return { items: {}, namespace: 'craftengine', errors: ['Empty or invalid YAML'] };
  }

  const namespace = overrideNamespace || (parsed.info && parsed.info.namespace) || 'craftengine';
  const items = {};

  const itemsSection = parsed.items;
  if (!itemsSection || typeof itemsSection !== 'object') {
    // Maybe no items section — try treating top-level keys as items
    for (const [rawItemId, itemData] of Object.entries(parsed)) {
      if (rawItemId === 'info' || rawItemId === 'equipments' || rawItemId === 'armors_rendering') continue;
      if (!itemData || typeof itemData !== 'object') continue;
      try {
        const converted = convertIAItem(rawItemId, itemData, namespace);
        const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
        items[finalId] = converted;
      } catch (e) {
        errors.push(`Failed to convert item '${rawItemId}': ${e.message}`);
      }
    }
  } else {
    for (const [rawItemId, itemData] of Object.entries(itemsSection)) {
      if (!itemData || typeof itemData !== 'object') continue;
      try {
        const converted = convertIAItem(rawItemId, itemData, namespace);
        const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
        items[finalId] = converted;
      } catch (e) {
        errors.push(`Failed to convert item '${rawItemId}': ${e.message}`);
      }
    }
  }

  return { items, namespace, errors };
}

module.exports = { convertIAItem, convertIAConfig };
