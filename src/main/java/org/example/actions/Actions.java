package org.example.actions;

import java.util.List;

public final class Actions {
    private final List<Action> init;
    private final List<Action> turn;

    public Actions(final List<Action> init, final List<Action> turn) {
        this.init = init;
        this.turn = turn;
    }

    public void start() {
        init.forEach(Action::execute);
    }

    public void next() {
        turn.forEach(Action::execute);
    }
}
