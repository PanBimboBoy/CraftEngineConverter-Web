package fr.robie.craftengineconverter.api.configuration.item.models.select;

public class ChargeTypeSelectConfiguration extends SelectModelConfiguration<ChargeTypeSelectConfiguration.ChargeType> {

    public ChargeTypeSelectConfiguration() {
        super("minecraft:charge_type");
    }

    public enum ChargeType {
        NONE,
        ROCKET,
        ARROW
    }
}