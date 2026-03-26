package model;

import util.*;
import java.util.*;

public class RockManager implements BoardElement, Burnable{
    private final int initialRockClusters;
    private final List<Position> rockPositions;
    private final Map<Position, List<Position>> neighbors;
    private final Random randomGenerator;
    private final ImmobileUpdateStrategy<Burnable> strategy;
    private final int rowCount;
    private final int columnCount;
    private final Extinguishable extinguishable;
    private BoardContext boardContext;


    // Counter map to track how long each rock has been exposed to fire
    private final Map<Position, Integer> rockFireCounters;
    private static final int PROPAGATION_DELAY = 4; // Rocks resist for 4 turns

    public RockManager(int rowCount, int columnCount, int initialRockClusters,
                       Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                       Random randomGenerator, ImmobileUpdateStrategy<Burnable> strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialRockClusters = initialRockClusters;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.rockPositions = new ArrayList<>();
        this.rockFireCounters = new HashMap<>();
        this.extinguishable = extinguishable;
        this.strategy = strategy;
        initialize();
    }

    public Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    private void placeRockCluster() {
        Position center = randomPosition();
        if (!rockPositions.contains(center)) {
            rockPositions.add(center);
        }
        List<Position> adjacentPositions = neighbors.get(center);
        if (adjacentPositions != null) {
            int count = 0;
            for (Position neighbor : adjacentPositions) {
                if (count >= 3) break;
                if (!rockPositions.contains(neighbor)) {
                    rockPositions.add(neighbor);
                    count++;
                }
            }
        }
    }

    @Override
    public void initialize() {
        rockPositions.clear();
        rockFireCounters.clear();
        for (int index = 0; index < initialRockClusters; index++) {
            placeRockCluster();
        }
    }

    @Override
    public List<Position> getPositions() {
        return rockPositions;
    }

    @Override
    public boolean remove(Position position) {
        if (rockPositions.remove(position)) {
            rockFireCounters.remove(position);
            return true;
        }
        return false;
    }

    @Override
    public void add(Position position) {
        if (!rockPositions.contains(position)) {
            rockPositions.add(position);
        }
    }

    @Override
    public List<Position> update(int step) {
        List<Position> transformedPositions = strategy.immobileStrategy(this, extinguishable);
        return transformedPositions;
    }

    @Override
    public ModelElement getState() {
        return ModelElement.ROCK;
    }

    @Override
    public void setBoardContext(BoardContext boardContext) {
        this.boardContext = boardContext;
    }

    public Integer getRockFireCounters(Position rockPosition) {
        return rockFireCounters.get(rockPosition);
    }

    public int getPropagationDelay() {
        return this.PROPAGATION_DELAY;
    }


    public Map<Position, Integer> getRockFireCountersMap() {
        return rockFireCounters;
    }
}