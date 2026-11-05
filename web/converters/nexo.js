'use strict';

const yaml = require('js-yaml');
const { convertMaterial, convertColor, sanitizeItemId, ensureNamespace } = require('./utils');

const DEFAULT_NAMESPACE = 'craftengineconverter';

/**
 * Converts a Nexo item YAML config section to CraftEngine format.
 * @param {string} rawItemId  - The raw Nexo item key
 * @param {object} nexoItem   - Parsed YAML section for this item
 * @param {string} namespace  - Target namespace (default: craftengineconverter)
 * @returns {object}          - CraftEngine item config object
 */
function convertNexoItem(rawItemId, nexoItem, namespace = DEFAULT_NAMESPACE) {
  const itemId = sanitizeItemId(rawItemId);
  const result = {};

  // --- Material ---
  if (nexoItem.material) {
    result.material = convertMaterial(nexoItem.material);
  } else {
    result.material = 'paper';
  }

  // --- Display name ---
  const displayName = nexoItem.itemname || nexoItem.displayname || nexoItem.display_name;
  if (displayName) {
    result.display_name = displayName;
  }

  // --- Lore ---
  if (Array.isArray(nexoItem.lore) && nexoItem.lore.length > 0) {
    result.lore = nexoItem.lore;
  }

  // --- Max stack size ---
  if (nexoItem.max_stack_size != null) {
    result.max_stack_size = nexoItem.max_stack_size;
  }

  // --- Unbreakable ---
  if (nexoItem.unbreakable === true || (nexoItem.durability && nexoItem.durability.unbreakable === true)) {
    result.unbreakable = true;
  }

  // --- Dyed color ---
  const color = nexoItem.color;
  if (color != null) {
    const converted = convertColor(color);
    if (converted) {
      result.dyed_color = converted;
    }
  }

  // --- Enchantment glow override ---
  if (nexoItem.glow === true) {
    result.enchantment_glint_override = true;
  }

  // --- Custom model data ---
  if (nexoItem.custom_model_data != null) {
    result.custom_model_data = nexoItem.custom_model_data;
  }

  // --- Pack / Model ---
  const packSection = nexoItem.Pack || nexoItem.pack;
  if (packSection) {
    result.model = convertNexoModel(rawItemId, packSection, namespace);
  }

  // --- Mechanics ---
  const mechanicsSection = nexoItem.Mechanics || nexoItem.mechanics;
  if (mechanicsSection) {
    const behavior = convertNexoMechanics(mechanicsSection, namespace, `${namespace}:${itemId}`);
    if (behavior) {
      result.behavior = behavior;
    }
  }

  // --- Item flags ---
  const itemFlags = nexoItem.itemflags || nexoItem.item_flags;
  if (Array.isArray(itemFlags) && itemFlags.length > 0) {
    const flagMap = {
      HIDE_ENCHANTS: 'hide_enchantments',
      HIDE_ATTRIBUTES: 'hide_attributes',
      HIDE_UNBREAKABLE: 'hide_unbreakable',
      HIDE_DESTROYS: 'hide_can_destroy',
      HIDE_PLACED_ON: 'hide_can_place_on',
      HIDE_POTION_EFFECTS: 'hide_additional_tooltip',
      HIDE_DYE: 'hide_dye',
    };
    const converted = itemFlags.map(f => flagMap[f.toUpperCase()] || f.toLowerCase()).filter(Boolean);
    if (converted.length > 0) {
      result.hide_tooltip_components = converted;
    }
  }

  return result;
}

/**
 * Convert the Pack section to a CraftEngine model config
 */
function convertNexoModel(rawItemId, pack, namespace) {
  const itemId = sanitizeItemId(rawItemId);
  const model = {
    type: 'craftengine:simple',
    path: `${namespace}:item/${itemId}`,
  };

  const parentModel = pack.parent_model || pack.parent || 'item/generated';
  const textures = pack.textures;

  const generation = {
    parent: parentModel,
    textures: {},
  };

  if (Array.isArray(textures) && textures.length > 0) {
    generation.textures.layer0 = ensureNamespace(textures[0], namespace);
    for (let i = 1; i < textures.length; i++) {
      generation.textures[`layer${i}`] = ensureNamespace(textures[i], namespace);
    }
  } else if (typeof textures === 'string') {
    generation.textures.layer0 = ensureNamespace(textures, namespace);
  }

  if (Object.keys(generation.textures).length > 0) {
    model.generation = generation;
  }

  // Custom model path
  if (pack.model_path || pack.custom_model_path) {
    model.path = ensureNamespace(pack.model_path || pack.custom_model_path, namespace);
  }

  return model;
}

/**
 * Convert the Mechanics section to CraftEngine behavior config.
 * @param {object} mechanics
 * @param {string} namespace
 * @param {string} fullItemId  - fully-qualified id (namespace:itemId)
 */
function convertNexoMechanics(mechanics, namespace, fullItemId) {
  // Custom block (Note block, Mushroom block, etc.)
  const customBlock = mechanics.custom_block || mechanics.Custom_block;
  if (customBlock) {
    return convertNexoCustomBlock(customBlock);
  }

  // Furniture
  const furniture = mechanics.furniture || mechanics.Furniture;
  if (furniture) {
    return convertNexoFurniture(furniture, namespace, fullItemId);
  }

  return null;
}

/**
 * Convert Nexo custom_block mechanic
 */
function convertNexoCustomBlock(block) {
  const type = (block.type || 'NOTE_BLOCK').toUpperCase();
  const behavior = {};

  const blockTypeMap = {
    NOTE_BLOCK: 'craftengine:noteblock',
    MUSHROOM_BLOCK: 'craftengine:mushroom',
    CHORUS_PLANT: 'craftengine:chorusplant',
    TRIPWIRE: 'craftengine:tripwire',
    STRING: 'craftengine:tripwire',
    LEAVES: 'craftengine:leaves',
  };

  behavior.type = blockTypeMap[type] || 'craftengine:noteblock';

  // Block settings
  if (block.hardness != null || block.resistance != null || block.light_level != null) {
    behavior.settings = {};
    if (block.hardness != null) behavior.settings.hardness = block.hardness;
    if (block.resistance != null) behavior.settings.resistance = block.resistance;
    if (block.light_level != null) behavior.settings.light_emission = block.light_level;
  }

  // Block states
  if (block.directional) {
    behavior.block_state = { type: 'directional' };
  } else if (block.pillar) {
    behavior.block_state = { type: 'pillar' };
  }

  return behavior;
}

/**
 * Convert Nexo furniture mechanic → full CraftEngine furniture_item behavior
 * @param {object} furniture
 * @param {string} namespace
 * @param {string} fullItemId  - fully-qualified id (namespace:itemId)
 */
function convertNexoFurniture(furniture, namespace, fullItemId) {
  // --- rotation ---
  let rotation = 'EIGHT';
  if (furniture.rotatable === false) rotation = 'FOUR';
  const restricted = furniture.restricted_rotation;
  if (restricted === 'VERY_STRICT') rotation = 'FOUR';
  else if (restricted === 'STRICT') rotation = 'EIGHT';

  // --- display properties ---
  let billboard = 'fixed';
  let displayTransform = 'fixed';
  let translation = [0, 0.5, 0];
  let scale = [1, 1, 1];

  const props = furniture.properties || {};
  if (props.tracking_rotation) billboard = props.tracking_rotation.toLowerCase();
  if (props.display_transform)  displayTransform = props.display_transform.toLowerCase();
  if (Array.isArray(props.translation) && props.translation.length >= 3) translation = props.translation;
  if (props.scale) {
    const parts = String(props.scale).split(',').map(Number);
    if (parts.length === 3 && parts.every(n => !isNaN(n))) scale = parts;
  }

  // --- placements ---
  const placementKeys = [];
  const limitedPlacing = furniture.limited_placing;
  if (limitedPlacing) {
    if (limitedPlacing.floor)   placementKeys.push('ground');
    if (limitedPlacing.wall)    placementKeys.push('wall');
    if (limitedPlacing.roof || limitedPlacing.ceiling) placementKeys.push('ceiling');
  }
  if (placementKeys.length === 0) placementKeys.push('ground', 'wall', 'ceiling');

  // --- element ---
  const element = {
    type: 'item_display',
    item: fullItemId,
  };
  if (displayTransform !== 'fixed' && displayTransform !== 'none') {
    element['display-transform'] = displayTransform;
  }
  if (billboard !== 'fixed') element.billboard = billboard;
  if (translation.some(v => v !== 0)) element.translation = translation.join(',');
  if (scale.some(v => v !== 1)) element.scale = scale.join(',');

  // --- hitboxes ---
  const hitboxes = [];
  const hitboxSection = furniture.hitbox;
  if (hitboxSection) {
    // barriers → shulker hitboxes
    const barriers = Array.isArray(hitboxSection.barriers) ? hitboxSection.barriers : [];
    for (const barrier of barriers) {
      const coords = String(barrier).split(',').map(s => parseFloat(s.trim()));
      if (coords.length === 3 && coords.every(n => !isNaN(n))) {
        hitboxes.push({ type: 'shulker', position: coords.join(',') });
      }
    }
    // shulkers: "x,y,z scale peek [direction]"
    const shulkers = Array.isArray(hitboxSection.shulkers) ? hitboxSection.shulkers : [];
    for (const entry of shulkers) {
      const parts = String(entry).trim().split(/\s+/);
      if (parts.length < 3) continue;
      const hb = { type: 'shulker', position: parts[0] };
      const sc = parseFloat(parts[1]);
      const pk = parseFloat(parts[2]);
      if (!isNaN(sc) && sc !== 1) hb.scale = sc;
      if (!isNaN(pk) && pk !== 0) hb.peek = Math.round(pk * 100);
      if (parts[3]) hb.direction = parts[3].toUpperCase();
      hitboxes.push(hb);
    }
    // interactions: "x,y,z w,h"
    const interactions = Array.isArray(hitboxSection.interactions) ? hitboxSection.interactions : [];
    for (const entry of interactions) {
      const parts = String(entry).trim().split(/\s+/);
      if (parts.length < 2) continue;
      const hb = { type: 'interaction', position: parts[0] };
      const size = parts[1].split(',').map(Number);
      if (size[0] && size[0] !== 1) hb.width  = size[0];
      if (size[1] && size[1] !== 2) hb.height = size[1];
      hitboxes.push(hb);
    }
    // ghasts: "x,y,z scale"
    const ghasts = Array.isArray(hitboxSection.ghasts) ? hitboxSection.ghasts : [];
    for (const entry of ghasts) {
      const parts = String(entry).trim().split(/\s+/);
      if (parts.length < 2) continue;
      const hb = { type: 'happy_ghast', position: parts[0] };
      const sc = parseFloat(parts[1]);
      if (!isNaN(sc) && sc !== 0.25) hb.scale = sc;
      hitboxes.push(hb);
    }
  }

  // --- sounds ---
  const soundsSection = furniture.block_sounds;
  const sounds = {};
  if (soundsSection) {
    if (soundsSection.place_sound) sounds.place = soundsSection.place_sound;
    if (soundsSection.break_sound) sounds.break  = soundsSection.break_sound;
  }

  // --- loot ---
  const loot = buildFurnitureLoot(furniture.drop, fullItemId);

  // --- seats ---
  const seats = Array.isArray(furniture.seats) ? furniture.seats : [];
  let seatEntry = null;
  if (seats.length > 0) {
    const coords = String(seats[0]).split(',').map(s => parseFloat(s.trim()));
    if (coords.length === 3 && coords.every(n => !isNaN(n))) {
      seatEntry = coords.join(',') + ' 0';
    }
  }
  if (seatEntry && hitboxes.length > 0) {
    hitboxes[0].seats = [seatEntry];
  }

  // --- assemble furniture sub-section ---
  const furnitureObj = {};
  if (Object.keys(sounds).length > 0) {
    furnitureObj.settings = { item: fullItemId, sounds };
  } else {
    furnitureObj.settings = { item: fullItemId };
  }
  furnitureObj.loot = loot;

  const placementObj = {};
  for (const pk of placementKeys) {
    const entry = { rules: { rotation } };
    entry.elements = [element];
    if (hitboxes.length > 0) entry.hitboxes = hitboxes;
    placementObj[pk] = entry;
  }
  furnitureObj.placement = placementObj;

  return {
    type: 'furniture_item',
    settings: { item: fullItemId },
    furniture: furnitureObj,
  };
}

/**
 * Build furniture loot table from Nexo drop section
 */
function buildFurnitureLoot(drop, itemId) {
  const defaultLoot = {
    pools: [{ rolls: 1, conditions: [{ type: 'minecraft:survives_explosion' }], entries: [{ type: 'craftengine:furniture', id: itemId }] }],
  };
  if (!drop) return defaultLoot;

  const silktouch = drop.silktouch === true;
  const fortune   = drop.fortune === true;

  if (silktouch && !fortune) {
    return {
      pools: [{
        rolls: 1,
        conditions: [{ type: 'minecraft:match_tool', predicate: { enchantments: [{ enchantment: 'minecraft:silk_touch', levels: { min: 1 } }] } }],
        entries: [{ type: 'craftengine:furniture', id: itemId }],
      }],
    };
  }

  const loots = Array.isArray(drop.loots) ? drop.loots : [];
  if (loots.length === 0) return defaultLoot;

  const pools = loots.map(loot => {
    const entry = { type: 'minecraft:item', name: loot.name || loot.item || itemId };
    const functions = [];
    if (loot.min_amount != null || loot.max_amount != null) {
      functions.push({ function: 'minecraft:set_count', count: { min: loot.min_amount || 1, max: loot.max_amount || 1 } });
    }
    if (fortune) {
      functions.push({ function: 'minecraft:apply_bonus', enchantment: 'minecraft:fortune', formula: 'minecraft:ore_drops' });
      functions.push({ function: 'minecraft:explosion_decay' });
    }
    if (functions.length > 0) entry.functions = functions;
    const pool = { rolls: 1, entries: [entry] };
    if (loot.probability != null && loot.probability < 1) {
      pool.conditions = [{ type: 'minecraft:random_chance', chance: loot.probability }];
    }
    return pool;
  });

  return { pools };
}

/**
 * Convert a full Nexo YAML config file (multiple items)
 * @param {string} yamlContent - Raw YAML content
 * @param {string} namespace   - Target namespace
 * @returns {{ items: object, errors: string[] }}
 */
function convertNexoConfig(yamlContent, namespace = DEFAULT_NAMESPACE) {
  const errors = [];
  let parsed;

  try {
    parsed = yaml.load(yamlContent);
  } catch (e) {
    return { items: {}, errors: [`YAML parse error: ${e.message}`] };
  }

  if (!parsed || typeof parsed !== 'object') {
    return { items: {}, errors: ['Empty or invalid YAML'] };
  }

  const items = {};

  for (const [rawItemId, itemData] of Object.entries(parsed)) {
    if (!itemData || typeof itemData !== 'object') continue;
    try {
      const converted = convertNexoItem(rawItemId, itemData, namespace);
      const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
      items[finalId] = converted;
    } catch (e) {
      errors.push(`Failed to convert item '${rawItemId}': ${e.message}`);
    }
  }

  return { items, errors };
}

/**
 * Convert Nexo resourcepack assets:
 * Renames model references, keeps structure mostly intact.
 * Returns a map of output path → content (string for text, Buffer for binary)
 */
function convertNexoPackFile(filePath, content) {
  // Only process model JSON files
  if (!filePath.endsWith('.json') || !filePath.includes('models/')) {
    return { path: filePath, content };
  }

  try {
    const model = JSON.parse(content.toString('utf8'));
    // No structural change needed for Nexo — the pack format is already Minecraft-compatible
    // Just ensure overrides reference the right path
    return { path: filePath, content: Buffer.from(JSON.stringify(model, null, 2), 'utf8') };
  } catch {
    return { path: filePath, content };
  }
}

module.exports = {
  convertNexoItem,
  convertNexoConfig,
  convertNexoPackFile,
  DEFAULT_NAMESPACE,
};
