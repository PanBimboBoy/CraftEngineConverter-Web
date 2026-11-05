'use strict';

const yaml = require('js-yaml');
const { convertMaterial, convertColor, sanitizeItemId, ensureNamespace } = require('./utils');

const DEFAULT_NAMESPACE = 'craftengineconverter';

/**
 * Converts an Oraxen item YAML config to CraftEngine format.
 * Oraxen format is similar to Nexo (same developer), with minor differences.
 */

function convertOraxenItem(rawItemId, oraxenItem, namespace = DEFAULT_NAMESPACE) {
  const itemId = sanitizeItemId(rawItemId);
  const result = {};

  // --- Material ---
  if (oraxenItem.material) {
    result.material = convertMaterial(oraxenItem.material);
  } else {
    result.material = 'paper';
  }

  // --- Display name (Oraxen uses displayname / display_name) ---
  const displayName = oraxenItem.displayname || oraxenItem.display_name || oraxenItem.itemname;
  if (displayName) {
    result.display_name = displayName;
  }

  // --- Lore ---
  if (Array.isArray(oraxenItem.lore) && oraxenItem.lore.length > 0) {
    result.lore = oraxenItem.lore;
  }

  // --- Max stack size ---
  if (oraxenItem.max_stack_size != null) {
    result.max_stack_size = oraxenItem.max_stack_size;
  }

  // --- Unbreakable ---
  if (oraxenItem.unbreakable === true) {
    result.unbreakable = true;
  }

  // --- Dyed color ---
  if (oraxenItem.color != null) {
    const converted = convertColor(oraxenItem.color);
    if (converted) result.dyed_color = converted;
  }

  // --- Glow ---
  if (oraxenItem.glow === true) {
    result.enchantment_glint_override = true;
  }

  // --- Pack / Model ---
  const packSection = oraxenItem.Pack || oraxenItem.pack;
  if (packSection) {
    result.model = convertOraxenModel(rawItemId, packSection, namespace);
  }

  // --- Mechanics ---
  const mechanicsSection = oraxenItem.Mechanics || oraxenItem.mechanics;
  if (mechanicsSection) {
    const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
    const behavior = convertOraxenMechanics(mechanicsSection, finalId);
    if (behavior) result.behavior = behavior;
  }

  // --- Item flags ---
  const itemFlags = oraxenItem.item_flags || oraxenItem.ItemFlags;
  if (Array.isArray(itemFlags) && itemFlags.length > 0) {
    const flagMap = {
      HIDE_ENCHANTS: 'hide_enchantments',
      HIDE_ATTRIBUTES: 'hide_attributes',
      HIDE_UNBREAKABLE: 'hide_unbreakable',
      HIDE_DESTROYS: 'hide_can_destroy',
      HIDE_PLACED_ON: 'hide_can_place_on',
    };
    const converted = itemFlags.map(f => flagMap[f.toUpperCase()] || f.toLowerCase()).filter(Boolean);
    if (converted.length > 0) result.hide_tooltip_components = converted;
  }

  return result;
}

function convertOraxenModel(rawItemId, pack, namespace) {
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

  if (pack.model_path) {
    model.path = ensureNamespace(pack.model_path, namespace);
  }

  return model;
}

function convertOraxenMechanics(mechanics, itemId) {
  // Oraxen mechanic key names
  const noteblock = mechanics.noteblock || mechanics.NoteBlock;
  if (noteblock) {
    const behavior = { type: 'craftengine:noteblock' };
    if (noteblock.hardness != null || noteblock.resistance != null) {
      behavior.settings = {};
      if (noteblock.hardness != null) behavior.settings.hardness = noteblock.hardness;
      if (noteblock.resistance != null) behavior.settings.resistance = noteblock.resistance;
    }
    return behavior;
  }

  const stringBlock = mechanics.stringblock || mechanics.StringBlock;
  if (stringBlock) {
    const behavior = { type: 'craftengine:tripwire' };
    if (stringBlock.hardness != null) {
      behavior.settings = { hardness: stringBlock.hardness };
    }
    return behavior;
  }

  const chorusBlock = mechanics.chorus || mechanics.ChorusPlant;
  if (chorusBlock) {
    return { type: 'craftengine:chorusplant' };
  }

  const furniture = mechanics.furniture || mechanics.Furniture;
  if (furniture) {
    return convertOraxenFurniture(furniture, itemId);
  }

  return null;
}

/**
 * Oraxen furniture → CraftEngine furniture_item
 * Oraxen furniture format is very similar to Nexo (same dev lineage)
 */
function convertOraxenFurniture(furniture, itemId) {
  // rotation
  let rotation = 'EIGHT';
  if (furniture.rotatable === false) rotation = 'FOUR';
  const restricted = furniture.restricted_rotation;
  if (restricted === 'VERY_STRICT') rotation = 'FOUR';
  else if (restricted === 'STRICT') rotation = 'EIGHT';

  // entity type
  const entityType = (furniture.type || furniture.entity || 'ITEM_DISPLAY').toUpperCase();
  const isArmorStand = entityType.includes('ARMOR_STAND');

  // placements
  const placementKeys = [];
  const limitedPlacing = furniture.limited_placing;
  if (limitedPlacing) {
    if (limitedPlacing.floor)   placementKeys.push('ground');
    if (limitedPlacing.wall)    placementKeys.push('wall');
    if (limitedPlacing.roof || limitedPlacing.ceiling) placementKeys.push('ceiling');
  }
  if (placementKeys.length === 0) placementKeys.push('ground', 'wall', 'ceiling');

  // display properties
  let displayTransform = 'fixed';
  let translation = [0, 0.5, 0];
  let scale = [1, 1, 1];
  const props = furniture.properties || {};
  if (props.display_transform)  displayTransform = props.display_transform.toLowerCase();
  if (Array.isArray(props.translation) && props.translation.length >= 3) translation = props.translation;
  if (props.scale) {
    const parts = String(props.scale).split(',').map(Number);
    if (parts.length === 3 && parts.every(n => !isNaN(n))) scale = parts;
  }

  // element
  const element = isArmorStand
    ? { type: 'armor_stand', item: itemId }
    : { type: 'item_display', item: itemId };

  if (!isArmorStand) {
    if (displayTransform !== 'fixed' && displayTransform !== 'none') {
      element['display-transform'] = displayTransform;
    }
    if (translation.some(v => v !== 0)) element.translation = translation.join(',');
  }
  if (scale.some(v => v !== 1)) element.scale = scale.join(',');

  // hitboxes from barriers/shulkers (same as Nexo)
  const hitboxes = [];
  const hitboxSection = furniture.hitbox;
  if (hitboxSection) {
    const barriers = Array.isArray(hitboxSection.barriers) ? hitboxSection.barriers : [];
    for (const barrier of barriers) {
      const coords = String(barrier).split(',').map(s => parseFloat(s.trim()));
      if (coords.length === 3 && coords.every(n => !isNaN(n))) {
        hitboxes.push({ type: 'shulker', position: coords.join(',') });
      }
    }
    const shulkers = Array.isArray(hitboxSection.shulkers) ? hitboxSection.shulkers : [];
    for (const entry of shulkers) {
      const parts = String(entry).trim().split(/\s+/);
      if (parts.length < 2) continue;
      const hb = { type: 'shulker', position: parts[0] };
      const sc = parseFloat(parts[1]);
      if (!isNaN(sc) && sc !== 1) hb.scale = sc;
      hitboxes.push(hb);
    }
  }

  // assemble
  const furnitureObj = {
    settings: { item: itemId },
    loot: {
      pools: [{ rolls: 1, conditions: [{ type: 'minecraft:survives_explosion' }], entries: [{ type: 'craftengine:furniture', id: itemId }] }],
    },
    placement: {},
  };
  for (const pk of placementKeys) {
    const entry = { rules: { rotation }, elements: [element] };
    if (hitboxes.length > 0) entry.hitboxes = hitboxes;
    furnitureObj.placement[pk] = entry;
  }

  return {
    type: 'furniture_item',
    settings: { item: itemId },
    furniture: furnitureObj,
  };
}

/**
 * Convert a full Oraxen YAML config file
 * @param {string} yamlContent
 * @param {string} namespace
 * @returns {{ items: object, errors: string[] }}
 */
function convertOraxenConfig(yamlContent, namespace = DEFAULT_NAMESPACE) {
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
      const converted = convertOraxenItem(rawItemId, itemData, namespace);
      const finalId = `${namespace}:${sanitizeItemId(rawItemId)}`;
      items[finalId] = converted;
    } catch (e) {
      errors.push(`Failed to convert item '${rawItemId}': ${e.message}`);
    }
  }

  return { items, errors };
}

module.exports = { convertOraxenItem, convertOraxenConfig };
