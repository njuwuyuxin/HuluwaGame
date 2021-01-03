package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.utils.GameLogger;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
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

    @FXML
    public void handleReplayButton(ActionEvent event) throws IOException, InterruptedException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("打开游戏记录文件");
        fileChooser.setInitialDirectory(new File("."));
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("save files", "*.save")
        );
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(currentStage);
        if(file == null) return;
        GameLogger.readLog(file);
        GameManager.getInstance().isReplay = true;

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("Main.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent mainSceneRoot = fxmlLoader.load();

        Scene mainScene = new Scene(mainSceneRoot, 1400, 1000);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("mainScene.css").toExternalForm());
        currentStage.hide();
        currentStage.setScene(mainScene);
        MainController controller = fxmlLoader.getController();
        controller.initializeInReplayMode(mainScene);
        currentStage.show();
    }
}
