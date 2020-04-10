package it.polimi.ingsw.model;

public enum Color {
    BEIGE,
    WHITE,
    BLUE;

    public static Color StringtoColor(String stringColor){
        if(stringColor.equals("BLUE"))
            return BLUE;

        else if(stringColor.equals("WHITE"))
            return WHITE;

        else
            return BEIGE;
    }
}
