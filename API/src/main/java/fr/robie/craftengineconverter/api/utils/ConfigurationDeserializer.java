package fr.robie.craftengineconverter.api.utils;

import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

@FunctionalInterface
public interface ConfigurationDeserializer<T> {
    T deserialize(@NotNull Object rawValue, @NotNull Supplier<T> defaultValueSupplier);
}