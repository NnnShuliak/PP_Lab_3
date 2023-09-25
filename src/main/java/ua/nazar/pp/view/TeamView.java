package ua.nazar.pp.view;

import ua.nazar.pp.controller.Team;
import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.droids.Droid;

public class TeamView {
    public static void displayDeath(Droid droid){
        System.out.println(droid.getName()+" die ☠️");
    }
    public static void displayDodging(Droid droid){
        System.out.println(droid.getName()+" dodged🤸‍♂️");
    }
    public static void displayHit(Droid droid){
        System.out.println(droid.getName()+ " was hit \uD83E\uDE78");
    }
    public static void print(String string){
        System.out.print(string);
    }

    public static void displayStartBattle() {
        System.out.println(Color.GREEN.getASNIColor() + "!!! START !!!" + Color.RESET.getASNIColor());
    }

    public static void displayRound(int round) {
        System.out.println(Color.GREEN.getASNIColor() + "ROUND " + round + Color.RESET.getASNIColor());
    }

    public static void displayTeamAttack(Team team) {
        System.out.println("\"" + team.getName() + "\"  attack ");
    }

    public static void displayTeamSkipRound(Team team) {
        System.out.println("\"" + team.getName() + "\" don`t have energy to attack \uD83D\uDD0C");
    }

    public static void displayAttack(Droid droid) {
        System.out.println(droid.getName() + " attacks ⚔️");
    }

    public static void displayUltimate(Droid droid) {
        System.out.println(droid.getName() + " ults \uD83D\uDCA5");
    }

    public static void displayTeamDroids(Team team) {
        System.out.println(String.format("%42s", "").replace(' ', '—'));
        System.out.printf("%-15s %-5s %-5s %-5s %-5s %-5s%n",
                "Name", "❤️", "\uD83D\uDDE1️", " 🦵", " \uD83D\uDD0B", " \uD83C\uDF1F");
        for (Droid droid : team.getTeam()) {
            System.out.printf("%-24s %-5s %-5s %-5d %-5d %-5d%n",
                    droid.getName(), droid.getHealth(), droid.getDamage(), droid.getAgility(), droid.getCurrentEnergy(), droid.getCurrentUltPoints());
        }
        System.out.println(String.format("%42s", "").replace(' ', '—'));
    }

    public static void displayTeams(Team firstTeam, Team secondTeam) {
        System.out.println("\"" + firstTeam.getName() + "\"");
        displayTeamDroids(firstTeam);
        System.out.println("\"" + secondTeam.getName() + "\"");
        displayTeamDroids(secondTeam);
    }


}
