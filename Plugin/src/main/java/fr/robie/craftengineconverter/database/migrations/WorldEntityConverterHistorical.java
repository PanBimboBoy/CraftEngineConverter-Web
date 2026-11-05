package fr.robie.craftengineconverter.database.migrations;

import fr.maxlego08.sarah.database.Migration;
import fr.robie.craftengineconverter.api.history.EntityHistory;

public class WorldEntityConverterHistorical extends Migration {
    @Override
    public void up() {
        this.create("world_entity_converter_historical", EntityHistory.class);
    }
}
