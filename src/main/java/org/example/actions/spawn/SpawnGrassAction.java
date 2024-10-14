package org.example.actions.spawn;

import org.example.actions.Action;
import org.example.board.EntitySpawner;
import org.example.entity.EntityType;

public class SpawnGrassAction implements Action {
    private final int grassAmount;
    private final EntitySpawner entitySpawner;

    public SpawnGrassAction(final int grassAmount, final EntitySpawner entitySpawner) {
        this.grassAmount = grassAmount;
        this.entitySpawner = entitySpawner;
    }

    @Override
    public void execute() {
        entitySpawner.spawnEntityByType(EntityType.GRASS, grassAmount);
    }
}
