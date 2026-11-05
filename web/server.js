'use strict';

const express = require('express');
const multer = require('multer');
const JSZip = require('jszip');
const cors = require('cors');
const path = require('path');
const { v4: uuidv4 } = require('uuid');
const { convertPack, convertConfigText } = require('./converters/packConverter');

const app = express();
const PORT = process.env.PORT || 3000;

// ─── Middleware ───────────────────────────────────────────────────────────────
app.use(cors());
app.use(express.json({ limit: '1mb' }));
app.use(express.static(path.join(__dirname, 'public')));

// Multer: memory storage, no file size limit
const upload = multer({
  storage: multer.memoryStorage(),
  // No limits — user asked for unlimited upload size
});

// ─── Routes ──────────────────────────────────────────────────────────────────

/**
 * POST /api/convert/pack
 * Converts an uploaded .zip resourcepack
 * Form data: file (zip), pluginType (nexo|itemsadder|oraxen), namespace (optional)
 */
app.post('/api/convert/pack', upload.single('file'), async (req, res) => {
  try {
    if (!req.file) {
      return res.status(400).json({ error: 'No file uploaded.' });
    }

    const pluginType = (req.body.pluginType || 'nexo').toLowerCase();
    const namespace = (req.body.namespace || '').trim() || undefined;

    if (!['nexo', 'itemsadder', 'oraxen'].includes(pluginType)) {
      return res.status(400).json({ error: `Invalid plugin type: ${pluginType}` });
    }

    // Parse uploaded ZIP
    const inputZip = await JSZip.loadAsync(req.file.buffer);
    const zipEntries = [];

    for (const [filePath, fileObj] of Object.entries(inputZip.files)) {
      if (fileObj.dir) continue;
      const content = await fileObj.async('nodebuffer');
      zipEntries.push({ path: filePath, content });
    }

    if (zipEntries.length === 0) {
      return res.status(400).json({ error: 'ZIP file is empty or has no files.' });
    }

    // Run conversion
    const { output, errors, itemCount } = convertPack(pluginType, zipEntries, namespace);

    if (output.length === 0 && errors.length > 0) {
      return res.status(422).json({ error: 'Conversion failed.', details: errors });
    }

    // Build output ZIP
    const outputZip = new JSZip();
    for (const { path: outPath, content } of output) {
      outputZip.file(outPath, content);
    }

    // Add a conversion report
    const report = [
      `CraftEngine Converter - Conversion Report`,
      `==========================================`,
      `Plugin:     ${pluginType}`,
      `Namespace:  ${namespace || 'auto-detected'}`,
      `Items:      ${itemCount}`,
      `Files:      ${output.length}`,
      `Errors:     ${errors.length}`,
      ``,
      errors.length > 0 ? `Errors:\n${errors.map(e => '  - ' + e).join('\n')}` : 'No errors.',
    ].join('\n');

    outputZip.file('conversion_report.txt', report);

    const outputBuffer = await outputZip.generateAsync({
      type: 'nodebuffer',
      compression: 'DEFLATE',
      compressionOptions: { level: 6 },
    });

    const filename = `craftengine_${pluginType}_${Date.now()}.zip`;

    res.set({
      'Content-Type': 'application/zip',
      'Content-Disposition': `attachment; filename="${filename}"`,
      'X-Conversion-Items': String(itemCount),
      'X-Conversion-Errors': String(errors.length),
      'X-Conversion-Files': String(output.length),
    });

    return res.send(outputBuffer);
  } catch (err) {
    console.error('Pack conversion error:', err);
    return res.status(500).json({ error: 'Internal server error.', details: err.message });
  }
});

/**
 * POST /api/convert/config
 * Converts a YAML config string (no ZIP)
 * Body: { pluginType, yamlContent, namespace }
 */
app.post('/api/convert/config', async (req, res) => {
  try {
    const { pluginType, yamlContent, namespace } = req.body;

    if (!pluginType || !yamlContent) {
      return res.status(400).json({ error: 'pluginType and yamlContent are required.' });
    }

    if (!['nexo', 'itemsadder', 'oraxen'].includes(pluginType.toLowerCase())) {
      return res.status(400).json({ error: `Invalid plugin type: ${pluginType}` });
    }

    const result = convertConfigText(pluginType.toLowerCase(), yamlContent, namespace);

    return res.json({
      yaml: result.yaml,
      errors: result.errors,
      itemCount: result.itemCount,
    });
  } catch (err) {
    console.error('Config conversion error:', err);
    return res.status(500).json({ error: 'Internal server error.', details: err.message });
  }
});

/**
 * GET /api/health
 */
app.get('/api/health', (_req, res) => {
  res.json({ status: 'ok', version: '1.0.0' });
});

// ─── Start ────────────────────────────────────────────────────────────────────
app.listen(PORT, () => {
  console.log(`CraftEngine Converter Web UI running at http://localhost:${PORT}`);
});
