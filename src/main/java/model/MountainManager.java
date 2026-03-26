package model;

import util.*;
import java.util.*;

public class MountainManager implements BoardElement {
    private final int initialMountainClusters; // Interpreted as number of "groups" of mountains
    private List<Position> mountainPositions;
    private final Map<Position, List<Position>> neighbors;
    private final ImmobileUpdateStrategy<BoardElement> strategy;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private final Extinguishable extinguishable;
    private BoardContext boardContext;


    public MountainManager(int rowCount, int columnCount, int initialMountainClusters,
                           Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                           Random randomGenerator, ImmobileUpdateStrategy<BoardElement> strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialMountainClusters = initialMountainClusters;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.mountainPositions = new ArrayList<>();
        this.extinguishable = extinguishable;
        this.strategy = strategy;
        initialize();
    }

    /**
     * Generates a random position within the board limits.
     */
    public Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    /**
     * Places a cluster of 4 mountains (1 center + 3 neighbors).
     */
    private void placeMountainCluster() {
        // Pick a random center
        Position center = randomPosition();
        if (!mountainPositions.contains(center)) {
            mountainPositions.add(center);
        }

        // Get neighbors to create the "glued" effect
        List<Position> adjacentPositions = neighbors.get(center);

        // Add up to 3 neighbors to make a group of 4
        if (adjacentPositions != null) {
            int count = 0;
            for (Position neighbor : adjacentPositions) {
                if (count >= 4) break; // Stop once we have added 3 neighbors

                if (!mountainPositions.contains(neighbor)) {
                    mountainPositions.add(neighbor);
                    count++;
                }
            }
        }
    }

    @Override
    public void initialize() {
        mountainPositions.clear();
        // Create the specified number of mountain clusters
        for (int index = 0; index < initialMountainClusters; index++) {
            placeMountainCluster();
        }
    }

    @Override
    public List<Position> getPositions() {
        return mountainPositions;
    }

    @Override
    public boolean remove(Position position) {
        // Optional: Depending on game rules, mountains might be indestructible.
        // If they are destructible, keep this. If not, return false.
        return mountainPositions.remove(position);
    }

    @Override
    public void add(Position position) {
        mountainPositions.add(position);
    }

    public ModelElement getState() {
        return ModelElement.MOUNTAIN;
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    /**
     * Updates the state of the mountains. it extinguishes the fire on itself
     */
    public List<Position> update(int step) {
        List<Position> noNewPositions = strategy.immobileStrategy(this, extinguishable);
        return new ArrayList<>();
    }


}