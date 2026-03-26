package model;

import util.Position;
import java.util.List;

/**
 * Interface fournissant un contexte abstrait pour l'accès à l'état du plateau.
 */
public interface BoardContext {

    /**
     * Récupère toutes les positions occupées par un type d'élément donné.
     */
    List<Position> getPositions(ModelElement element);

    /**
     * Vérifie si une position donnée est un obstacle non franchissable (Montagne).
     */
    boolean isImpassable(Position position);

    /**
     * Récupère l'élément du plateau qui est eteignable.
     */
    Extinguishable getExtinguishableElement();

    /**
     * Vérifie si une position est valide (dans les limites du plateau).
     */
    boolean isPositionValid(Position position);

    /**
     * Récupère les voisins d'une position donnée.
     */
    List<Position> getNeighbors(Position position);

    int rowCount();

    int columnCount();
}