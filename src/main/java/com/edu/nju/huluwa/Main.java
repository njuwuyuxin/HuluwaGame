package com.edu.nju.huluwa;
import com.edu.nju.huluwa.gamedata.BattleGround;
import com.edu.nju.huluwa.gamedata.FighterList;
import com.edu.nju.huluwa.roles.Fighter;
import com.edu.nju.huluwa.roles.Human;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application{
    public static void main(String[] args) {
        System.out.println("hello world");
        // Player p = new Player();
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("Main.fxml"));
        Scene scene = new Scene(root, 600, 500);
        scene.getStylesheets().add(getClass().getClassLoader().getResource("application.css").toExternalForm());

        primaryStage.setTitle("Simple JavaFX");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

class A{}