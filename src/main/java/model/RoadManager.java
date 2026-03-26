package model;

import util.*;
import java.util.*;

public class RoadManager implements BoardElement {
    private final int initialRoadLines; // Interpreted as number of "groups" or "lines" of roads
    private List<Position> roadPositions;
    private final Map<Position, List<Position>> neighbors;
    private final ImmobileUpdateStrategy strategy;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private final Extinguishable extinguishable;
    private BoardContext boardContext;


    // Constant for the length of a straight road segment
    private static final int DEFAULT_ROAD_LENGTH = 4;

    public RoadManager(int rowCount, int columnCount, int initialRoadLines,
                       Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                       Random randomGenerator, ImmobileUpdateStrategy strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialRoadLines = initialRoadLines;
        this.randomGenerator = randomGenerator;
        this.roadPositions = new ArrayList<>();
        this.extinguishable = extinguishable;
        this.neighbors = neighbors;
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
     * Attempts to place a straight line of road of length DEFAULT_ROAD_LENGTH
     * starting from a random position.
     */
    private void placeRoadLine() {
        // 1. Pick a random starting position (potential start of the line)
        Position start = randomPosition();

        // 2. Determine a random direction (horizontal or vertical)
        // 0: Horizontal (change column), 1: Vertical (change row)
        boolean isHorizontal = randomGenerator.nextBoolean();

        int currentRow = start.row();
        int currentColumn = start.column();

        // 3. Place the road line
        for (int i = 0; i < DEFAULT_ROAD_LENGTH; i++) {
            Position roadSegment;

            if (isHorizontal) {
                // Check if the next column is within bounds
                if (currentColumn + i >= columnCount) break;
                roadSegment = new Position(currentRow, currentColumn + i);
            } else {
                // Check if the next row is within bounds
                if (currentRow + i >= rowCount) break;
                roadSegment = new Position(currentRow + i, currentColumn);
            }

            // Only add if it's not already a road segment
            if (!roadPositions.contains(roadSegment)) {
                roadPositions.add(roadSegment);
            }
        }
    }

    @Override
    public void initialize() {
        roadPositions.clear();
        // Create the specified number of road lines
        for (int index = 0; index < initialRoadLines; index++) {
            placeRoadLine();
        }
    }

    @Override
    public List<Position> getPositions() {
        return roadPositions;
    }

    @Override
    public boolean remove(Position position) {
        // Roads might be indestructible, similar to mountains.
        return roadPositions.remove(position);
    }

    @Override
    public void add(Position position) {
        roadPositions.add(position);
    }

    @Override
    public ModelElement getState() {
        return ModelElement.ROAD; // Assuming an appropriate ModelElement enum value exists
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    /**
     * Updates the state of the roads.
     * Works like mountains.
     */
    public List<Position> update(int step) {
        // Fonctionne comme des montagnes
        List<Position> noNewPositions = strategy.immobileStrategy(this, extinguishable);

        return new ArrayList<>();
    }


}
