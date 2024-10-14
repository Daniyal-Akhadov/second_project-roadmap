package org.example.view;

import org.example.board.BoardAccessor;
import org.example.board.Coordinate;
import org.example.entity.Entity;
import org.example.entity.EntityFactoryService;
import org.example.entity.nonstatic.Creature;

import java.util.Map;
import java.util.Set;

import static org.example.entity.EntityType.*;
import static org.example.entity.EntityType.StaticEntityType.ROCK;
import static org.example.entity.EntityType.StaticEntityType.TREE;

public final class BoardConsoleRenderer implements BoardRenderer {
    private final EntityFactoryService entityFactoryService;
    private final BoardAccessor board;

    public BoardConsoleRenderer(final EntityFactoryService entityFactoryService, final BoardAccessor board) {
        this.entityFactoryService = entityFactoryService;
        this.board = board;
    }

    @Override
    public void render() {
        final Map<Coordinate, Entity> map = board.getEntities();
        final Set<Creature> removedCreatureCoordinates = entityFactoryService.getDeadCreatures();

        for (int row = 0; row < board.getHeight(); row++) {
            for (int column = 0; column < board.getWidth(); column++) {
                final Coordinate coordinate = new Coordinate(column, row);
                final Entity entity = map.get(coordinate);
                String unicode = getEntityUnicode(entity);

                for (final Creature removedCreature : removedCreatureCoordinates) {
                    if (removedCreature.getCoordinate().equals(coordinate)) {
                        unicode = getDeadCreatureUnicode();
                        break;
                    }
                }

                System.out.print(unicode);
            }

            System.out.println();
        }
    }

    @Override
    public void clearConsole() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (final Exception e) {
            System.out.println(e);
        }
    }

    private static String getEntityUnicode(final Entity entity) {
        if (entity == null)
            return "\uD83C\uDFFD";

        final String name = getUpperCaseEntityName(entity);

        return switch (name) {
            case HERBIVORE -> "\uD83D\uDC07";
            case PREDATOR -> "\uD83D\uDC2F";
            case GRASS -> "\uD83C\uDF3F";
            case ROCK -> "\uD83E\uDEA8";
            case TREE -> "\uD83C\uDF32";
            default -> throw new IllegalStateException("Unexpected value: " + name);
        };
    }

    private static String getUpperCaseEntityName(final Entity entity) {
        return entity.getClass().getSimpleName().toUpperCase();
    }

    private static String getDeadCreatureUnicode() {
        return "\uD83E\uDD69";
    }
}
