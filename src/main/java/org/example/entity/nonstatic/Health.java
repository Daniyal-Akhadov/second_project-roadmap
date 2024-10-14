package org.example.entity.nonstatic;

public final class Health {
    private int max;
    private int current;

    private boolean isAlive;

    public Health(final int max) {
        initialize(max);
        isAlive = true;
    }

    private void initialize(final int max) {
        this.max = max;
        this.current = this.max;
    }

    public void takeDamage(final int damage) {
        if (damage <= 0) {
            return;
        }

        current -= damage;

        if (current <= 0) {
            die();
        }
    }

    public void add(final int value) {
        if (value <= 0) {
            return;
        }

        current += value;
        current %= max;
    }

    public boolean isAlive() {
        return isAlive;
    }

    private void die() {
        isAlive = false;
    }

    @Override
    public String toString() {
        return "Health{" +
                "current=" + current +
                '}';
    }
}
