package fr.robie.craftengineconverter.database.migrations;

import fr.maxlego08.sarah.database.Migration;
import fr.robie.craftengineconverter.api.history.BlockHistory;

public class WorldBlockConverterHistorical extends Migration {
    @Override
    public void up() {
        this.create("world_block_converter_historical", BlockHistory.class);
    }

}
