package org.example.entity;

public abstract class EntityType {
    public static final String STATIC = "STATIC";

    public static class StaticEntityType {
        public static final String ROCK = "ROCK";
        public static final String TREE = "TREE";
    }

    public static final String HERBIVORE = "HERBIVORE";
    public static final String GRASS = "GRASS";
    public static final String PREDATOR = "PREDATOR";

    public static boolean isValidated(final String entityType) {
        return switch (entityType) {
            case STATIC, PREDATOR, HERBIVORE, GRASS -> true;
            default -> throw new IllegalStateException("Unexpected value: " + entityType);
        };
    }
}
