package com.edu.nju.huluwa;

import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.NetClient;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class GameManager {
    public enum NetMode {SERVER,CLIENT};
    public enum Turn {SELF,OPPOSITE};
    public enum Phase {MOVE,ATTACK};

    private static GameManager instance = new GameManager();
    private NetClient nc;
    private Stage primaryStage;

    private NetMode netMode;
    public Turn turn;
    public Phase phase;
    private GameManager(){
        nc = new NetClient();
    }

    public static GameManager getInstance(){
        return instance;
    }

    public NetClient getNetClient(){
        return nc;
    }

    public void setPrimaryStage(Stage stage){
        primaryStage = stage;
    }

    public void handleReceiveMessage(Message msg){
        System.out.println("receive message call back");
    }

    public void createServer(){
        System.out.println("server ip:"+nc.getLocalAddr()+" server port:"+nc.getLocalPort());
        nc.start("server");
        netMode = NetMode.SERVER;
        System.out.println("Create Server success");
    }

    public void createClient(String ip,int port){
        nc.setRemoteAddr(ip);
        nc.setRemotePort(port);
        nc.start("client");
        netMode = NetMode.CLIENT;
        System.out.println("Create Client success");
    }

    public NetMode getNetMode(){
        return netMode;
    }
}
