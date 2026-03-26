package util;

import model.BoardElement;
import model.Extinguishable;
import model.MobileElement;

import java.util.List;
import java.util.Map;

public interface ImmobileUpdateStrategy<T> {
    List<Position> immobileStrategy(T element, Extinguishable extinguishable);
}
