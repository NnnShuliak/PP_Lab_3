package ua.nazar.pp.controller;

import ua.nazar.pp.model.*;
import ua.nazar.pp.model.droids.*;
import ua.nazar.pp.view.MenuView;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Game {
    private static final long seed = new Random().nextLong();
    private static final Save save = new Save();
    private static final DroidFactory droidFactory = new DroidFactory();
    private static final DroidType[] droidTypes = DroidType.values();

    private static final String saveDirPath = "saves\\";

    public static void main(String[] args) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            MenuView.displayMenu();
            while (true) {
                MenuView.print("Your choose: ");
                int menuFunc;
                try {
                    menuFunc = Integer.parseInt(reader.readLine());
                } catch (Exception e) {
                    continue;
                }
                if (menuFunc < 1 || menuFunc > 4) {
                    MenuView.printError("Incorrect input. Try Again");
                    continue;
                }
                switch (menuFunc) {
                    case 1 -> startGame();
                    case 2 -> loadGame();
                    case 3 -> showDroidsInfo();
                    case 4 -> System.exit(1);
                }
                break;
            }
        }
    }


    private static void showDroidsInfo() {
        MenuView.println("List of droids:");
        MenuView.displayExistedDroids();
        while (true) {
            MenuView.print("Choose about which droid you wanna watch information: ");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            try {
                DroidType droidType = droidTypes[Integer.parseInt(reader.readLine()) - 1];
                MenuView.println(droidFactory.createDroid(droidType).getInfo());
            } catch (Exception e) {
                break;
            }
        }
    }

    private static void startGame() throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        MenuView.print("Enter name of first team: ");
        String name = reader.readLine();
        save.firstTeamName = name;
        Team firstTeam = new Team(selectingDroids(), name, Color.BLUE);
        MenuView.displaySelectedTeam(firstTeam);

        MenuView.print("Enter name of second team: ");
        name = reader.readLine();
        save.secondTeamName = name;
        Team secondTeam = new Team(selectingDroids(), name, Color.RED);
        MenuView.displaySelectedTeam(secondTeam);

        Battleground battleground = new Battleground(firstTeam, secondTeam, seed);

        String winner = battleground.startBattle() == Battleground.ResultOfGame.FIRST_TEAM_WIN ?
                battleground.getFirstTeamName() : battleground.getSecondTeamName();
        MenuView.displayWinner(winner);

        MenuView.print("Do you wanna to save this game? Enter \"+\" if you want: ");

        if (reader.readLine().equals("+")) {
            MenuView.println("List of existed saves");
            MenuView.displayListOfExistedSaves(saveDirPath);
            MenuView.print("Enter new save name to make one or rewrite existed: ");
            saveGame(reader.readLine());

        }
    }

    private static void loadGame() throws IOException {
        MenuView.println("List of existed saves");
        MenuView.displayListOfExistedSaves(saveDirPath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            MenuView.print("Choose save: ");
            String saveName = reader.readLine();
            if (Files.exists(Paths.get(saveDirPath + saveName + ".txt"))) {
                Battleground battleground = loadSave(saveName);
                String winner = battleground.startBattle() == Battleground.ResultOfGame.FIRST_TEAM_WIN ?
                        battleground.getFirstTeamName() : battleground.getSecondTeamName();
                MenuView.displayWinner(winner);
                return;
            }
            MenuView.printError("The file doesn't exist. Try again");
        }

    }

    private static Battleground loadSave(String saveName) throws IOException {

        List<String> fileInfo = Files.readAllLines(Paths.get(saveDirPath + saveName + ".txt"));

        Team firstTeam = new Team(Objects.requireNonNull(formTeamBySelectedTypes(fileInfo.get(1).split(" "))), fileInfo.get(0), Color.BLUE);
        Team secondTeam = new Team(Objects.requireNonNull(formTeamBySelectedTypes(fileInfo.get(3).split(" "))), fileInfo.get(2), Color.RED);

        return new Battleground(firstTeam, secondTeam, Long.parseLong(fileInfo.get(4)));
    }

    private static void saveGame(String saveName) throws Exception {
        saveName = saveName + ".txt";
        File file = new File(saveDirPath + saveName);
        try (BufferedWriter fileWriter = new BufferedWriter(new FileWriter(file))) {
            fileWriter.write(save.toString());
        }
    }


    private static List<Droid> selectingDroids() throws IOException {
        boolean isFirstTeammatesNull = save.firstTeammates == null;
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        MenuView.displayExistedDroids();
        while (true) {
            MenuView.print("Choose droids: ");
            String chosenDroids = reader.readLine();
            if (save.firstTeammates == null) {
                save.firstTeammates = chosenDroids;
            } else {
                save.secondTeammates = chosenDroids;
            }
            String[] droids = chosenDroids.split(" ");
            List<Droid> formedTeam = formTeamBySelectedTypes(droids);
            if (formedTeam == null) {
                if (isFirstTeammatesNull) save.firstTeammates = null;
                MenuView.printError("""
                        You must select the droids as indicated in the example.
                        Example: print "3 1 4" to select Gnar, Alucard and Ezreal
                        You cannot choose two identical droids. Try again""");
                continue;
            }
            return formedTeam;
        }


    }

    private static List<Droid> formTeamBySelectedTypes(String[] selectedTypes) {

        List<Droid> formedTeam = new ArrayList<>();
        try {
            for (String type : selectedTypes) {
                Droid newDroid = droidFactory.createDroid(droidTypes[(Integer.parseInt(type) - 1)]);
                if (formedTeam.contains(newDroid)) return null;
                formedTeam.add(newDroid);
            }
        } catch (Exception e) {
            return null;
        }
        return formedTeam;
    }

    private static class Save {
        private String firstTeamName;
        private String secondTeamName;
        private String firstTeammates;
        private String secondTeammates;

        @Override
        public String toString() {

            return firstTeamName + "\n" +
                    firstTeammates + "\n" +
                    secondTeamName + "\n" +
                    secondTeammates + "\n" +
                    seed;
        }
    }
}
