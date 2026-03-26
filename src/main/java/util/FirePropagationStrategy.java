package util;

import model.MobileElement;

import java.util.*;

public class FirePropagationStrategy implements PropagationUpdateStrategy {

    public List<Position> propagationStrategy(MobileElement element, Map<Position, List<Position>> neighbors, int step) {
        List<Position> modifiedPositions = new ArrayList<>();

        // Logique de propagation
        if (step % 2 == 0) {
            Set<Position> currentFirePositions = new HashSet<>(element.getPositions());
            Set<Position> newFirePositions = new HashSet<>();

            for (Position fire : currentFirePositions) {
                // Ajout des voisins du feu comme potentiellement en feu
                if (neighbors.containsKey(fire)) {
                    for (Position neighbor : neighbors.get(fire)) {
                        if (!currentFirePositions.contains(neighbor)) {
                            newFirePositions.add(neighbor);
                        }
                    }
                }
            }

            // Ajouter les nouvelles positions au BoardElement
            List<Position> listNewPos = new ArrayList<>(currentFirePositions);
            listNewPos.addAll(newFirePositions); // On a les ancienne + les nouvelles
            element.setNewPositions(listNewPos);

            modifiedPositions.addAll(newFirePositions);
        }
        return modifiedPositions;
    }
}
