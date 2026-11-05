package fr.robie.craftengineconverter.common.utils;

public record CacheConversion(String fileName, int offset, ThrowingTriConsumer<String, Integer> converter) {
}
