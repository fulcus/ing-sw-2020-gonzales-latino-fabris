package it.polimi.ingsw.client.gui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;


/**
 * Graphic User Interface chosen by the player to run the app.
 */
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
        //ImageCursor cursor = new ImageCursor(new Image("/labels/cursor_rotated.png"));
        //scene.setCursor(cursor);
        stage.setTitle("Santorini");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();

        try {
            GuiManager.queue.put("done");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    protected static Stage getStage() {
        return stage;
    }

}
