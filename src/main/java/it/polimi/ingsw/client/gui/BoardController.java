package it.polimi.ingsw.client.gui;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class BoardController {

    @FXML
    private GridPane board;

    public BoardController() {
    }

    @FXML
    private void chooseCell(MouseEvent event) {
        Node source = (Node)event.getSource();
        Integer colIndex = GridPane.getColumnIndex(source);
        Integer rowIndex = GridPane.getRowIndex(source);
        System.out.printf("Mouse clicked cell in [%d, %d]%n", rowIndex,colIndex);
    }
}