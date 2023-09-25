package ua.nazar.pp.model.droids;

import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.Mark;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Marksman extends Droid{

    private static final double percentDamage = 0.05;
    private static final double percentDamageWithMark = 0.1;
    private static final double markDamageBonus = 1.2;
    public Marksman(){
        super("Ezreal",70,30,15,100,30,8);
    }

    @Override
    public void attack(List<Droid> enemyTeam) {
        Droid enemy = chooseEnemy(enemyTeam);
        dealDamage(enemy);

    }

    @Override
    public void ultimate(List<Droid> enemyTeam) {
        enemyTeam.forEach(this::dealDamage);
    }

    @Override
    protected void dealDamage(Droid droid) {
        if(droid.isDodged())return;
        if(droid.isMarked(Mark.EZREAL_MARK)){
            droid.takeDamage((int) (droid.getHealth()*percentDamageWithMark+getDamage()*markDamageBonus));
            droid.removeMark(Mark.EZREAL_MARK);
        }else{
            droid.getMarked(Mark.EZREAL_MARK);
            droid.takeDamage((int) (droid.getHealth()*percentDamage+getDamage()));
        }
    }

    @Override
    protected Droid chooseEnemy(List<Droid> enemyTeam) {
        return enemyTeam.stream().filter(Droid::isAlive)
                .max(Comparator.comparingInt(Droid::getDamage))
                .orElseThrow(NoSuchElementException::new);
    }

    @Override
    public String getInfo() {
        return Color.YELLOW.getASNIColor()+"""
                Ezreal - is a marksman who specializes in dealing damage to enemies at a long distance. He has high
                strength, but average amount of health and agility. His feature is that he can mark his enemies with a
                special mark, which allows him to deal more damage depending on their current health. In addition, he
                can use his ultimate attack to attack the entire enemy team at once. He always attacks the droid in the
                enemy team who has the most strength."""+Color.RESET.getASNIColor();
    }
}
