package org.example.board;

import org.example.entity.Entity;

import java.util.Map;

public interface BoardAccessor {

    int getWidth();

    int getHeight();

    Map<Coordinate, Entity> getEntities();
}


