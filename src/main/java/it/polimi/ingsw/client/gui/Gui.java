package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/welcome.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/connect.fxml"));
            Parent root = FXMLLoader.load(getClass().getResource("/scenes/choose-num-of-players.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/choose-nickname.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/choose-color.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/start-player.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/choose-god.fxml"));
            //Parent root = FXMLLoader.load(getClass().getResource("/scenes/board.fxml"));

            Scene scene = new Scene(root);

            stage.setTitle("Santorini");
            stage.setScene(scene);

            stage.setResizable(false);
            //stage.setMinWidth(500);
            //stage.setMinHeight(330);

            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
