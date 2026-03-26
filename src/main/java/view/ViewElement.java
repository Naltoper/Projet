package view;

import javafx.scene.paint.Color;

public enum ViewElement {
  FIREFIGHTER(Color.BLUE),
    FIRE(Color.RED),
    CLOUD(Color.GREY),
    MOTORISEDFIREFIGHTER(Color.GREEN),
    MOUNTAIN(Color.BLACK),
    ROAD(Color.DARKGREY),
    ROCK(Color.BROWN),
    EMPTY(Color.WHITE);

  final Color color;
  ViewElement(Color color) {
    this.color = color;
  }
}
