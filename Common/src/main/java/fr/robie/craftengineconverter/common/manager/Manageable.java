package fr.robie.craftengineconverter.common.manager;

public interface Manageable {
    default void initialize() {
    }

    default void reload() {
        this.unload();
        this.load();
    }

    default void unload() {
    }

    default void load() {
    }

    default void disable() {
    }
}
