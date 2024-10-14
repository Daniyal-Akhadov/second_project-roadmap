package org.example;

import org.example.simulation.Simulation;
import org.example.simulation.SimulationInitializer;

public final class Launcher {
    public static void main(final String[] args) {
        final SimulationInitializer simulationInitializer = new SimulationInitializer();
        final Simulation simulation = simulationInitializer.init();
        simulation.start();
        simulation.next();
    }
}

