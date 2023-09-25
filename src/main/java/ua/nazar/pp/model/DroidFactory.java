package ua.nazar.pp.model;

import ua.nazar.pp.model.droids.*;

public class DroidFactory {
    public Droid createDroid(DroidType droidType){
        Droid droid = null;
        switch (droidType){
            case MAGE -> droid = new Mage();
            case TANK -> droid = new Tank();
            case WARRIOR -> droid = new Warrior();
            case ASSASSIN -> droid = new Assassin();
            case MARKSMAN -> droid = new Marksman();
        }
        return droid;
    }
}
