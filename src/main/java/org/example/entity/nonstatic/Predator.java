package org.example.entity.nonstatic;

import org.example.board.Coordinate;
import org.example.board.EntityLocator;
import org.example.entity.Entity;
import org.example.path.PathSearchAlgorithm;

import java.util.ArrayList;
import java.util.List;

public final class Predator extends Creature {
    private final int damage;

    public Predator(final int speed, final Health health, final Coordinate coordinate, final PathSearchAlgorithm pathSearchAlgorithm, final EntityLocator entityLocator, final int damage) {
        super(speed, health, coordinate, pathSearchAlgorithm, entityLocator);
        this.damage = damage;
    }

    @Override
    protected boolean canEatAt(final Coordinate nextCoordinate) {
        return board.getEntityBy(nextCoordinate) instanceof Herbivore;
    }

    @Override
    protected void eat(final Entity entity) {
        if (entity instanceof final Herbivore herbivore) {
            herbivore.takeDamage(damage);
        }
    }

    @Override
    protected List<Coordinate> setupPathToFood() {
        final List<Class<? extends Entity>> bounds = new ArrayList<>();
        bounds.add(Grass.class);
        bounds.add(Predator.class);

        return findPathToGoalEntity(Herbivore.class.getSimpleName(), bounds);
    }
}

