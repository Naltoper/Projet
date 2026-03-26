package app;

import model.*;
import util.*;
import java.util.*;
/**
 *  Cette clase permet d'instancier chaque element et des les passer a FireFighterBoard
 */
public class SimulatorConfigurator {
    private final int rowCount;
    private final int columnCount;

    public SimulatorConfigurator(int rowCount, int columnCount) {
        this.rowCount = rowCount;
        this.columnCount = columnCount;
    }

    public FirefighterBoard createBoard() {
        // Utilitaires
        Random randomGenerator = new Random();
        // Initialisation des positions et des voisins
        Position[][] positions = new Position[rowCount][columnCount];
        Map<Position, List<Position>> neighbors = new HashMap<>();


        for (int c = 0; c < columnCount; c++)
            for (int r = 0; r < rowCount; r++)
                positions[r][c] = new Position(r, c);

        for (int c = 0; c < columnCount; c++)
            for (int r = 0; r < rowCount; r++) {
                List<Position> list = new ArrayList<>();
                Position p = positions[r][c];
                if (r > 0) list.add(positions[r - 1][c]);
                if (c > 0) list.add(positions[r][c - 1]);
                if (r < rowCount - 1) list.add(positions[r + 1][c]);
                if (c < columnCount - 1) list.add(positions[r][c + 1]);
                neighbors.put(p, list);
            }

        BoardElementFactory factory = new DefaultBoardElementFactory();
        List<BoardElement> allElements = factory.createAllElements(rowCount, columnCount, neighbors, randomGenerator);
        FirefighterBoard board = new FirefighterBoard(columnCount, rowCount, allElements);
        // Injection du boardContext une fois le boardContext créé
        for (BoardElement element : allElements) {
            element.setBoardContext(board);
        }

        return board;
    }
}
