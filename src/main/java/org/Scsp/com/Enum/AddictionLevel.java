package org.Scsp.com.Enum;

public enum AddictionLevel {
    LOW,
    MEDIUM,
    HIGH,
    EXTREME;

    public static AddictionLevel fromString(String level) {
        switch (level.toUpperCase()) {
            case "LOW":
                return LOW;
            case "MEDIUM":
                return MEDIUM;
            case "HIGH":
                return HIGH;
            case "EXTREME":
                return EXTREME;
            default:
                throw new IllegalArgumentException("Unknown addiction level: " + level);
        }
    }
}
