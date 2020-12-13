package com.edu.nju.huluwa.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;

public class MainController {
    private Button currentButton;
    private int currentBoyIndex;

    @FXML
    public void handlerBtnClick(ActionEvent event) {
        Button btnSource = (Button) event.getSource();
        btnSource.setText("I am clicked!");
    }

    @FXML
    public void boy1Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 1;
    }
    @FXML
    public void boy2Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 2;
    }
    @FXML
    public void boy3Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 3;
    }
    @FXML
    public void boy4Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 4;
    }
    @FXML
    public void boy5Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 5;
    }
    @FXML
    public void boy6Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 6;
    }
    @FXML
    public void boy7Clicked(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        currentButton = btnSource;
        currentBoyIndex = 7;
    }

    @FXML
    public void gridClicked(MouseEvent event) {
        //cal grid position with mouse position
        int clickCol = (int)event.getSceneX()/100;
        int clickRow = (int)event.getSceneY()/100;
        System.out.println(event.getSceneX()+" "+event.getSceneY());
        if(currentButton!=null) {
            GridPane.setColumnIndex(currentButton, clickCol);
            GridPane.setRowIndex(currentButton, clickRow);
        }
        currentButton = null;
    }
}
