package it.polimi.ingsw.server.model;

/**
 * The color of the worker.
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
    public static Color StringToColor(String stringColor) {
        if (stringColor.equals("BLUE"))
            return BLUE;

        else if (stringColor.equals("WHITE"))
            return WHITE;

        else
            return BEIGE;
    }
}
