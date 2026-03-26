package model;

import util.Position;

import java.util.List;

/**
 * Les elements visibles
 */
public interface BoardElement {
    void initialize();
    List<Position> getPositions();
    boolean remove(Position position);
    void add(Position position);
    ModelElement getState();
    List<Position> update(int step);
    void setBoardContext(BoardContext boardContext);
}
