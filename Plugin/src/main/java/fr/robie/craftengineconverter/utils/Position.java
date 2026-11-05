package fr.robie.craftengineconverter.utils;

import org.jetbrains.annotations.NotNull;

public record Position(float x, float y, float z) {
    @Override
    public @NotNull String toString() {
        return this.x + "," + this.y + "," + this.z;
    }
}
