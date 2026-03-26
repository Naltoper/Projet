package app;

import controller.Controller;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.FirefighterBoard;
import model.ModelElement;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class SimulatorApplication extends javafx.application.Application {
  private static final String VIEW_RESOURCE_PATH = "/view/view.fxml";
  private static final String APP_NAME = "Firefighter simulator";
  private static final int ROW_COUNT = 20;
  private static final int COLUMN_COUNT = 20;
  private static final int BOX_WIDTH = 50;
  private static final int BOX_HEIGHT = 50;
  public static final int INITIAL_FIRE_COUNT = 3;
  public static final int INITIAL_FIREFIGHTER_COUNT = 11;
  public static final int INITIAL_CLOUD_COUNT = 11;
  public static final int INITIAL_MOTORISEDFIREFIGHTER_COUNT = 1;
  public static final int INITIAL_MOUNTAIN_COUNT = 30;
  public static final int INITIAL_ROAD_COUNT = 12;
  public static final int INITIAL_ROCK_COUNT = 12;



    private Stage primaryStage;
  private Parent view;

  private void initializePrimaryStage(Stage primaryStage) {
    this.primaryStage = primaryStage;
    this.primaryStage.setTitle(APP_NAME);
    this.primaryStage.setOnCloseRequest(event -> Platform.exit());
    this.primaryStage.setResizable(true);
    this.primaryStage.sizeToScene();
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    initializePrimaryStage(primaryStage);
    initializeView();
    showScene();
  }

  private void initializeView() throws IOException {
    FXMLLoader loader = new FXMLLoader();
    URL location = SimulatorApplication.class.getResource(VIEW_RESOURCE_PATH);
    loader.setLocation(location);
    view = loader.load();
    Controller controller = loader.getController();

    // On instancie le configurator
    SimulatorConfigurator configurator = new SimulatorConfigurator(ROW_COUNT, COLUMN_COUNT);
    // On creer un board
    FirefighterBoard board = configurator.createBoard();

    controller.initialize(BOX_WIDTH, BOX_HEIGHT, COLUMN_COUNT, ROW_COUNT, board);
  }

  private void showScene() {
    Scene scene = new Scene(view);
    primaryStage.setScene(scene);
    primaryStage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
