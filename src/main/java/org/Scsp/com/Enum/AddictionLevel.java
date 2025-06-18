package org.Scsp.com.Enum;

public enum AddictionLevel {
    LOW,
    MEDIUM,
    HIGH,
    NONE;

    public static AddictionLevel fromString(String level) {
        switch (level.toUpperCase()) {
            case "LOW":
                return LOW;
            case "MEDIUM":
                return MEDIUM;
            case "HIGH":
                return HIGH;
            case "NONE":
                return NONE;
            default:
                throw new IllegalArgumentException("Unknown addiction level: " + level);
        }
    }
}
