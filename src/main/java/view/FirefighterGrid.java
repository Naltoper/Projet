package view;

import javafx.scene.canvas.Canvas;
import javafx.scene.paint.Color;
import javafx.util.Pair;
import util.Position;

import java.util.List;

public class FirefighterGrid extends Canvas implements Grid<ViewElement>{

    private void paintElementAtPosition(ViewElement element, Position position) {
        paintBox(position.row(), position.column(), element.color);
    }
    private int boxWidth;
    private int boxHeight;
    private int columnCount;
    private int rowCount;

    @Override
    public void repaint(List<Pair<Position, ViewElement>> positionedElements) {
        clear(positionedElements);
        paint(positionedElements);
        paintLines();
    }

    private void clear(List<Pair<Position, ViewElement>> positionedElements) {
        for (Pair<Position, ViewElement> positionElement : positionedElements) {
            Position position = positionElement.getKey();
            clearBox(position.row(), position.column());
        }
    }

    private void paint(List<Pair<Position, ViewElement>> positionedElements) {
        for(Pair<Position, ViewElement> pair : positionedElements){
            paintElementAtPosition(pair.getValue(), pair.getKey());
        }
    }

    @Override
    public void repaint(ViewElement[][] elements) {
        clear();
        paint(elements);
        paintLines();
    }

    private void clear() {
        getGraphicsContext2D().clearRect(0,0,getWidth(), getHeight());
    }

    private void paint(ViewElement[][] elements) {
        for(int column = 0; column < columnCount; column++)
            for(int row = 0; row < rowCount; row++){
                paintElementAtPosition(elements[row][column], new Position(row, column));
            }
    }

    public int columnCount() {
        return columnCount;
    }

    public int rowCount() {
        return rowCount;
    }

    @Override
    public void setDimensions(int columnCount, int rowCount, int boxWidth, int boxHeight) {
        this.boxWidth = boxWidth;
        this.boxHeight = boxHeight;
        this.columnCount = columnCount;
        this.rowCount = rowCount;
        super.setWidth(boxWidth * columnCount);
        super.setHeight(boxHeight * rowCount);
    }

    private void paintLines(){
        paintHorizontalLines();
        paintVerticalLines();
    }

    private void paintVerticalLines() {
        for(int column = 0; column < columnCount; column++)
            getGraphicsContext2D().strokeLine(column * boxWidth, 0,column * boxWidth, getHeight());
    }

    private void paintHorizontalLines() {
        for(int row = 0; row < rowCount; row++)
            getGraphicsContext2D().strokeLine(0, row * boxHeight, getWidth(), row * boxHeight);
    }

    private void paintBox(int row, int column, Color color){
        getGraphicsContext2D().setFill(color);
        getGraphicsContext2D().fillRect(column * boxWidth,row * boxHeight, boxWidth, boxHeight);
    }

    private void clearBox(int row, int column){
        getGraphicsContext2D().clearRect(column * boxWidth,row * boxHeight, boxWidth, boxHeight);
    }
}