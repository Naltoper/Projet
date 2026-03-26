package util;

import model.MobileElement;

import java.util.List;
import java.util.Map;


public interface PropagationUpdateStrategy {
    /**
     * Fournie une methode qui gere la propagation d'un element (different du deplacement)
     */
    List<Position> propagationStrategy(MobileElement boardElement, Map<Position, List<Position>> neighbors, int step);
}