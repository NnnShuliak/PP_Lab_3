package ua.nazar.pp.model;

public enum Color {
    RED("\u001B[31m"),
    ORANGE("\u001B[38;5;214m"),
    YELLOW("\u001B[38;5;11m"),
    LIGHT_GREEN("\u001B[38;5;118m"),
    LIGHT_BLUE("\u001B[38;5;45m"),
    BLUE("\u001B[34m"),
    DARK_BLUE("\u001B[38;5;20m"),
    INDIGO("\u001B[38;5;54m"),
    VIOLET("\u001B[38;5;93m"),


    RESET("\u001B[0m"),
    GREEN("\u001B[38;5;83m"),
    DARK_GREEN("\u001B[38;5;2m"),
    BROWN("\u001B[38;5;130m"),
    PURPLE("\u001B[38;5;5m"),
    CYAN("\u001B[38;5;14m");

    private final String ASNIColor;
    Color(String ASNIColor){
        this.ASNIColor = ASNIColor;

    }

    public String getASNIColor() {
        return ASNIColor;
    }
}
