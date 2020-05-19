package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {

    private static Stage stage;

    public static void main() {
        launch();
    }

    @Override
    public void start(Stage stage) {
        try {
            Gui.stage = stage;

            Parent root = FXMLLoader.load(getClass().getResource("/scenes/welcome.fxml"));

            Scene scene = new Scene(root);

            stage.setTitle("Santorini");
            stage.setScene(scene);
            stage.setResizable(false);
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected static Stage getStage() {
        return stage;
    }


}
