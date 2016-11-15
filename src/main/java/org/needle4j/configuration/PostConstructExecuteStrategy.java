package org.needle4j.configuration;

public enum PostConstructExecuteStrategy {
    ALWAYS,
    NEVER,
    DEFAULT;

    public static PostConstructExecuteStrategy fromString(final String value) {
        for (final PostConstructExecuteStrategy v : PostConstructExecuteStrategy.values()) {
            if (v.name().equalsIgnoreCase(value)) {
                return v;
            }
        }
        return DEFAULT;
    }
}
