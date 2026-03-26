package util;
import model.*;
import java.util.*;

public interface BoardElementFactory {

    Extinguishable createFireManager(int rowCount, int columnCount, Map<Position, List<Position>> neighbors,
                                     Random randomGenerator, PropagationUpdateStrategy strategy);

    FirefighterManager createFirefighterManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                                Map<Position, List<Position>> neighbors, Random randomGenerator,
                                                MovingUpdateStrategy strategy);

    MotorizedFirefighterManager createMotorizedFireFighterManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                                Map<Position, List<Position>> neighbors, Random randomGenerator,
                                                MovingUpdateStrategy strategy);

    CloudManager createCloudManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                                Map<Position, List<Position>> neighbors, Random randomGenerator,
                                                MovingUpdateStrategy strategy);

    MountainManager createMountainManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                     Map<Position, List<Position>> neighbors, Random randomGenerator,
                                     ImmobileUpdateStrategy strategy);

    RoadManager createRoadManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                     Map<Position, List<Position>> neighbors, Random randomGenerator,
                                     ImmobileUpdateStrategy strategy);

    RockManager createRockManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                  Map<Position, List<Position>> neighbors, Random randomGenerator,
                                  ImmobileUpdateStrategy strategy);

    List<BoardElement> createAllElements(int rowCount, int columnCount, Map<Position, List<Position>> neighbors,
                                         Random randomGenerator);
}