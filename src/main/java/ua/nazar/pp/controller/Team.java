package ua.nazar.pp.controller;

import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.droids.Droid;
import ua.nazar.pp.view.TeamView;

import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;

public class Team {
    private final String name;
    private List<Droid> team;
    private final String commonName;

    public Team(List<Droid> team, String name,Color color) {
        this.commonName = name;
        this.name = color.getASNIColor()+name+Color.RESET.getASNIColor();
        this.team = team;
        team.forEach(droid -> droid.setName(color.getASNIColor() + droid.getName() + Color.RESET.getASNIColor()));
    }


    public void teamAttack(Team enemyTeam) {

        if (canTeamAttack()) {

            TeamView.displayTeamAttack(this);
            Droid attacker = chooseAttacker();
            if (attacker.canUseUlt()) {
                TeamView.displayUltimate(attacker);
                attacker.ultimate(enemyTeam.getTeam());
                attacker.spendUltPoints();
            } else {
                TeamView.displayAttack(attacker);
                attacker.attack(enemyTeam.getTeam());
            }
        } else {
            TeamView.displayTeamSkipRound(this);
        }
    }

    public boolean isTeamAlive() {
        return team.stream().anyMatch(Droid::isAlive);
    }


    public void chargeDroidsAndIncrementUltPoints() {
        team.stream()
                .filter(Droid::isAlive)
                .forEach(droid -> {
                    droid.charge(20);
                    droid.setCurrentUltPoints(droid.getCurrentUltPoints() + 1);
                });
    }

    private Droid chooseAttacker() {
        Droid attacker = team.stream().filter(Droid::canAttack)
                .filter(Droid::canUseUlt).max(Comparator.comparingInt(Droid::getDamage))
                .orElse(null);
        if (attacker == null) {
            attacker = team.stream().filter(Droid::canAttack)
                    .max(Comparator.comparingInt(Droid::getDamage))
                    .orElseThrow(NoSuchElementException::new);
        }
        attacker.spendEnergy();
        return attacker;
    }

    private boolean canTeamAttack() {
        return team.stream().anyMatch(Droid::canAttack);
    }

    public List<Droid> getTeam() {
        return team;
    }

    public void setTeam(List<Droid> team) {
        this.team = team;
    }

    public String getName() {
        return name;
    }

    public String getCommonName() {
        return commonName;
    }


}
