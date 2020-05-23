package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
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
        Gui.stage = stage;

        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("/scenes/welcome.fxml"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        stage.setTitle("Santorini");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

    }

    protected static Stage getStage() {
        return stage;
    }

    /*
    protected static void setScene(String fxmlPath) {
        Parent root = null;
        try {
            root = FXMLLoader.load(Gui.class.getResource(fxmlPath));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Scene scene = new Scene(root);
        stage.setScene(scene);

    }
    */


}
