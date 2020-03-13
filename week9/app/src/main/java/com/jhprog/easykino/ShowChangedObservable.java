package com.jhprog.easykino;

import java.util.Observable;

public class ShowChangedObservable extends Observable {
    private static final ShowChangedObservable sco = new ShowChangedObservable();
    
    public static ShowChangedObservable getInstance() {
        return sco;
    }
    
    private ShowChangedObservable() {
    }

    public void iChanged() {
        setChanged();
        notifyObservers();
    }

}
