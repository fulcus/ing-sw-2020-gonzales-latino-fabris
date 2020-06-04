package it.polimi.ingsw.server.model;

/**
 * Defines the available colors in the game.
 * Every Player and his workers will be characterized by one of them.
 */
public enum Color {
    BEIGE,
    WHITE,
    BLUE;


    /**
     * Given a string returns the Color.
     *
     * @param stringColor string representation of the color
     * @return color object
     */
    public static Color stringToColor(String stringColor) {
        if (stringColor.equals("BLUE"))
            return BLUE;

        else if (stringColor.equals("WHITE"))
            return WHITE;

        else
            return BEIGE;
    }
}
