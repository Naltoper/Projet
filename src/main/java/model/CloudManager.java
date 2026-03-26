package model;

import util.*;
import java.util.*;

public class CloudManager implements BoardElement, MobileElement {
    private final int initialCloudCount;
    private final Extinguishable extinguishable;
    private List<Position> cloudPositions;
    private final MovingUpdateStrategy strategy;
    private final Map<Position, List<Position>> neighbors;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private final int nbMoves;
    private BoardContext boardContext;

    public CloudManager(int rowCount, int columnCount, int initialCloudCount,
                        Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                        Random randomGenerator, MovingUpdateStrategy strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialCloudCount = initialCloudCount;
        this.extinguishable = extinguishable;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.cloudPositions = new ArrayList<>();
        this.nbMoves = 1;
        this.strategy = strategy;
        initialize();
    }

    private Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    public void initialize() {
        cloudPositions.clear();
        for (int index = 0; index < initialCloudCount; index++)
            cloudPositions.add(randomPosition());
    }

    public List<Position> getPositions() {
        return cloudPositions;
    }

    public boolean remove(Position position) {
        return cloudPositions.remove(position);
    }

    public void add(Position position) {
        cloudPositions.add(position);
    }

    public void setNewPositions(List<Position> newPositions){
        cloudPositions = newPositions;
    }

    public ModelElement getState() {
        return ModelElement.CLOUD;
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    public List<Position> update(int step) {
        List<Position> modifiedPositions = strategy.moveStrategy(this, extinguishable, neighbors, nbMoves, boardContext);
        return modifiedPositions;
    }
}