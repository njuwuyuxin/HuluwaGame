package com.edu.nju.huluwa;
import com.edu.nju.huluwa.gamedata.BattleGround;
import com.edu.nju.huluwa.gamedata.FighterList;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import com.edu.nju.huluwa.network.NetClient;
import com.edu.nju.huluwa.network.RanNumMsg;
import com.edu.nju.huluwa.roles.Fighter;
import com.edu.nju.huluwa.roles.Human;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent mainMenuRoot = FXMLLoader.load(getClass().getClassLoader().getResource("mainMenu.fxml"));
        Scene mainMenuScene = new Scene(mainMenuRoot, 300, 500);
        mainMenuScene.getStylesheets().add(getClass().getClassLoader().getResource("mainMenu.css").toExternalForm());

        primaryStage.setTitle("HuluwaGame");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
        GameManager.getInstance().setPrimaryStage(primaryStage);
    }
}

class A{}