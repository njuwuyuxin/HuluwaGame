package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Scanner;

public class MainMenuController {
    @FXML
    public void handleGameStartButton(ActionEvent event) throws IOException {
        Button btnSource = (Button) event.getSource();
        System.out.println("press start Game");

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("NetSetting.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent netSettingRoot = fxmlLoader.load();

        Scene netSettingScene = new Scene(netSettingRoot, 500, 180);
        netSettingScene.getStylesheets().add(getClass().getClassLoader().getResource("NetSetting.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.hide();
        currentStage.setScene(netSettingScene);
        NetSettingController controller = fxmlLoader.getController();
        controller.initialize(netSettingScene);
        currentStage.show();

    }

    @FXML
    public void handleGameExitButton(ActionEvent event){
        Platform.exit();
    }
}
