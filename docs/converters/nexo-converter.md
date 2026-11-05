# Nexo Converter

This section provides instructions on how to convert configuration files from the **Nexo** plugin into the **CraftEngine** format using the **CraftEngineConverter** plugin.

## 📋 Prerequisites

Before proceeding with the conversion, ensure that you have the following prerequisites met:
- **CraftEngineConverter** plugin installed on your Minecraft server. Refer to the [🚀 Quick Start](../getting-started/quick-start.md) guide for installation instructions.
- **Nexo** plugin installed on your Minecraft server (Recommended to convert existing blocks/furniture).
- **CraftEngine** plugin installed on your Minecraft server.

## Command

To convert Nexo configuration files, use the following command in your Minecraft server console or in-game chat (if you have the necessary permissions):

```bash
/ceconverter convert nexo
```

This command will initiate the conversion process for Nexo configuration files.

## Converted Files Location

After the conversion is complete, the converted files will be saved in the following directory within your server:`plugins/CraftEngineConverter/converted/Nexo/`

## Upload Converted Files to CraftEngine

For this you need to go to `plugins/CraftEngine/converted/Nexo/` and copy all the files from there to your plugin's folder. 
Or you can go to `plugins/CraftEngineConverter/converted/Nexo/CraftEngine/resources/craftengineconverter` and copy the `configuration` and `resourcepack` folders to your `plugins/CraftEngine/resources/xxx` folder where `xxx` is a name you choose to organize your CraftEngine resources.

## Usage

If you want to convert in one click nexo blocks or furniture when you interact with them in-game, make sure to enable the following options in the `config.yml` file of **CraftEngineConverter**:

```yaml
nexo:
  enable-hook: true # Enable Nexo integration if Nexo is installed
  # Require this permission to allow Nexo block interaction conversion : craftengineconverter.nexo.block.interact.conversion
  enable-block-interaction-conversion: true # When enabled, when you interact with a nexo block, it will convert the nexo block to their CE equivalent
  # Require this permission to allow Nexo furniture interaction conversion : craftengineconverter.nexo.furniture.interact.conversion
  enable-furniture-interaction-conversion: true # When enabled, when you interact with a nexo furniture, it will convert the furniture to their CE equivalent
```

Make sure that **Nexo** plugin is installed on your server for these features to work. 

## Additional Information

For more details on configuring **CraftEngineConverter**, please refer to the [🔧 Main Configuration](../configuration/main-config.md) page.