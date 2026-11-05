# 🛠️ Main Config (`config.yml`) Guide

This guide provides a detailed overview of the main configuration file (`config.yml`) for **CraftEngineConverter**. Here, you'll find explanations for each configurable option, allowing you to customize the plugin to suit your server's needs.

## 📁 Configuration File Location
The `config.yml` file is located in the `plugins/CraftEngineConverter/` directory of your Minecraft server.

## ⚙️ Configuration Options

```yaml
enable-debug: false

# Language settings (available languages: EN, FR)
language: "EN"

# Each block has a usage limit for a block-state. This setting controls how those limits are enforced when multiple plugins are installed:
#  - "plugin": Each plugin gets its own separate limit. Different plugins can use the same block-states independently without affecting each other.
#  - "global": All plugins share the same limit. Once any plugin uses up the limit for a block-state, other plugins cannot use it (prevents conflicts and duplicates).
# 
# Choose "global" to prevent multiple plugins from using the same block-states; otherwise choose "plugin" to allow each plugin its own allocation.
#
# Note:
#  - Block-states can have parent-child relationships.
#  - A parent's limit automatically includes the limits of all its children.
#    For example, if "note-block" (a child of "solid") has a limit of 10, then "solid" will have its own limit plus 10.
#
# Available block-state groups and their capacity:
#  - SOLID (1338 available): Top-level group
#      - MUSHROOM (189 available): Mushroom block variants
#          - RED_MUSHROOM_BLOCK (63 available)
#          - BROWN_MUSHROOM_BLOCK (63 available)
#          - MUSHROOM_STEM (63 available)
#      - NOTE_BLOCK (1149 available)
#
#  - TRIPWIRE (126 available): Tripwire variants
#      - LOWER_TRIPWIRE (63 available)
#      - HIGHER_TRIPWIRE (63 available)
#
#  - TINTABLE_LEAVES (65 available)
#      - WATERLOGGED_TINTABLE_LEAVES (65 available)
#
#  - NON_TINTABLE_LEAVES (234 available)
#      - WATERLOGGED_NON_TINTABLE_LEAVES (234 available)
#
#  - LEAVES (143 available)
#      - WATERLOGGED_LEAVES (143 available)
#
#  - Independent blocks (not part of any shared group):
#      - CACTUS (15), SUGAR_CANE (15)
#      - WEEPING_VINE (50), TWISTING_VINE (50), CAVE_VINE (100)
#      - SAPLING (8), KELP (25), CHORUS (0)
block-state-limit:
  type: PLUGIN
  tintable-leaves:
    start-limit: 0
  waterlogged-tintable-leaves:
    start-limit: 0
  non-tintable-leaves:
    start-limit: 0
  waterlogged-non-tintable-leaves:
    start-limit: 0
  leaves:
    start-limit: 0
  waterlogged-leaves:
    start-limit: 0
  lower-tripwire:
    start-limit: 0
  higher-tripwire:
    start-limit: 0
  tripwire:
    start-limit: 0
  mushroom-stem:
    start-limit: 0
  red-mushroom-block:
    start-limit: 0
  brown-mushroom-block:
    start-limit: 0
  mushroom:
    start-limit: 0
  note-block:
    start-limit: 0
  solid:
    start-limit: 0
  cactus:
    start-limit: 0
  sugar-cane:
    start-limit: 0
  weeping-vine:
    start-limit: 0
  twisting-vine:
    start-limit: 0
  cave-vine:
    start-limit: 0
  sapling:
    start-limit: 0
  kelp:
    start-limit: 0
  chorus:
    start-limit: 0

# Auto-convert items on plugin enable
auto-convert-on-startup: false

# Select which converters and options should be used on startup
# If empty, all options for all converters will be used
# Available options: ITEMS, EMOJIS, IMAGES, LANGUAGES, SOUNDS, RECIPES, PACKS, ALL
# auto-convert-on-startup-types:
#   nexo:
#     - ITEMS
#     - EMOJIS
#   itemsadder:
#     - ITEMS
#     - IMAGES

# Default material for items
default-material: "PAPER"

# Disable italic formatting in item lore by adding <!i> tag
disable-default-italic: true

# Armor converter settings
# Available types: COMPONENT, TRIM , BOTH
armor-converter-type: "COMPONENT"

blacklisted-paths:
  - "shaders/*" # Blacklist all files in shaders folder
  # - "shaders/rendertype_text.fsh" # Example of how to blacklist a specific file
  # - "minecraft:textures/shaders/" # Example of how to blacklist the entire shaders namespace

formatting:
  packet-events: true
  boss-bar: true
  action-bar: true
  plugin-message: true
  title: true
  menu-title: true

tag:
  nexo-glyph: # <glyph:old_name>
    enabled: true
  itemsadder-image: # :old_name:
    enabled: true
  placeholder-api: # <placeholderapi:placeholder> or <papi:placeholder>
    enabled: true

allow-block-conversion-propagation: true # When enabled, if a block is converted, the surrounding blocks will also be checked for conversion
max-block-conversion-propagation-depth: 64 # Maximum number of blocks to propagate conversion in one block interaction event

nexo:
  enable-hook: true # Enable Nexo integration if Nexo is installed
  # Require this permission to allow Nexo block interaction conversion : craftengineconverter.nexo.block.interact.conversion
  enable-block-interaction-conversion: true # When enabled, when you interact with a nexo block, it will convert the nexo block to their CE equivalent
  # Require this permission to allow Nexo furniture interaction conversion : craftengineconverter.nexo.furniture.interact.conversion
  enable-furniture-interaction-conversion: true # When enabled, when you interact with a nexo furniture, it will convert the furniture to their CE equivalent
  enable-chunk-load-conversion: false # When enabled, when a chunk is loaded, it will convert all Nexo blocks and furnitures to CE equivalents

itemsadder:
  enable-hook: true # Enable ItemsAdder integration if ItemsAdder is installed
  img-placeholderapi-support: true # Register a placeholder %img:old_name% to get the new image
  # Require this permission to allow ItemsAdder block interaction conversion : craftengineconverter.itemsadder.block.interact.conversion
  enable-block-interaction-conversion: true # When enabled, when you interact with an ItemsAdder block, it will convert the block to their CE equivalent
  # Require this permission to allow ItemsAdder furniture interaction conversion : craftengineconverter.itemsadder.furniture.interact.conversion
  enable-furniture-interaction-conversion: true # When enabled, when you interact with an ItemsAdder furniture, it will convert the furniture to their CE equivalent
  enable-chunk-load-conversion: false # When enabled, when a chunk is loaded, it will convert all ItemsAdder blocks and furnitures to CE equivalents
  blacklisted-content-folders-namespaces: [] # List of content folders or namespaces to ignore during conversion

world-converter:
  enable: false # Enable world conversion feature (Requires server restart to take effect)
  nexo:
    enable: true # Enable Nexo world conversion without nexo plugin installed ! Just explore and let the lugin convert Nexo blocks and furnitures to CE equivalents
  itemsadder:
    enable: true # Enable ItemsAdder world conversion without ItemsAdder plugin installed ! Just explore and let the lugin convert ItemsAdder blocks and furnitures to CE equivalents
  progress-bar-options:
    progress-color: GREEN
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50

# Available colors:
# BLACK, DARK_BLUE, DARK_GREEN, DARK_AQUA, DARK_RED, DARK_PURPLE,
# GOLD, GRAY, DARK_GRAY, BLUE, GREEN, AQUA, RED, LIGHT_PURPLE, YELLOW, WHITE
progress-bar-options:
  items:
    progress-color: GREEN
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
  emojis:
    progress-color: AQUA
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
  images:
    progress-color: GOLD
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
  languages:
    progress-color: BLUE
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
  sounds:
    progress-color: LIGHT_PURPLE
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
  packs:
    progress-color: RED
    empty-color: DARK_GRAY
    percent-color: YELLOW
    progress-char: █
    empty-char: ░
    bar-width: 50
```