package fr.robie.craftengineconverter.api.configuration.item.models.select;

public class MainHandSelectConfiguration extends SelectModelConfiguration<MainHandSelectConfiguration.MainHand> {

    public MainHandSelectConfiguration() {
        super("minecraft:main_hand");
    }

    public enum MainHand {
        LEFT,
        RIGHT
    }
}
