package util;

import model.BoardElement;
import model.Burnable;
import model.Extinguishable;

import java.util.*;

public class RockUpdateStrategy implements ImmobileUpdateStrategy<Burnable> {


    @Override
    public List<Position> immobileStrategy(Burnable element, Extinguishable extinguishable) {

        // Carte des compteurs d'extinction pour chaque position de rocher
        Map<Position, Integer> extinguishCounters = element.getRockFireCountersMap();
        int propagationDelay = element.getPropagationDelay();

        // Liste des positions à retourner (potentiellement les rochers qui disparaissent)
        List<Position> removedPositions = new ArrayList<>();

        // Parcourir toutes les positions occupées par des rochers
        for (Position rockPos : new HashSet<>(element.getPositions())) {

            if (extinguishable.getPositions().contains(rockPos)) {
                // Le rocher est en contact avec le feu → il éteint le feu et incrémente son compteur d'extinction
                int currentCount = extinguishCounters.getOrDefault(rockPos, 0) + 1;
                extinguishCounters.put(rockPos, currentCount);
                extinguishable.extinguish(rockPos);

                // Si le rocher a éteint le feu PROPAGATION_DELAY fois consécutives,
                // il brûle et disparaît définitivement
                if (currentCount >= propagationDelay) {
                    element.remove(rockPos);
                    extinguishCounters.remove(rockPos);
                    removedPositions.add(rockPos);
                }
            }
        }

        return removedPositions;
    }
}
