package org.example.actions.spawn;

import org.example.actions.Action;
import org.example.board.EntitySpawner;
import org.example.entity.EntityFactoryService;
import org.example.entity.EntityType;

public final class SpawnHerbivore implements Action {
    private final int LAG_LEVEL = 1;

    private final EntitySpawner entitySpawner;
    private final EntityFactoryService entityFactory;

    public SpawnHerbivore(final EntitySpawner entitySpawner, final EntityFactoryService entityFactory) {
        this.entitySpawner = entitySpawner;
        this.entityFactory = entityFactory;
    }

    @Override
    public void execute() {
        if (isLag()) {
            entitySpawner.spawnEntityByType(EntityType.HERBIVORE, difference());
        }
    }

    private boolean isLag() {
        return difference() > LAG_LEVEL;
    }

    private int difference() {
        return entityFactory.predatorCount() - entityFactory.herbivoreCount();
    }
}
