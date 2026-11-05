package fr.robie.craftengineconverter.common.utils;

@FunctionalInterface
public interface ThrowingTriConsumer<A, B> {
    void accept(A a, B b) throws IllegalArgumentException;
}