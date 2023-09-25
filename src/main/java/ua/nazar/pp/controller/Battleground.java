package ua.nazar.pp.controller;

import ua.nazar.pp.model.droids.Droid;
import ua.nazar.pp.view.TeamView;

public class Battleground {
    private final Team firstTeam;
    private final Team secondTeam;
    public static int rounds;


    public Battleground(Team firstTeam, Team secondTeam, long seed) {
        this.firstTeam = firstTeam;
        this.secondTeam = secondTeam;
        rounds = 0;
        Droid.setSeed(seed);
    }

    public ResultOfGame startBattle() {
        TeamView.displayStartBattle();
        TeamView.displayTeams(firstTeam,secondTeam);
        while (true) {
            rounds++;
            TeamView.displayRound(rounds);
            firstTeam.teamAttack(secondTeam);
            if(!secondTeam.isTeamAlive())return ResultOfGame.FIRST_TEAM_WIN;
            secondTeam.teamAttack(firstTeam);
            if(!firstTeam.isTeamAlive())return ResultOfGame.SECOND_TEAM_WIN;
            endOfRound();


        }
    }

    private void endOfRound() {
        prepareDroidsToTheNextRound();
        TeamView.displayTeamDroids(firstTeam);
        TeamView.displayTeamDroids(secondTeam);



    }

    private void prepareDroidsToTheNextRound() {
        firstTeam.setTeam(firstTeam.getTeam().stream().filter(Droid::isAlive).toList());
        secondTeam.setTeam(secondTeam.getTeam().stream().filter(Droid::isAlive).toList());
        firstTeam.chargeDroidsAndIncrementUltPoints();
        secondTeam.chargeDroidsAndIncrementUltPoints();
    }

    public String getFirstTeamName() {
        return firstTeam.getCommonName();
    }

    public String getSecondTeamName() {
        return secondTeam.getCommonName();
    }

    enum ResultOfGame {
        FIRST_TEAM_WIN,
        SECOND_TEAM_WIN
    }

}


