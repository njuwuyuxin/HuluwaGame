package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import java.io.IOException;

public class NetSettingController {
    private Scene selfScene;

    @FXML
    public void initialize(Scene scene){
        selfScene = scene;
        ((Label)selfScene.lookup("#serverIP")).setText(GameManager.getInstance().getNetClient().getLocalAddr());
        ((Label)selfScene.lookup("#serverPort")).setText(Integer.toString(GameManager.getInstance().getNetClient().getLocalPort()));
    }

    @FXML
    public void handleCreateServerButton(ActionEvent event) throws IOException {
        GameManager.getInstance().createServer();
        loadMainScene(event);
    }

    @FXML
    public void handleCreateClientButton(ActionEvent event) throws IOException {
        String ip = ((TextField)((Node)event.getSource()).getScene().lookup("#targetIP")).getText();
        int port = Integer.valueOf(((TextField)((Node)event.getSource()).getScene().lookup("#targetPort")).getText());
        System.out.println(ip+" "+port);
        GameManager.getInstance().createClient(ip,port);
        loadMainScene(event);
    }

    private void loadMainScene(ActionEvent event) throws IOException {
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
}
