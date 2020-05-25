package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.model.Color;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

public class ColorSetting {

    public ColorSetting() {
    }

    protected synchronized boolean checkColorValidity(String chosenColor, ViewClient client, GameController gc) {

        if (gc.colorIsAvailable(chosenColor) && gc.colorIsValid(chosenColor)) {
            client.notifyValidColor();
            client.getPlayer().setColor(Color.stringToColor(chosenColor));
            return true;
        }

        return false;
    }


}
