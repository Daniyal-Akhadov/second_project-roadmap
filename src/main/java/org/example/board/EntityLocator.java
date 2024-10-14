package org.example.board;

import org.example.entity.Entity;

public interface EntityLocator {
    Entity getEntityBy(Coordinate coordinate);

    void removeEntityBy(Coordinate coordinate);
}
