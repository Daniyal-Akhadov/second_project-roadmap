package org.example.simulation;

public final class SimulationConfig {
    public static final int HEIGHT = 20;
    public static final int WIDTH = 20;
    public static final float STATIC_ENTITY_RATIO = 0.2f;
    public static final float GRASS_RATIO = 0.1f;
    public static final float HERBIVORE_RATIO = 0.025f;
    public static final float PREDATOR_RATIO = 0.0167f;

    static int getPredatorCount(final int size) {
        return (int) Math.ceil(size * PREDATOR_RATIO);
    }

    static int getHerbivoreCount(final int size) {
        return (int) Math.ceil(size * HERBIVORE_RATIO);
    }

    static int getGrassCount(final int size) {
        return (int) Math.ceil(size * GRASS_RATIO);
    }

    static int getStaticEntityCount(final int size) {
        return (int) Math.ceil(size * STATIC_ENTITY_RATIO);
    }

    static int getSize() {
        return HEIGHT * WIDTH;
    }
}
