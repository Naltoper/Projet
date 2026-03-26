package util;

import app.SimulatorApplication;
import model.*;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class DefaultBoardElementFactory implements BoardElementFactory{
    @Override
    public Extinguishable createFireManager(int rowCount, int columnCount, Map<Position, List<Position>> neighbors,
                                            Random randomGenerator, PropagationUpdateStrategy strategy) {
        return new FireManager(rowCount, columnCount, SimulatorApplication.INITIAL_FIRE_COUNT,
                neighbors, randomGenerator, strategy);
    }

    @Override
    public FirefighterManager createFirefighterManager(int rowCount, int columnCount, Extinguishable extinguishable,
                                                       Map<Position, List<Position>> neighbors, Random randomGenerator,
                                                       MovingUpdateStrategy strategy) {
        return new FirefighterManager(
                rowCount, columnCount, SimulatorApplication.INITIAL_FIREFIGHTER_COUNT, extinguishable, neighbors,
                randomGenerator, strategy);
    }

    @Override
    public MotorizedFirefighterManager createMotorizedFireFighterManager(int rowCount, int columnCount,
                                                                         Extinguishable extinguishable, Map<Position,
                    List<Position>> neighbors, Random randomGenerator, MovingUpdateStrategy strategy) {
        return new MotorizedFirefighterManager(
                rowCount, columnCount, SimulatorApplication.INITIAL_MOTORISEDFIREFIGHTER_COUNT, extinguishable, neighbors,
                randomGenerator, strategy);
    }

    @Override
    public CloudManager createCloudManager(int rowCount, int columnCount, Extinguishable extinguishable, Map<Position,
            List<Position>> neighbors, Random randomGenerator, MovingUpdateStrategy strategy) {
        return new CloudManager(
                rowCount, columnCount, SimulatorApplication.INITIAL_CLOUD_COUNT, extinguishable, neighbors,
                randomGenerator, strategy);
    }

    @Override
    public MountainManager createMountainManager(int rowCount, int columnCount, Extinguishable extinguishable, Map<Position,
            List<Position>> neighbors, Random randomGenerator, ImmobileUpdateStrategy strategy) {
        return new MountainManager(rowCount, columnCount, SimulatorApplication.INITIAL_MOUNTAIN_COUNT,
                extinguishable, neighbors, randomGenerator, strategy);
    }

    @Override
    public RoadManager createRoadManager(int rowCount, int columnCount, Extinguishable extinguishable, Map<Position,
            List<Position>> neighbors, Random randomGenerator, ImmobileUpdateStrategy strategy) {
        return new RoadManager(rowCount, columnCount, SimulatorApplication.INITIAL_ROAD_COUNT,
                extinguishable, neighbors, randomGenerator, strategy);
    }

    @Override
    public RockManager createRockManager(int rowCount, int columnCount, Extinguishable extinguishable, Map<Position, List<Position>> neighbors, Random randomGenerator, ImmobileUpdateStrategy strategy) {
        return new RockManager(rowCount, columnCount, SimulatorApplication.INITIAL_ROCK_COUNT,
                extinguishable, neighbors, randomGenerator, strategy);
    }

    @Override
    public List<BoardElement> createAllElements(int rowCount, int columnCount, Map<Position, List<Position>> neighbors,
                                                Random randomGenerator) {
        // init the necessary strategies
        PropagationUpdateStrategy firePropagationStrategy = new FirePropagationStrategy();
        MovingUpdateStrategy movingStrategyFireFighter = new FireFighterUpdateStrategy();
        MovingUpdateStrategy movingStrategyRandom = new RandomMoveUpdateStrategy();
        ImmobileUpdateStrategy<BoardElement> mountainStrategy = new MountainUpdateStrategy();
        ImmobileUpdateStrategy<Burnable> rockStrategy = new RockUpdateStrategy();

        // init the managers
        FireManager fireManager = (FireManager) createFireManager(
                rowCount, columnCount, neighbors, randomGenerator, firePropagationStrategy);

        FirefighterManager firefighterManager = createFirefighterManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, movingStrategyFireFighter);

        MotorizedFirefighterManager motorizedFirefighterManager = createMotorizedFireFighterManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, movingStrategyFireFighter);

        CloudManager cloudManager = createCloudManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, movingStrategyRandom);

        MountainManager mountainManager = createMountainManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, mountainStrategy);

        RoadManager roadManager = createRoadManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, mountainStrategy);

        RockManager rockManager = createRockManager(
                rowCount, columnCount, fireManager, neighbors, randomGenerator, rockStrategy);

        return List.of(
                fireManager,
                firefighterManager,
                motorizedFirefighterManager,
                cloudManager,
                mountainManager,
                roadManager,
                rockManager
        );
    }
}
