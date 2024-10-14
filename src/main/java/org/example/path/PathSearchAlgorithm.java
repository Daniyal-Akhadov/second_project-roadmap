package org.example.path;

import org.example.board.Coordinate;
import org.example.entity.Entity;

import java.util.List;

public interface PathSearchAlgorithm {
    void removeCurrentCoordinateFromPath(List<Coordinate> path);

    List<Coordinate> getSpeedAdjustedPath(List<Coordinate> path, int speed);

    List<Coordinate> searchPath(Coordinate start, String goalEntity, Class<? extends Entity>... bounds);
}
