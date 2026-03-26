package model;

import util.Position;

import java.util.*;

public interface MobileElement extends BoardElement {
    void setNewPositions(List<Position> newPositions);
}
