package org.example.entity.nonstatic;

import org.example.board.Coordinate;
import org.example.board.EntityLocator;
import org.example.entity.Entity;
import org.example.entity.static_entity.Rock;
import org.example.entity.static_entity.Tree;
import org.example.path.PathSearchAlgorithm;

import java.util.ArrayList;
import java.util.List;

public abstract class Creature extends Entity {
    protected final EntityLocator board;

    private final int speed;
    private final Health health;
    private final PathSearchAlgorithm breadthFirstSearch;
    private Coordinate coordinate;
    private boolean isEating;

    protected Creature(final int speed, final Health health, final Coordinate coordinate, final PathSearchAlgorithm pathSearchAlgorithm, final EntityLocator board) {
        this.speed = speed;
        this.health = health;
        this.coordinate = coordinate;
        this.breadthFirstSearch = pathSearchAlgorithm;
        this.board = board;
    }

    public final Coordinate makeStep() {
        if (isAlive() != true) {
            return handleDeath();
        }

        if (isEating) {
            finishEating();
            return getCoordinate();
        }

        final List<Coordinate> path = setupPathToFood();

        if (path.isEmpty()) {
            return getCoordinate();
        }

        final Coordinate nextCoordinate = move(path.getFirst());
        tryEatAt(nextCoordinate);
        return nextCoordinate;
    }

    public Coordinate getCoordinate() {
        return new Coordinate(coordinate.getX(), coordinate.getY());
    }

    public boolean isAlive() {
        return health.isAlive();
    }

    protected abstract List<Coordinate> setupPathToFood();

    protected abstract void eat(final Entity entity);

    protected abstract boolean canEatAt(final Coordinate nextCoordinate);

    protected final List<Coordinate> findPathToGoalEntity(final String goalEntity, final List<Class<? extends Entity>> bounds) {
        final Class<? extends Entity>[] allBounds = prepareBounds(bounds);

        final List<Coordinate> path = breadthFirstSearch.searchPath(
                coordinate,
                goalEntity,
                allBounds
        );

        final List<Coordinate> adjustedPath = breadthFirstSearch.getSpeedAdjustedPath(path, speed);
        breadthFirstSearch.removeCurrentCoordinateFromPath(adjustedPath);
        return adjustedPath;
    }

    protected void addHealth(final int value) {
        health.add(value);
    }

    private void tryEatAt(final Coordinate coordinate) {
        if (canEatAt(coordinate)) {
            eat(board.getEntityBy(coordinate));
            isEating = true;
        }
    }

    private Coordinate move(final Coordinate nextCoordinate) {
        setupNextCoordinate(nextCoordinate);
        return nextCoordinate;
    }

    private Coordinate handleDeath() {
        final Coordinate currentCoordinate = getCoordinate();
        board.removeEntityBy(currentCoordinate);
        return currentCoordinate;
    }

    private Class<? extends Entity>[] prepareBounds(final List<Class<? extends Entity>> additionalBounds) {
        final List<Class<? extends Entity>> allBounds = new ArrayList<>();
        allBounds.add(Tree.class);
        allBounds.add(Rock.class);
        allBounds.addAll(additionalBounds);

        return allBounds.toArray(new Class[0]);
    }

    private void setupNextCoordinate(final Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    protected void takeDamage(final int damage) {
        health.takeDamage(damage);
    }

    private void finishEating() {
        isEating = false;
    }
}
