package model;

import util.Position;
import util.PropagationUpdateStrategy;

import java.util.*;

public class FireManager implements BoardElement, Extinguishable, MobileElement {
    private final int initialFireCount;
    private Set<Position> firePositions;
    private final Map<Position, List<Position>> neighbors;
    private final PropagationUpdateStrategy strategy;
    private final Random randomGenerator;
    private final int rowCount;
    private final int columnCount;
    private BoardContext boardContext;


    public FireManager(int rowCount, int columnCount, int initialFireCount, Map<Position,
            List<Position>> neighbors, Random randomGenerator, PropagationUpdateStrategy strategy) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.initialFireCount = initialFireCount;
        this.neighbors = neighbors;
        this.randomGenerator = randomGenerator;
        this.firePositions = new HashSet<>();
        this.strategy = strategy;
        initialize();
    }

    public Position randomPosition() {
        return new Position(randomGenerator.nextInt(rowCount), randomGenerator.nextInt(columnCount));
    }

    public void initialize() {
        firePositions.clear();
        for (int index = 0; index < initialFireCount; index++)
            firePositions.add(randomPosition());
    }

    public List<Position> getPositions() {
        List<Position> listFirePositions = new ArrayList<>(firePositions);
        return listFirePositions;
    }

    public boolean remove(Position position) {
        return firePositions.remove(position);
    }
    // Meme fonction mais a utiliser pour eteindre un feu
    public boolean extinguish(Position position){
        return firePositions.remove(position);
    }

    public void add(Position position) {
        firePositions.add(position);
    }

    public ModelElement getState() {
        return ModelElement.FIRE;
    }

    public void setNewPositions(List<Position> newPositions){
        firePositions = new HashSet<>(newPositions);
    }

    public void setBoardContext(BoardContext boardContext){
        this.boardContext = boardContext;
    }

    public List<Position> update(int step) {
        List<Position> modifiedPositions = strategy.propagationStrategy(this, neighbors, step);
        return modifiedPositions;
    }
}