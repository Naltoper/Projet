package model;

import util.*;
import java.util.*;

public class FirefighterBoard implements Board<List<ModelElement>>, BoardContext {
    private final int columnCount;
    private final int rowCount;
    private final List<BoardElement> boardElements;

    private int step = 0;

    // Le Board ne crée plus ses éléments, il les reçoit.
    public FirefighterBoard(int columnCount, int rowCount, List<BoardElement> initialElements) {
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        this.boardElements = initialElements;
    }


    @Override
    public List<ModelElement> getState(Position position) {
        List<ModelElement> result = new ArrayList<>();

        for (BoardElement element : boardElements) {
            if (element.getPositions().contains(position))
                result.add(element.getState());
        }
            
        return result;
    }


    @Override
    public int rowCount() {
        return rowCount;
    }

    @Override
    public int columnCount() {
        return columnCount;
    }

    public List<Position> updateToNextGeneration() {
        // Délégation des mises à jour aux managers
        List<Position> modifiedPositions = new ArrayList<>();

        for (BoardElement element : boardElements) {
            modifiedPositions.addAll(element.update(step)); // Cast car on update que le MobileElement
        }
        
        step++;
        return modifiedPositions;
    }

    @Override
    public int stepNumber() {
        return step;
    }

    @Override
    public void reset() {
        step = 0;
        for (BoardElement element : boardElements) {
            element.initialize();
        }
    }

    @Override
    public void setState(List<ModelElement> state, Position position) {
        // Retirer tous les éléments à cette position
        for (BoardElement element : boardElements) {
            while (element.remove(position)) { } // Supprime toutes les instances de element à cette position
        }
    }



    public List<Position> getPositions(ModelElement element) {
        for (BoardElement boardElement : boardElements) {
            if (boardElement.getState().equals(element)) {
                return boardElement.getPositions();
            }
        }
        return List.of();
    }

    @Override
    public boolean isImpassable(Position position) {
        return false;
    }

    @Override
    public Extinguishable getExtinguishableElement() {
        return null;
    }

    @Override
    public boolean isPositionValid(Position position) {
        return false;
    }

    @Override
    public List<Position> getNeighbors(Position position) {
        return List.of();
    }
}