package util;

import model.BoardContext;
import model.Extinguishable;
import model.MobileElement;

import java.util.List;
import java.util.Map;

public interface MovingUpdateStrategy {
    List<Position> moveStrategy(MobileElement element, Extinguishable extinguishable,
                                Map<Position, List<Position>> neighbors, int nbMoves, BoardContext boardContext);
}
