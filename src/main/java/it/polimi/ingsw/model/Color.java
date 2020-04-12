package it.polimi.ingsw.model;

/**
 * The color of the worker.
 */
public enum Color {
    BEIGE,
    WHITE,
    BLUE;

    public static Color StringToColor(String stringColor) {
        if(stringColor.equals("BLUE"))
            return BLUE;

        else if(stringColor.equals("WHITE"))
            return WHITE;

        else
            return BEIGE;
    }
}
