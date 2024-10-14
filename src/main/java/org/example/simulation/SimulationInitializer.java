package org.example.simulation;

import org.example.actions.Actions;
import org.example.actions.MoveCreatures;
import org.example.actions.spawn.SpawnGrassAction;
import org.example.actions.spawn.SpawnHerbivore;
import org.example.board.Board;
import org.example.entity.EntityFactory;
import org.example.entity.EntityFactoryService;
import org.example.entity.EntityType;
import org.example.view.BoardConsoleRenderer;
import org.example.view.BoardRenderer;

import java.util.List;

public final class SimulationInitializer {
    public Simulation init() {
        final int staticEntityCount = SimulationConfig.getStaticEntityCount(SimulationConfig.getSize());
        final int grassCount = SimulationConfig.getGrassCount(SimulationConfig.getSize());
        final int herbivoreCount = SimulationConfig.getHerbivoreCount(SimulationConfig.getSize());
        final int predatorCount = SimulationConfig.getPredatorCount(SimulationConfig.getSize());

        final EntityFactoryService entityFactoryService = new EntityFactory();
        final Board board = createBoard(entityFactoryService, staticEntityCount, grassCount, herbivoreCount, predatorCount);
        final BoardRenderer boardRenderer = new BoardConsoleRenderer(entityFactoryService, board);
        final Actions actions = createActions(entityFactoryService, board);

        return new Simulation(boardRenderer, entityFactoryService, actions);
    }

    private static Actions createActions(final EntityFactoryService entityFactoryService, final Board board) {
        return new Actions(List.of(),
                List.of(
                        new MoveCreatures(entityFactoryService, board),
                        new SpawnGrassAction(1, board),
                        new SpawnHerbivore(board, entityFactoryService)
                )
        );
    }

    private static Board createBoard(final EntityFactoryService entityFactoryService, final int staticEntityCount, final int grassCount, final int herbivoreCount, final int predatorCount) {
        return new Board.Builder(SimulationConfig.HEIGHT, SimulationConfig.WIDTH, entityFactoryService)
                .addEntityCount(EntityType.STATIC, staticEntityCount)
                .addEntityCount(EntityType.GRASS, grassCount)
                .addEntityCount(EntityType.HERBIVORE, herbivoreCount)
                .addEntityCount(EntityType.PREDATOR, predatorCount)
                .build();
    }
}
