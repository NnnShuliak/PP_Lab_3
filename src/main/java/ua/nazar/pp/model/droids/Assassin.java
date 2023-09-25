package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.Mark;

import java.util.List;

public class Assassin extends Droid {

    public Assassin() {
        super("Shaco", 60, 45, 60, 80, 45, 4);
    }

    @Override
    public void attack(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        dealDamage(enemy);
    }

    @Override
    public void ultimate(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        int quantityOfMarks = (int)enemy.getMarks().stream().filter(mark -> mark.equals(Mark.SHACO_MARK)).count();
        if (quantityOfMarks >= 2) {
            enemy.takeDamage(10000);
        } else {
            dealDamage(enemy);
        }
    }

    @Override
    protected Droid chooseEnemy(List<Droid> enemyTeam) {
        return enemyTeam.get(enemyTeam.size()-1);
    }

    @Override
    protected void dealDamage(Droid droid) {
        int startAgility = droid.getAgility();
        droid.setAgility(startAgility / 2);
        if(droid.isDodged())return;
        droid.getMarked(Mark.SHACO_MARK);
        droid.takeDamage(getDamage());
        droid.setAgility(startAgility);

    }
    @Override
    public String getInfo() {
        return Color.RED.getASNIColor()+"""
                Shaco - is an assassin who specializes in dealing damage to one enemy. He has a small amount of health,
                but high agility and strength. His feature is that he can reduce the agility of his enemy by half for
                one turn, which increases the probability of his attack success. In addition, he can mark his enemies
                with a special mark, which allows him to instantly kill them, if the enemy has two or more such marks.
                His name is Shaco, and he attacks the enemy team from behind, quickly killing important targets."""
                +Color.RESET.getASNIColor();
    }



}
