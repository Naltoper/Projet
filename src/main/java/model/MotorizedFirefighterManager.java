package model;

import util.*;
import java.util.*;

public class MotorizedFirefighterManager implements BoardElement, MobileElement {
    private final int initialMotorisedFirefighterCount;
    private final Extinguishable extinguishable;
    private List<Position> motorisedFirefighterPositions;
    private final MovingUpdateStrategy strategy;
    private final Map<Position, List<Position>> neighbors;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private final int nbMoves;
    private BoardContext boardContext;



    public MotorizedFirefighterManager(int rowCount, int columnCount, int initialMotorisedFirefighterCount,
                              Extinguishable extinguishable, Map<Position, List<Position>> neighbors,
                              Random randomGenerator, MovingUpdateStrategy strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialMotorisedFirefighterCount = initialMotorisedFirefighterCount;
        this.extinguishable = extinguishable;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.motorisedFirefighterPositions = new ArrayList<>();
        this.strategy = strategy;
        this.nbMoves = 2;
        initialize();
    }

    private Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    public void initialize() {
        motorisedFirefighterPositions.clear();
        for (int index = 0; index < initialMotorisedFirefighterCount; index++)
            motorisedFirefighterPositions.add(randomPosition());
    }

    public List<Position> getPositions() {
        return motorisedFirefighterPositions;
    }
    
    public boolean remove(Position position) {
        return motorisedFirefighterPositions.remove(position);
    }
 
    public void add(Position position) {
        motorisedFirefighterPositions.add(position);
    }

    public ModelElement getState() {
        return ModelElement.MOTORISEDFIREFIGHTER;
    }

    public void setNewPositions(List<Position> newPositions){
        motorisedFirefighterPositions = newPositions;
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    public List<Position> update(int step) {
        List<Position> modifiedPositions = strategy.moveStrategy(this, extinguishable, neighbors, nbMoves, boardContext);
        return modifiedPositions;
    }
}