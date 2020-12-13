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
        Scene mainMenuScene = new Scene(mainMenuRoot, 1000, 1000);
        mainMenuScene.getStylesheets().add(getClass().getClassLoader().getResource("mainMenu.css").toExternalForm());

        primaryStage.setTitle("HuluwaGame");
        primaryStage.setScene(mainMenuScene);
        primaryStage.show();

        /*Test NetClient*/
//        NetClient nc = new NetClient();
//        System.out.println("input 1 to create a server, 2 to create a client");
//        int cmd=0;
//        Scanner input = new Scanner(System.in);
//        cmd = input.nextInt();
//        if(cmd==1){
//            System.out.println("server ip:"+nc.getLocalAddr()+" server port:"+nc.getLocalPort());
//            System.out.println(nc.start("server"));
//            System.out.println("server ip:"+nc.getLocalAddr()+" server port:"+nc.getLocalPort());
//            while (true) {
//                Message m = nc.recvMsg();
//                if(m!=null){
//                    System.out.println("message type:"+m.getKind());
//                }
//            }
//        }
//        else if(cmd==2){
//            System.out.println("input target ip and port");
//            String ip = input.next();
//            int port = input.nextInt();
//            nc.setRemoteAddr(ip);
//            nc.setRemotePort(port);
//            System.out.println(nc.start("client"));
//            Message a = new MoveMsg(1,1,2,3);
//            Message b = new RanNumMsg(10);
//            nc.sendMsg(a);
//            nc.sendMsg(b);
//        }

    }
}

class A{}