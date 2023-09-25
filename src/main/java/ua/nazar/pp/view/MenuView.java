package ua.nazar.pp.view;

import ua.nazar.pp.controller.Team;
import ua.nazar.pp.model.Color;
import ua.nazar.pp.model.DroidFactory;
import ua.nazar.pp.model.DroidType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;


public class MenuView {
    private static final String menuColor = Color.CYAN.getASNIColor();
    private static final String errorColor = Color.RED.getASNIColor();
    public static void print(String string){
        System.out.print(menuColor+string);
    }
    public static void println(String string){
        System.out.println(menuColor+string);
    }
    public static void printError(String string){
        System.out.println(errorColor+string+Color.RESET.getASNIColor());
    }
    public static void displayMenu(){


        System.out.println(menuColor+"||:::::::::MENU:::::::::||");
        System.out.printf("%s %-20s %s%n","||","1. Start new game","||");
        System.out.printf("%s %-20s %s%n","||","2. Load saved game","||");
        System.out.printf("%s %-20s %s%n","||","3. Info","||");
        System.out.printf("%s %-20s %s%n","||","4. Exit","||");
        System.out.println("||::::::::::::::::::::::||");

    }
    public static void displayListOfExistedSaves(String saveDirPath) throws IOException {
        Files.list(Path.of(saveDirPath)).forEach(path -> System.out.println(path.getFileName().toString().split("\\.")[0])
        );
    }
    public static void displayExistedDroids(){
        DroidType[] droidTypes = DroidType.values();
        DroidFactory droidFactory = new DroidFactory();
        for (int i = 1; i <= droidTypes.length; i++) {
            System.out.println(i + ". " + droidFactory.createDroid(droidTypes[i-1]).getName());
        }
    }
    public static void displaySelectedTeam(Team team){
        println("Formed team:");
        println("Name: "+team.getName());
        println("Members:");
        for (int i = 0; i < team.getTeam().size(); i++) {
            println(i+1+". "+team.getTeam().get(i).getName());
        }

    }
    public static void displayWinner(String winner){
        List<Color> colors = Arrays.stream(Color.values()).limit(8).toList();
        String text =
                " /.:::::::::::::::::::::::::::::.\\\n"+
                "|::CONGRATULATION TO THE WINNERS::|\n" +
                        " \\':::::::::::::::::::::::::::::'/\n"+
                "     THE \""+winner+"\" TEAM WON";
        for (int i = 0; i < text.length() ; i++) {
            System.out.print(colors.get(i%8).getASNIColor()+text.charAt(i));
        }
        System.out.println();
    }
}
