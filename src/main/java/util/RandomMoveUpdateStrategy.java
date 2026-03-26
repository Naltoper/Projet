package util;

import model.BoardContext;
import model.Extinguishable;
import model.MobileElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class RandomMoveUpdateStrategy implements MovingUpdateStrategy {
    /**
     * Methode de deplacement aleatoire sure les case voisine (nuage)
     * @param position
     * @param neighbors
     * @return
     */
    private Position randomMove(Position position, Map<Position, List<Position>> neighbors){
        List<Position> list = neighbors.get(position); // List des neighbors
        return list.get(new Random().nextInt(list.size())); // Position aleatoire
    }

    public List<Position> moveStrategy(MobileElement element, Extinguishable extinguishable, Map<Position,
            List<Position>> neighbors, int nbMoves, BoardContext boardContext){
        List<Position> modifiedPositions = new ArrayList<>();
        List<Position> cloudNewPositions = new ArrayList<>();
        List<Position> currentFirePositions = extinguishable.getPositions();

        for (Position elementPosition : element.getPositions()) {
            // 1. Détermination de la nouvelle position (mouvement)
            Position newElementPosition =
                    randomMove(elementPosition, neighbors);

            // 2. Extinction à la nouvelle position
            extinguishable.extinguish(newElementPosition);

            // 3. Extinction chez les voisins de la nouvelle position
            List<Position> neighborFirePositions = neighbors.get(newElementPosition).stream()
                    .filter(currentFirePositions::contains).toList();
            for (Position firePosition : neighborFirePositions)
                extinguishable.extinguish(firePosition);

            // 4. Mise à jour des positions modifiées (anciennes, nouvelles et éteintes)
            modifiedPositions.add(elementPosition);
            modifiedPositions.add(newElementPosition);
            modifiedPositions.addAll(neighborFirePositions);

            // 5. Ajout de la nouvelle position pour la prochaine génération
            cloudNewPositions.add(newElementPosition);
        }
        // Mise à jour finale des positions des pompiers
        element.setNewPositions(cloudNewPositions);
        return modifiedPositions;
    }
}
