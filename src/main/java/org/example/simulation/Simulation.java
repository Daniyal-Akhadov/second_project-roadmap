package org.example.simulation;

import org.example.actions.Actions;
import org.example.entity.EntityFactoryService;
import org.example.view.BoardRenderer;

public final class Simulation {
    private static final int SLEEP_TIME_MS = 1_600;

    private final BoardRenderer renderer;
    private final EntityFactoryService entityFactoryService;
    private final Actions actions;

    public Simulation(final BoardRenderer renderer,
                      final EntityFactoryService entityFactoryService,
                      final Actions actions) {

        this.renderer = renderer;
        this.entityFactoryService = entityFactoryService;
        this.actions = actions;
    }

    public void start() {
        renderBoard();
        actions.start();
    }

    public void next() {
        while (entityFactoryService.hasHerbivore()) {
            try {
                actions.next();
                renderBoard();
                pause();
            } catch (final InterruptedException e) {
                handleInterruptedException(e);
            }
        }
    }

    private void renderBoard() {
        renderer.clearConsole();
        renderer.render();
    }

    private void pause() throws InterruptedException {
        Thread.sleep(SLEEP_TIME_MS);
    }

    private void handleInterruptedException(final InterruptedException e) {
        Thread.currentThread().interrupt();
        throw new RuntimeException(e);
    }
}
