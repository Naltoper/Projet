package model;

import view.ViewElement;

public enum ModelElement {
    FIREFIGHTER(ViewElement.FIREFIGHTER),
    FIRE(ViewElement.FIRE),
    CLOUD(ViewElement.CLOUD),
    MOTORISEDFIREFIGHTER(ViewElement.MOTORISEDFIREFIGHTER),
    MOUNTAIN(ViewElement.MOUNTAIN),
    ROAD(ViewElement.ROAD),
    ROCK(ViewElement.ROCK);

    public final ViewElement viewElement;
    ModelElement(ViewElement viewElement) {
        this.viewElement = viewElement;
    }
}
