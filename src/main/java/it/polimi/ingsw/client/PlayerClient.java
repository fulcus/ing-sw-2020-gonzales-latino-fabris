package it.polimi.ingsw.client;

/**
 * Allows to store in the client information about the players.
 */

public class PlayerClient {

    private String nickname;
    private String color;
    private String god;

    public PlayerClient(String nickname, String color) {
        this.nickname = nickname;
        this.color = color;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getGod() {
        return god;
    }

    public void setGod(String god) {
        this.god = god;
    }
}
