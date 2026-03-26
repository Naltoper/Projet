package util;

import model.BoardElement;
import model.Extinguishable;

import java.util.List;

public class MountainUpdateStrategy implements ImmobileUpdateStrategy<BoardElement> {

    public List<Position> immobileStrategy(BoardElement element, Extinguishable extinguishable) {
        // Extinction sur les positions des montagnes
        for (Position mountainPosition : element.getPositions()){
            extinguishable.extinguish(mountainPosition);
        }

        return List.of();
    }
}
