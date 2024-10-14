package org.example.entity.nonstatic;

import org.example.board.Coordinate;
import org.example.board.EntityLocator;
import org.example.entity.Entity;
import org.example.path.PathSearchAlgorithm;

import java.util.ArrayList;
import java.util.List;

public final class Herbivore extends Creature {
    public Herbivore(final int speed, final Health health, final Coordinate coordinate, final PathSearchAlgorithm pathSearchAlgorithm, final EntityLocator board) {
        super(speed, health, coordinate, pathSearchAlgorithm, board);
    }

    @Override
    protected boolean canEatAt(final Coordinate nextCoordinate) {
        return board.getEntityBy(nextCoordinate) instanceof Grass;
    }

    @Override
    protected void eat(final Entity entity) {
        if (entity instanceof final Grass grass && !grass.isEaten()) {
            addHealth(grass.getIncreaseHealth());
            grass.markAsEaten();
        }
    }

    @Override
    protected List<Coordinate> setupPathToFood() {
        final List<Class<? extends Entity>> bounds = new ArrayList<>();
        bounds.add(Predator.class);
        bounds.add(Herbivore.class);

        return findPathToGoalEntity(Grass.class.getSimpleName(), bounds);
    }
}
