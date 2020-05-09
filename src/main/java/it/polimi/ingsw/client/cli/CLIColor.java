package it.polimi.ingsw.client.cli;

public enum CLIColor {
    ANSI_MALE_WORKER( " M⃣ "),
    ANSI_FEMALE_WORKER(" F⃣ "),
    ANSI_WHITE("\u001B[231m"),
    ANSI_RED("\u001B[31m"),
    ANSI_GREEN("\u001B[38;5;34m"),
    ANSI_YELLOW("\u001B[33m"),
    ANSI_BLUE("\u001B[34m"),
    ANSI_PURPLE("\u001B[35m"),
    ANSI_BEIGE("\u001b[38;5;94m"),
    Background_White("\u001b[48;5;7m"),
    Background_Beige("\u001b[48;5;94m"),
    Background_Black("\u001b[40m"),
    Background_Red("\u001b[41m"),
    Background_Green("\u001b[42m"),
    Background_Yellow("\u001b[43m"),
    Background_Blue("\u001b[44m"),
    Background_Magenta("\u001b[45m"),
    Background_Cyan("\u001b[46m"),
    Background_Bright_Black("\u001b[40;1m"),
    Background_Bright_Red("\u001b[41;1m"),
    Background_Bright_Green("\u001b[48;5;34m"),
    Background_Bright_Yellow("\u001b[43;1m"),
    Background_Bright_Blue("\u001b[44;1m"),
    Background_Bright_Magenta("\u001b[45;1m"),
    Background_Bright_Cyan("\u001b[46;1m"),
    Background_Bright_White("\u001b[47;1m");


    static final String COLOR_RESET = "\u001B[0m";
    static final String BACKGROUND_RESET = "\u001b[0m";

    String currentColor;

    CLIColor(String currentColor) {this.currentColor = currentColor;}

    public String getCurrentColor(){return currentColor;}


    @Override
    public String toString() {
        return currentColor;
    }
}
