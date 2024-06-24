package net.minestom.server.utils;

import org.jetbrains.annotations.NotNull;

/**
 * Represents the base for any data type that is numeric.
 *
 * @param <T> The type numeric of the range object.
 */
public sealed interface Range<T extends Number> {

    record Byte(byte minimum,
                byte maximum) implements Range<java.lang.Byte> {
        @Override
        public boolean isInRange(java.lang.@NotNull Byte value) {
            return value >= minimum && value <= maximum;
        }
    }

    record Short(short minimum,
                 short maximum) implements Range<java.lang.Short> {
        @Override
        public boolean isInRange(java.lang.@NotNull Short value) {
            return value >= minimum && value <= maximum;
        }
    }

    record Integer(int minimum,
                   int maximum) implements Range<java.lang.Integer> {
        @Override
        public boolean isInRange(java.lang.@NotNull Integer value) {
            return value >= minimum && value <= maximum;
        }
    }

    record Long(long minimum,
                long maximum) implements Range<java.lang.Long> {
        @Override
        public boolean isInRange(java.lang.@NotNull Long value) {
            return value >= minimum && value <= maximum;
        }
    }

    record Float(float minimum,
                 float maximum) implements Range<java.lang.Float> {
        @Override
        public boolean isInRange(java.lang.@NotNull Float value) {
            return value >= minimum && value <= maximum;
        }
    }

    record Double(double minimum,
                  double maximum) implements Range<java.lang.Double> {
        @Override
        public boolean isInRange(java.lang.@NotNull Double value) {
            return value >= minimum && value <= maximum;
        }
    }

    /**
     * Whether the given {@code value} is in range of the minimum and the maximum.
     *
     * @param value The value to be checked.
     * @return {@code true} if the value in the range of {@code minimum} and {@code maximum},
     * otherwise {@code false}.
     */
    boolean isInRange(@NotNull T value);
}
