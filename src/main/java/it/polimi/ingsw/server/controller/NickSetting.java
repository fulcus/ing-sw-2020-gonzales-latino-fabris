package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.server.ViewClient;
import it.polimi.ingsw.server.model.Game;
import it.polimi.ingsw.server.model.Player;

public class NickSetting {

    public NickSetting() {
    }


    protected synchronized boolean checkNicknameValidity(String chosenNickname, ViewClient client, Game game, GameController gc) {

        if (gc.nicknameIsAvailable(chosenNickname) && chosenNickname.length() > 0) {
            Player newPlayer = game.addPlayer(chosenNickname, client);
            client.setPlayer(newPlayer);
            client.notifyValidNick();
            return true;
        }

        return false;
    }
}
