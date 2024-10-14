package org.example.entity.nonstatic;

import org.example.entity.Entity;

public final class Grass extends Entity {
    private final int increaseHealth;
    private boolean isEaten;

    public Grass(final int increaseHealth) {
        this.increaseHealth = increaseHealth;
        this.isEaten = false;
    }

    public void markAsEaten() {
        isEaten = true;
    }

    public boolean isEaten() {
        return isEaten;
    }

    public int getIncreaseHealth() {
        return increaseHealth;
    }
}

