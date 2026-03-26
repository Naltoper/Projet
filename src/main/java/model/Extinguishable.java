package model;

import util.Position;

/**
 *  Interface pour les elements eteignable, qu'on voudrait pouvoir suivre (ex: Fire)
 */
public interface Extinguishable extends BoardElement{
    /**
     *  Identique a remove mais utile pour la clareté du code,
     *  celle ci s'utilise precisemment quand un element eteint un feu
     */
    boolean extinguish(Position position);
}
