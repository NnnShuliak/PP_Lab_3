package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Color;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Mage extends Droid {
    private static final double energyStealPercent = 0.2;
    private static final double energyStealBonus = 20;
    private static final double energyDamagePercent = 0.15;

    public Mage() {
        super("Kassadin", 75, 23, 10, 200, 80, 9);
        setCurrentEnergy(100);
    }

    @Override
    public void attack(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        stealEnergy(enemy);
        dealDamage(enemy);
    }

    @Override
    public void ultimate(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        stealAllEnergy(enemy);
    }

    @Override
    protected void dealDamage(Droid droid) {
        if (droid.isDodged()) return;
        droid.takeDamage((int) (getDamage() + getCurrentEnergy() * energyDamagePercent));
    }

    @Override
    protected Droid chooseEnemy(List<Droid> enemyTeam) {
        return enemyTeam.stream()
                .max(Comparator.comparingInt(Droid::getCurrentEnergy))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String getInfo() {
        return Color.PURPLE.getASNIColor()+"""
                Kassadin - is a mage who specializes in stealing energy from enemies. He has average health and
                strength, but low agility and high amount of maximum energy. His feature is that he can increase his
                current energy by using the energy of his enemies, and deal damage depending on his current energy.
                In addition, he can use his ultimate attack to completely suck out the energy of one enemy and deal
                damage to him if it exceeds the maximum level of energy of the mage. He always attacks the droid in
                the enemy team who has the most current energy"""+Color.RESET.getASNIColor();
    }

    private void stealEnergy(Droid droid) {
        if (droid.getCurrentEnergy() * (1 - energyStealPercent) < energyStealBonus) {
            charge(droid.getCurrentEnergy());
            droid.setCurrentEnergy(0);
        } else {
            int startEnergy = droid.getCurrentEnergy();
            droid.setCurrentEnergy((int) (droid.getCurrentEnergy() * (1 - energyStealPercent) - energyStealBonus));
            charge(startEnergy - getCurrentEnergy());
        }
    }

    private void stealAllEnergy(Droid droid) {
        if (droid.isDodged()) return;
        int damage = getMaxEnergy() - (getCurrentEnergy() + droid.getCurrentEnergy());
        if (damage > 0) {
            droid.takeDamage(damage);
        }
        charge(droid.getCurrentEnergy());
        droid.setCurrentEnergy(0);
    }
}
