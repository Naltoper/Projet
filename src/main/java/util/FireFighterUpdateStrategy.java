package util;

import model.*;

import java.util.*;

public class FireFighterUpdateStrategy implements MovingUpdateStrategy {

    /**
     * Methode de deplacement vers la case voisine la plus proche d'un feu (pompier)
     * @param position current position.
     * @param targets positions that are targeted.
     * @return the position next to the current position that is on the path to the closest target.
     */
    public Position neighborClosestToFire(Position position, Collection<Position> targets,
                                          Map<Position,List<Position>>neighbors, BoardContext boardContext) {
        Set<Position> seen = new HashSet<Position>();
        HashMap<Position, Position> firstMove = new HashMap<Position, Position>();
        Queue<Position> toVisit = new LinkedList<Position>();
        // TODO si des Pos de neighbors ne sont pas des monatgnes, alors on les ajoutes a toVisite
        List<Position> mountainPositions = boardContext.getPositions(ModelElement.MOUNTAIN); // Recup les positions des montagne
        for (Position neighborPos : neighbors.get(position)) {
            if (!mountainPositions.contains(neighborPos)) {
                toVisit.add(neighborPos);
            }
        }

        for (Position initialMove : toVisit)
            firstMove.put(initialMove, initialMove);
        while (!toVisit.isEmpty()) {
            Position current = toVisit.poll();
            if (targets.contains(current))
                return firstMove.get(current);
            for (Position adjacent : neighbors.get(current)) {
                if (seen.contains(adjacent)) continue;
                toVisit.add(adjacent);
                seen.add(adjacent);
                firstMove.put(adjacent, firstMove.get(current));
            }
        }
        return position;
    }

    public List<Position> moveStrategy(MobileElement element, Extinguishable extinguishable, Map<Position,
            List<Position>> neighbors, int nbMoves, BoardContext boardContext){
        List<Position> modifiedPositions = new ArrayList<>();
        List<Position> cloudNewPositions = new ArrayList<>();
        List<Position> currentFirePositions = extinguishable.getPositions();

        for (Position elementPosition : element.getPositions()) {
            // 1. Détermination de la nouvelle position (mouvement)
            Position newElementPosition =
                    neighborClosestToFire(elementPosition, extinguishable.getPositions(), neighbors, boardContext);
                // 1.Bis on le refait autant de fois qu'il faut si nbMoves > 1
            for (int i = 1; i < nbMoves; i++) {
                newElementPosition = neighborClosestToFire(newElementPosition, extinguishable.getPositions(), neighbors, boardContext);
            }

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