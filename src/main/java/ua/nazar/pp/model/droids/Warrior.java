package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Color;

import java.util.List;

public class Warrior extends Droid {
    private static final double steelHpPercent = 0.1;

    public Warrior() {
        super("Alucard", 150, 20, 20, 120, 45, 8);
        setHealth(100);
    }

    @Override
    public void attack(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        dealDamage(enemy);
    }

    @Override
    public void ultimate(List<Droid> enemyTeam) {
        int currentDamage = getDamage();
        for (Droid enemy : enemyTeam) {
            dealDamage(enemy);
            setDamage(getDamage() / 2);
        }
        setDamage(currentDamage);
    }

    @Override
    protected void dealDamage(Droid enemy) {
        if(enemy.isDodged()) return;
        int startHealth = enemy.getHealth();
        enemy.takeDamage(getDamage());
        int healthAfterAttack = enemy.getHealth();
        int healthDifference = startHealth - healthAfterAttack;
        if (healthDifference > 0) {
            heal((int) ((healthDifference)/2+startHealth*steelHpPercent));
        }

    }

    @Override
    protected Droid chooseEnemy(List<Droid> enemyTeam) {
        Droid enemy = enemyTeam.get(0);
        if (enemyTeam.size() > 1) {
            enemy = enemyTeam.get(0).getHealth() > enemyTeam.get(1).getHealth()
                    ? enemyTeam.get(0) : enemyTeam.get(1);
        }
        return enemy;

    }

    @Override
    public String getInfo() {
        return Color.DARK_GREEN.getASNIColor() + """
                Alucard - is a warrior who specializes in close combat with enemies. He has a large maximum health,
                but a low strength and a decent agility. His feature is that he can restore his health by dealing damage
                to enemies. In addition, he can use his ultimate attack to attack the entire enemy team in turn, but
                with each subsequent attack his strength decreases. He attacks the enemy with
                the highest amount of health, but he can only choose from the first two.""" + Color.RESET.getASNIColor();
    }

}
