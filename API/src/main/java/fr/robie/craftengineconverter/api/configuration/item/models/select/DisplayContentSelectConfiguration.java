package fr.robie.craftengineconverter.api.configuration.item.models.select;

public class DisplayContentSelectConfiguration extends SelectModelConfiguration<DisplayContentSelectConfiguration.DisplayContent>{

    public DisplayContentSelectConfiguration() {
        super("minecraft:display_context");
    }

    public enum DisplayContent {
        NONE,
        THIRDPERSON_LEFTHAND,
        THIRDPERSON_RIGHTHAND,
        FIRSTPERSON_LEFTHAND,
        FIRSTPERSON_RIGHTHAND,
        HEAD,
        GUI,
        GROUND,
        FIXED,
        ON_SHELF
    }
}
