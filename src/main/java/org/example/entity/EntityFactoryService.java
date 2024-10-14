package org.example.entity;

import org.example.board.BoardService;
import org.example.board.Coordinate;
import org.example.entity.nonstatic.Creature;

import java.util.Set;

public interface EntityFactoryService {
    Entity createEntity(String type, Coordinate coordinate, BoardService board);

    boolean hasHerbivore();

    void removeDeadCreatures();

    Set<Creature> getDeadCreatures();

    Set<Creature> getAliveCreatures();

    int herbivoreCount();

    int predatorCount();
}
