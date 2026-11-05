'use strict';

const yaml = require('js-yaml');
const { convertNexoConfig, convertNexoPackFile } = require('./nexo');
const { convertIAConfig } = require('./itemsadder');
const { convertOraxenConfig } = require('./oraxen');
const { sanitizeItemId } = require('./utils');

/**
 * Process an entire resourcepack ZIP for Nexo → CraftEngine
 * Returns an array of { path, content } entries to place in the output ZIP.
 */
function convertNexoPack(zipEntries, namespace = 'craftengineconverter') {
  const output = [];
  const allItems = {};
  const errors = [];

  for (const { path, content } of zipEntries) {
    const lowerPath = path.toLowerCase();

    // Item YAML configs in "items/" directory
    if ((lowerPath.endsWith('.yml') || lowerPath.endsWith('.yaml')) && lowerPath.includes('items/')) {
      try {
        const result = convertNexoConfig(content.toString('utf8'), namespace);
        Object.assign(allItems, result.items);
        errors.push(...result.errors);
      } catch (e) {
        errors.push(`Error processing ${path}: ${e.message}`);
      }
      continue;
    }

    // Pack assets — pass through with model JSON processing
    if (lowerPath.includes('assets/')) {
      const converted = convertNexoPackFile(path, content);
      // Remap into CraftEngine output structure
      output.push({
        path: 'CraftEngine/resources/' + converted.path,
        content: converted.content,
      });
      continue;
    }

    // pack.mcmeta — transform for CraftEngine
    if (path === 'pack.mcmeta') {
      try {
        const meta = JSON.parse(content.toString('utf8'));
        output.push({
          path: 'CraftEngine/resources/pack.mcmeta',
          content: Buffer.from(JSON.stringify(meta, null, 2), 'utf8'),
        });
      } catch {
        output.push({ path: 'CraftEngine/resources/pack.mcmeta', content });
      }
      continue;
    }
  }

  // Write the items config file
  if (Object.keys(allItems).length > 0) {
    const itemsYaml = yaml.dump(allItems, { lineWidth: -1, noRefs: true });
    output.push({
      path: `CraftEngine/resources/${namespace}/configuration/items/converted_items.yml`,
      content: Buffer.from(itemsYaml, 'utf8'),
    });
  }

  return { output, errors, itemCount: Object.keys(allItems).length };
}

/**
 * Process an entire resourcepack ZIP for ItemsAdder → CraftEngine
 */
function convertItemsAdderPack(zipEntries, overrideNamespace = null) {
  const output = [];
  const allItems = {};
  const errors = [];
  let detectedNamespace = overrideNamespace || 'craftengine';

  for (const { path, content } of zipEntries) {
    const lowerPath = path.toLowerCase();

    // ItemsAdder items YAML (in contents/NAMESPACE/items/*.yml)
    if ((lowerPath.endsWith('.yml') || lowerPath.endsWith('.yaml')) && (lowerPath.includes('/items/') || lowerPath.includes('contents/'))) {
      try {
        const result = convertIAConfig(content.toString('utf8'), overrideNamespace);
        if (result.namespace && result.namespace !== 'craftengine') {
          detectedNamespace = result.namespace;
        }
        Object.assign(allItems, result.items);
        errors.push(...result.errors);
      } catch (e) {
        errors.push(`Error processing ${path}: ${e.message}`);
      }
      continue;
    }

    // Pack assets (namespace/assets/namespace/...)
    // ItemsAdder puts assets at: contents/NAMESPACE/resourcepack/assets/NAMESPACE/...
    if (lowerPath.includes('/resourcepack/assets/') || lowerPath.includes('/assets/minecraft/') || lowerPath.includes('/assets/')) {
      // Extract the part after 'assets/'
      const assetsIdx = lowerPath.indexOf('/assets/');
      if (assetsIdx !== -1) {
        const relativePath = path.substring(assetsIdx + 1); // 'assets/...'
        output.push({
          path: `CraftEngine/resources/${relativePath}`,
          content,
        });
      }
      continue;
    }

    // pack.mcmeta
    if (path.endsWith('pack.mcmeta')) {
      try {
        const meta = JSON.parse(content.toString('utf8'));
        output.push({
          path: 'CraftEngine/resources/pack.mcmeta',
          content: Buffer.from(JSON.stringify(meta, null, 2), 'utf8'),
        });
      } catch {
        output.push({ path: 'CraftEngine/resources/pack.mcmeta', content });
      }
    }
  }

  if (Object.keys(allItems).length > 0) {
    const itemsYaml = yaml.dump(allItems, { lineWidth: -1, noRefs: true });
    output.push({
      path: `CraftEngine/resources/${detectedNamespace}/configuration/items/converted_items.yml`,
      content: Buffer.from(itemsYaml, 'utf8'),
    });
  }

  return { output, errors, itemCount: Object.keys(allItems).length };
}

/**
 * Process an entire resourcepack ZIP for Oraxen → CraftEngine
 */
function convertOraxenPack(zipEntries, namespace = 'craftengineconverter') {
  const output = [];
  const allItems = {};
  const errors = [];

  for (const { path, content } of zipEntries) {
    const lowerPath = path.toLowerCase();

    // Oraxen item YAMLs
    if ((lowerPath.endsWith('.yml') || lowerPath.endsWith('.yaml')) && !lowerPath.includes('assets/')) {
      try {
        const result = convertOraxenConfig(content.toString('utf8'), namespace);
        Object.assign(allItems, result.items);
        errors.push(...result.errors);
      } catch (e) {
        errors.push(`Error processing ${path}: ${e.message}`);
      }
      continue;
    }

    // Pack assets — same as Nexo structure
    if (lowerPath.includes('assets/')) {
      output.push({
        path: 'CraftEngine/resources/' + path,
        content,
      });
      continue;
    }

    if (path.endsWith('pack.mcmeta')) {
      output.push({ path: 'CraftEngine/resources/pack.mcmeta', content });
    }
  }

  if (Object.keys(allItems).length > 0) {
    const itemsYaml = yaml.dump(allItems, { lineWidth: -1, noRefs: true });
    output.push({
      path: `CraftEngine/resources/${namespace}/configuration/items/converted_items.yml`,
      content: Buffer.from(itemsYaml, 'utf8'),
    });
  }

  return { output, errors, itemCount: Object.keys(allItems).length };
}

/**
 * Main entry point for ZIP conversion.
 * @param {string} pluginType - 'nexo' | 'itemsadder' | 'oraxen'
 * @param {Array<{path: string, content: Buffer}>} zipEntries
 * @param {string} namespace  - target namespace
 * @returns {{ output: Array<{path, content}>, errors: string[], itemCount: number }}
 */
function convertPack(pluginType, zipEntries, namespace) {
  switch (pluginType.toLowerCase()) {
    case 'nexo':
      return convertNexoPack(zipEntries, namespace);
    case 'itemsadder':
      return convertItemsAdderPack(zipEntries, namespace || null);
    case 'oraxen':
      return convertOraxenPack(zipEntries, namespace);
    default:
      return { output: [], errors: [`Unknown plugin type: ${pluginType}`], itemCount: 0 };
  }
}

/**
 * Convert a raw YAML config string (no ZIP, just text)
 * Used by the text-based converter tab.
 * @param {string} pluginType
 * @param {string} yamlContent
 * @param {string} namespace
 * @returns {{ yaml: string, errors: string[], itemCount: number }}
 */
function convertConfigText(pluginType, yamlContent, namespace) {
  let result;

  switch (pluginType.toLowerCase()) {
    case 'nexo':
      result = convertNexoConfig(yamlContent, namespace || 'craftengineconverter');
      break;
    case 'itemsadder':
      result = convertIAConfig(yamlContent, namespace || null);
      break;
    case 'oraxen':
      result = convertOraxenConfig(yamlContent, namespace || 'craftengineconverter');
      break;
    default:
      return { yaml: '', errors: [`Unknown plugin type: ${pluginType}`], itemCount: 0 };
  }

  const outputYaml = yaml.dump(result.items, {
    lineWidth: -1,
    noRefs: true,
    indent: 2,
  });

  return {
    yaml: outputYaml,
    errors: result.errors,
    itemCount: Object.keys(result.items).length,
  };
}

module.exports = { convertPack, convertConfigText };
