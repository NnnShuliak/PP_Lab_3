package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.Mark;

import ua.nazar.pp.view.TeamView;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Tank extends Droid {
    private boolean isTransformed;

    public Tank() {
        super("Gnar", 70, 10, 20, 100, 20, 100000);
        isTransformed = false;
    }

    @Override
    public void attack(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        dealDamage(enemy);
    }

    @Override
    public void ultimate(List<Droid> enemyTeam) {
        List<Droid> enemiesToUlt = enemyTeam.stream()
                .limit(2).toList();
        dealDamage(enemiesToUlt.get(0));
        enemiesToUlt.get(0).getMarked(Mark.GIGACHAD_GNAR_MARK);
        if (enemiesToUlt.size() == 2) {
            setDamage(getDamage()*2);
            dealDamage(enemiesToUlt.get(1));
            setDamage(getDamage()/2);
            enemiesToUlt.get(1).getMarked(Mark.GIGACHAD_GNAR_MARK);
            enemiesToUlt.get(1).getMarked(Mark.GIGACHAD_GNAR_MARK);

        }

    }

    @Override
    protected void dealDamage(Droid droid) {
        if (droid.isDodged()) return;
        droid.takeDamage(getDamage());
    }

    @Override
    protected Droid chooseEnemy(List<Droid> enemyTeam) {
        if(isTransformed){
            return enemyTeam.get(0);
        }
        return enemyTeam.stream()
                .min(Comparator.comparingInt(Droid::getHealth))
                .orElseThrow(NoSuchElementException::new);
    }

    private void transformIntoGigaGnar() {
        TeamView.print(getName()+" transform into ");
        String[] colors = getName().split("Gnar");
        setName(colors[0]+"Gigachad Gnar"+colors[1]);
        TeamView.print(getName()+"\n");
        setMaxHealth(200);
        setHealth(200);
        setDamage(13);
        setAgility(1);
        setCurrentEnergy(150);
        setMaxEnergy(150);
        setEnergyPerAttack(40);
        setUltPointsToUseUlt(7);
        setCurrentUltPoints(0);
        setAlive(true);
        isTransformed = true;

    }

    @Override
    public void setHealth(int health) {
        super.setHealth(health);
        if (getHealth() < getMaxHealth() * 0.3 && !isTransformed) {
            transformIntoGigaGnar();
        }

    }

    @Override
    public String getInfo() {
        return Color.BROWN.getASNIColor()+"""
                Gnar - is a tank who specializes in taking a lot of damage and helping allies attack the enemy. He
                has high health, but low strength and speed. His feature is that he can transform into Gigachad Gnar
                when his health falls below a certain level. In this state, he becomes stronger, bigger and more
                aggressive. In addition, he can use his ultimate attack to attack up to two enemies at once and mark
                them with a special mark that prevents them from dodging his next attack. Before transforming, he hits
                the target with the lowest health, but when he becomes Gigachad Gnar, he only hits the first target."""
                +Color.RESET.getASNIColor();
    }
}
