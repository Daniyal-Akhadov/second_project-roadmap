package org.example.actions;

import org.example.board.Coordinate;
import org.example.board.EntityPositionUpdater;
import org.example.entity.EntityFactoryService;
import org.example.entity.nonstatic.Creature;

import java.util.Set;

public final class MoveCreatures implements Action {
    private final EntityFactoryService entityFactoryService;
    private final EntityPositionUpdater positionUpdater;

    public MoveCreatures(final EntityFactoryService entityFactoryService, final EntityPositionUpdater positionUpdater) {
        this.entityFactoryService = entityFactoryService;
        this.positionUpdater = positionUpdater;
    }

    @Override
    public void execute() {
        entityFactoryService.removeDeadCreatures();
        final Set<Creature> creatures = entityFactoryService.getAliveCreatures();

        for (final Creature creature : creatures) {
            final Coordinate oldCoordinate = creature.getCoordinate();
            final Coordinate newCoordinate = creature.makeStep();
            positionUpdater.updateEntityPosition(oldCoordinate, newCoordinate, creature);
        }
    }
}

