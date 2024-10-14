package org.example.board;

import org.example.entity.Entity;

public interface EntityPositionUpdater {
    void updateEntityPosition(Coordinate oldCoordinate, Coordinate newCoordinate, Entity entity);
}
