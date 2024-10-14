package org.example.board;

import org.example.entity.Entity;
import org.example.entity.EntityFactoryService;
import org.example.entity.EntityType;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class Board implements BoardService, EntityPositionUpdater, EntitySpawner {
    private final int height;
    private final int width;
    private final EntityFactoryService entityFactoryService;
    private final Map<Coordinate, Entity> map;

    private Board(final Builder builder, final EntityFactoryService entityFactoryService) {
        this.height = builder.height;
        this.width = builder.width;
        this.map = new HashMap<>();
        this.entityFactoryService = entityFactoryService;
        setupDefaultEntityPositions(builder.entities);
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public Map<Coordinate, Entity> getEntities() {
        return new HashMap<>(map);
    }

    @Override
    public void removeEntityBy(final Coordinate coordinate) {
        if (coordinate == null) {
            return;
        }

        map.remove(coordinate);
    }

    @Override
    public Entity getEntityBy(final Coordinate coordinate) {
        return map.get(coordinate);
    }

    @Override
    public void updateEntityPosition(final Coordinate oldCoordinate, final Coordinate newCoordinate, final Entity entity) {
        final Entity entityOnCoordinate = map.get(oldCoordinate);

        if (entityOnCoordinate == entity) {
            map.remove(oldCoordinate);
        }

        map.put(newCoordinate, entity);
    }

    @Override
    public void spawnEntityByType(final String type, final int count) {
        EntityType.isValidated(type);

        for (int i = 0; i < count; i++) {
            final Coordinate coordinate = getFreeCoordinate();
            final Entity entity = entityFactoryService.createEntity(type, coordinate, this);
            map.put(coordinate, entity);
        }
    }

    private Coordinate getFreeCoordinate() {
        final Random random = new Random();
        Coordinate coordinate = new Coordinate(random.nextInt(height), random.nextInt(width));

        while (map.containsKey(coordinate)) {
            coordinate = new Coordinate(random.nextInt(width), random.nextInt(height));
        }

        return coordinate;
    }

    private void setupDefaultEntityPositions(final Map<String, Integer> entities) {
        for (final Map.Entry<String, Integer> entry : entities.entrySet()) {
            spawnEntityByType(entry.getKey(), entry.getValue());
        }
    }

    public final static class Builder {
        private final int height;
        private final int width;
        private final EntityFactoryService entityFactoryService;
        private final Map<String, Integer> entities;

        public Builder(final int height, final int width, final EntityFactoryService entityFactoryService) {
            this.height = height;
            this.width = width;
            this.entityFactoryService = entityFactoryService;
            this.entities = new HashMap<>();
        }

        public Builder addEntityCount(final String type, final int count) {
            EntityType.isValidated(type);
            entities.put(type, count);
            return this;
        }

        public Board build() {
            return new Board(this, entityFactoryService);
        }
    }
}

