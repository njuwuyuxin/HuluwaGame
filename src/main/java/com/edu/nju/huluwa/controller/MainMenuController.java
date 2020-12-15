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
        System.out.println("start create net connect");

        System.out.println("input 1 to create a server, 2 to create a client");
        int cmd=0;
        Scanner input = new Scanner(System.in);
        cmd = input.nextInt();
        if(cmd==1){
            GameManager.getInstance().createServer();
        }
        else if(cmd==2){
            System.out.println("input target ip and port");
            String ip = input.next();
            int port = input.nextInt();
            GameManager.getInstance().createClient(ip,port);
        }


        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getClassLoader().getResource("Main.fxml"));
        fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
        Parent mainSceneRoot = fxmlLoader.load();

        Scene mainScene = new Scene(mainSceneRoot, 1000, 1000);
        mainScene.getStylesheets().add(getClass().getClassLoader().getResource("mainScene.css").toExternalForm());
        Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        currentStage.hide();
        currentStage.setScene(mainScene);
        MainController controller = fxmlLoader.getController();
        controller.initialize(mainScene);
        currentStage.show();


    }

    @FXML
    public void handleGameExitButton(ActionEvent event){
        Platform.exit();
    }
}
