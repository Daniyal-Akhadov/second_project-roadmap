package org.example.entity;

import org.example.board.BoardService;
import org.example.board.Coordinate;
import org.example.entity.nonstatic.*;
import org.example.entity.static_entity.Rock;
import org.example.entity.static_entity.Tree;
import org.example.path.BreadthFirstSearch;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

import static org.example.entity.EntityType.*;
import static org.example.entity.EntityType.StaticEntityType.ROCK;
import static org.example.entity.EntityType.StaticEntityType.TREE;

public final class EntityFactory implements EntityFactoryService {
    private final Set<Creature> creatures;
    private final Random random;

    public EntityFactory() {
        creatures = new HashSet<>();
        random = new Random();
    }

    @Override
    public Entity createEntity(final String type, final Coordinate coordinate, final BoardService board) {
        return switch (type) {
            case GRASS -> createGrass();
            case PREDATOR -> createPredator(coordinate, board);
            case HERBIVORE -> createHerbivore(coordinate, board);
            case STATIC -> createStaticEntity();
            default -> throw new IllegalStateException("Unexpected value: " + type);
        };
    }

    @Override
    public boolean hasHerbivore() {
        return getAliveCreatures().stream().anyMatch(creature -> creature instanceof Herbivore);
    }

    @Override
    public void removeDeadCreatures() {
        creatures.removeIf(creature -> creature.isAlive() != true);
    }

    @Override
    public Set<Creature> getDeadCreatures() {
        return creatures.stream()
                .filter(creature -> creature.isAlive() != true)
                .collect(Collectors.toSet());
    }

    @Override
    public Set<Creature> getAliveCreatures() {
        return creatures.stream()
                .filter(Creature::isAlive)
                .collect(Collectors.toSet());
    }

    private Grass createGrass() {
        final int maxIncreaseHealth = 1;
        final int increaseHealth = random.nextInt(maxIncreaseHealth + 1);
        return new Grass(increaseHealth);
    }

    private Creature createHerbivore(final Coordinate coordinate, final BoardService board) {
        final Herbivore herbivore =
                new Herbivore(2, new Health(2), coordinate, new BreadthFirstSearch(board), board);
        creatures.add(herbivore);
        return herbivore;
    }

    private Creature createPredator(final Coordinate coordinate, final BoardService board) {
        final Predator predator =
                new Predator(1, new Health(1), coordinate, new BreadthFirstSearch(board), board, 1);
        creatures.add(predator);
        return predator;
    }

    private Entity createStaticEntity() {
        final String[] values = {TREE, ROCK};
        final String randomType = values[random.nextInt(values.length)];

        return switch (randomType) {
            case ROCK -> new Rock();
            case TREE -> new Tree();
            default -> throw new IllegalStateException("Unexpected value: " + randomType);
        };
    }

    public int predatorCount() {
        return (int) creatures.stream().filter(creature -> creature instanceof Predator).count();
    }

    @Override
    public int herbivoreCount() {
        return (int) creatures.stream().filter(creature -> creature instanceof Herbivore).count();
    }
}