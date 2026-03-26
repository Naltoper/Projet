package model;

import util.*;
import java.util.*;

public class FirefighterManager implements BoardElement, MobileElement {
    private final int initialFirefighterCount;
    private final Extinguishable extinguishable;
    private List<Position> firefighterPositions;
    private final MovingUpdateStrategy strategy;
    private final Map<Position, List<Position>> neighbors;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private final int nbMoves;
    private BoardContext boardContext;


    public FirefighterManager(int rowCount, int columnCount, int initialFirefighterCount, 
                              Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                              Random randomGenerator, MovingUpdateStrategy strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialFirefighterCount = initialFirefighterCount;
        this.extinguishable = extinguishable;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.firefighterPositions = new ArrayList<>();
        this.strategy = strategy;
        this.nbMoves = 1;
        initialize();
    }

    private Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    public void initialize() {
        firefighterPositions.clear();
        for (int index = 0; index < initialFirefighterCount; index++)
            firefighterPositions.add(randomPosition());
    }

    public List<Position> getPositions() {
        return firefighterPositions;
    }
    
    public boolean remove(Position position) {
        return firefighterPositions.remove(position);
    }
 
    public void add(Position position) {
        firefighterPositions.add(position);
    }

    public ModelElement getState() {
        return ModelElement.FIREFIGHTER;
    }

    public void setNewPositions(List<Position> newPositions){
        firefighterPositions = newPositions;
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    public List<Position> update(int step) {
        List<Position> modifiedPositions = strategy.moveStrategy(this, extinguishable, neighbors, nbMoves, boardContext);
        return modifiedPositions;
    }
}