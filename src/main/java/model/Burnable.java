package model;

import util.Position;

import java.util.Map;

public interface Burnable extends BoardElement {
    int getPropagationDelay();
    Map<Position, Integer> getRockFireCountersMap();
}
