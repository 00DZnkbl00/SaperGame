package com.example.sapergame;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
    int[][] mineFields;
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    @FXML
    private GridPane gridBoard;
    @FXML
    protected void onCheckTileClick(ActionEvent event) {
        Button button=(Button) event.getSource();
        int[] buttonCoordinates=getCoordinatesFromId(button.getId());
        if (mineCheck(buttonCoordinates)){
            button.setText("X");
            button.setDisable(true);
            alert.setTitle("Koniec gry");
            alert.setHeaderText("Przegrałeś!");
            alert.showAndWait();
            cleanTable();
        }
        else{
            button.setText(String.valueOf(nearbyMines(buttonCoordinates)));
            button.setDisable(true);
            if (gameEnd()){
                alert.setTitle("Koniec gry");
                alert.setHeaderText("Wygrałeś!");
                alert.showAndWait();
                cleanTable();
            }

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mineFields=new int[10][];

        for (int i = 0; i < mineFields.length; i++) {
            mineFields[i]=addMines();
        }
    }
    private int nearbyMines(int[] button){
        int mines=0;
        for (int i = button[0]-1; i <= button[0]+1 ; i++) {
            for (int j = button[1]-1; j <= button[1]+1 ; j++){
                if (mineCheck(new int[]{i,j}))mines++;
            }
        }
        return mines;
    }
    private boolean mineCheck(int[] button) {
        for (int[] mineField : mineFields) {
            if (mineField[0] == button[0] && mineField[1] == button[1]) return true;
        }
        return false;
    }
    private int[] getCoordinatesFromId(String string)
    {
        int a,b;
        String[] temp=string.split("b");
        a=Integer.parseInt(temp[1]);
        b=Integer.parseInt(temp[2]);
        return new int[]{a,b};
    }
    private int[] addMines()
    {
        int a,b;
        Random random=new Random();
        a=random.nextInt(0,8);
        b=random.nextInt(0,8);
        return new int[]{a,b};
    }
    private void cleanTable()
    {
        for(Node node: gridBoard.getChildren()) {
            if(node instanceof Button) {
                ((Button) node).setText("");
                node.setDisable(false);
            }
        }
    }
    private boolean gameEnd()
    {
        int[] temp;
        for(Node node: gridBoard.getChildren()) {
            if(node instanceof Button) {
                temp=getCoordinatesFromId(node.getId());
                if(!mineCheck(temp)&&!node.isDisable())
                    return false;
            }
        }
        return true;
    }
}