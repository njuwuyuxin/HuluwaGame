package com.edu.nju.huluwa.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class MainMenuController {
    @FXML
    public void handleGameStartButton(ActionEvent event) throws IOException {
        Button btnSource = (Button) event.getSource();
        System.out.println("press start Game");

        Parent mainSceneRoot = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        Scene mainScene = new Scene(mainSceneRoot, 1000, 1000);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("mainScene.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.hide();
        currentStage.setScene(mainScene);
        currentStage.show();
    }

    @FXML
    public void handleGameExitButton(ActionEvent event){
        Platform.exit();
    }
}
