package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Mark;
import ua.nazar.pp.view.TeamView;

import java.util.*;

public abstract class Droid {
    private static Random random;
    private String name;
    private int health;
    private int maxHealth;
    private int damage;
    private int agility;
    private int currentEnergy;
    private int energyPerAttack;
    private int maxEnergy;
    private boolean isAlive;
    private int ultPointsToUseUlt;
    private int currentUltPoints;
    private final List<Mark> marks;


    protected Droid(String name, int maxHealth, int damage, int agility, int maxEnergy, int energyPerAttack, int ultPointsToUseUlt) {
        this.name = name;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.damage = damage;
        this.agility = agility;
        this.maxEnergy = maxEnergy;
        this.currentEnergy = maxEnergy;
        this.energyPerAttack = energyPerAttack;
        this.isAlive = true;
        this.ultPointsToUseUlt = ultPointsToUseUlt;
        this.currentUltPoints = 0;
        marks = new ArrayList<>();
    }

    public abstract void attack(List<Droid> enemyTeam);

    public abstract void ultimate(List<Droid> enemyTeam);

    protected abstract void dealDamage(Droid droid);

    protected abstract Droid chooseEnemy(List<Droid> enemyTeam);

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public void takeDamage(int damage) {
        setHealth(getHealth() - damage);
        currentUltPoints += 2;
        if (health <= 0) {
            health = 0;
            isAlive = false;
            TeamView.displayDeath(this);
        }
    }

    public void charge(int quantityOfEnergy) {
        currentEnergy += quantityOfEnergy;
        if (currentEnergy > maxEnergy) currentEnergy = maxEnergy;
    }

    public void heal(int hp) {
        health += hp;
        if (health > maxHealth) health = maxHealth;
    }

    public void setHealth(int health) {
        this.health = health;
        if (this.health < 0) {
            this.health = 0;
            isAlive = false;
        }
    }

    public boolean isDodged() {
        boolean result;
        if (isMarked(Mark.GIGACHAD_GNAR_MARK) || Math.abs(random.nextInt()) % 100 + 1 > agility) {
            removeMark(Mark.GIGACHAD_GNAR_MARK);
            TeamView.displayHit(this);
            result = false;
        } else {
            TeamView.displayDodging(this);
            result = true;
        }
        return result;
    }

    public boolean canAttack() {
        return currentEnergy >= energyPerAttack && isAlive;
    }

    public boolean canUseUlt() {
        return currentUltPoints >= ultPointsToUseUlt && isAlive;
    }

    public void spendEnergy() {
        currentEnergy -= energyPerAttack;
    }

    public void spendUltPoints() {
        currentUltPoints -= ultPointsToUseUlt;
        if (currentUltPoints < 0) currentUltPoints = 0;
    }

    public boolean isMarked(Mark mark) {
        return marks.contains(mark);
    }

    public void removeMark(Mark mark) {
        marks.remove(mark);
    }

    public void getMarked(Mark mark) {
        marks.add(mark);
    }


    public List<Mark> getMarks() {
        return marks;
    }


    public void setUltPointsToUseUlt(int ultPointsToUseUlt) {
        this.ultPointsToUseUlt = ultPointsToUseUlt;
    }

    public int getCurrentUltPoints() {
        return currentUltPoints;
    }

    public void setCurrentUltPoints(int currentUltPoints) {
        this.currentUltPoints = currentUltPoints;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public int getAgility() {
        return agility;
    }

    public void setAgility(int agility) {
        this.agility = agility;
    }

    public int getCurrentEnergy() {
        return currentEnergy;
    }

    public void setCurrentEnergy(int currentEnergy) {
        this.currentEnergy = currentEnergy;
    }

    public void setEnergyPerAttack(int energyPerAttack) {
        this.energyPerAttack = energyPerAttack;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }

    public int getMaxHealth() {
        return maxHealth;
    }
    public void setMaxHealth(int maxHealth) {
        this.maxHealth = maxHealth;
    }
    public int getMaxEnergy() {
        return maxEnergy;
    }
    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public int getHealth() {
        return health;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Droid droid = (Droid) o;
        return health == droid.health && maxHealth == droid.maxHealth && damage == droid.damage && agility == droid.agility && currentEnergy == droid.currentEnergy && energyPerAttack == droid.energyPerAttack && maxEnergy == droid.maxEnergy && ultPointsToUseUlt == droid.ultPointsToUseUlt && currentUltPoints == droid.currentUltPoints && Objects.equals(name, droid.name);
    }
    public abstract String getInfo();

}
