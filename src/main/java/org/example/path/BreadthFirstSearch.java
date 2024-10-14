package org.example.path;

import org.example.board.BoardService;
import org.example.board.Coordinate;
import org.example.entity.Entity;

import java.util.*;

public final class BreadthFirstSearch implements PathSearchAlgorithm {
    private final Map<Coordinate, Boolean> visited;
    private final Map<Coordinate, Coordinate> parent;
    private final Queue<Coordinate> queue;
    private final List<Coordinate> boardCoordinates;
    private final BoardService boardService;

    public BreadthFirstSearch(final BoardService boardService) {
        this.visited = new HashMap<>();
        this.parent = new HashMap<>();
        this.queue = new LinkedList<>();
        this.boardService = boardService;

        boardCoordinates = generateBoardCoordinates(boardService.getHeight(), boardService.getWidth());
    }

    @Override
    public List<Coordinate> searchPath(final Coordinate start, final String goalEntity, final Class<? extends Entity>... bounds) {
        initializeDefaultSettings();
        initializeSearch(start);

        while (queue.isEmpty() != true) {
            final Coordinate current = queue.poll();

            if (isGoalEntity(current, goalEntity)) {
                return reconstructPath(current);
            }

            exploreNeighbors(current, bounds);
        }

        return Collections.emptyList();
    }

    @Override
    public List<Coordinate> getSpeedAdjustedPath(final List<Coordinate> path, final int speed) {
        final List<Coordinate> adjustedPath = new ArrayList<>();

        for (int i = 0; i < path.size(); i++) {
            if (i % speed == 0 || i == path.size() - 1) {
                adjustedPath.add(path.get(i));
            }
        }

        return adjustedPath;
    }

    @Override
    public void removeCurrentCoordinateFromPath(final List<Coordinate> path) {
        if (path.isEmpty()) {
            return;
        }

        path.removeFirst();
    }

    private static ArrayList<Coordinate> generateBoardCoordinates(final int height, final int width) {
        final ArrayList<Coordinate> boardCoordinates = new ArrayList<>();

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                final Coordinate coordinate = new Coordinate(x, y);
                boardCoordinates.add(coordinate);
            }
        }

        return boardCoordinates;
    }

    private void initializeSearch(final Coordinate start) {
        queue.add(start);
        visited.put(start, true);
    }

    private boolean isGoalEntity(final Coordinate current, final String goalEntity) {
        final Entity entity = boardService.getEntityBy(current);

        if (entity == null) {
            return false;
        }

        return entity.getClass().getSimpleName().equals(goalEntity);
    }

    private void exploreNeighbors(final Coordinate current, final Class<? extends Entity>... bounds) {
        final List<Coordinate> neighbors = getNeighbors(current, bounds);

        for (final Coordinate neighbor : neighbors) {
            if (!visited.getOrDefault(neighbor, false)) {
                enqueueNeighbor(neighbor, current);
            }
        }
    }

    private void enqueueNeighbor(final Coordinate neighbor, final Coordinate current) {
        queue.add(neighbor);
        visited.put(neighbor, true);
        parent.put(neighbor, current);
    }

    private void initializeDefaultSettings() {
        for (final Coordinate point : boardCoordinates) {
            visited.put(point, false);
            parent.put(point, null);
        }

        queue.clear();
    }

    private List<Coordinate> reconstructPath(Coordinate current) {
        final List<Coordinate> path = new ArrayList<>();

        while (current != null) {
            path.add(current);
            current = parent.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    private List<Coordinate> getNeighbors(final Coordinate point, final Class<? extends Entity>... bounds) {
        final List<Coordinate> neighbors = new ArrayList<>();
        for (int xOffset = -1; xOffset <= 1; xOffset++) {
            for (int yOffset = -1; yOffset <= 1; yOffset++) {
                if (xOffset == 0 && yOffset == 0) {
                    continue;
                }

                final Coordinate neighbor = new Coordinate(point.getX() + xOffset, point.getY() + yOffset);

                if (isValidPoint(neighbor, bounds)) {
                    neighbors.add(neighbor);
                }
            }
        }

        return neighbors;
    }

    private boolean isValidPoint(final Coordinate point, final Class<? extends Entity>... bounds) {
        final Entity entity = boardService.getEntityBy(point);

        if (entity != null) {
            for (final Class<?> clazz : bounds) {
                if (clazz.isAssignableFrom(entity.getClass())) {
                    return false;
                }
            }
        }

        return point.getX() >= 0 && point.getX() < boardService.getWidth() &&
                point.getY() >= 0 && point.getY() < boardService.getHeight();
    }
}
