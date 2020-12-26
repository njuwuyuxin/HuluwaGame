package com.edu.nju.huluwa;

import com.edu.nju.huluwa.gamedata.FighterList;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.NetClient;
import com.edu.nju.huluwa.roles.Fighter;
import com.edu.nju.huluwa.roles.Monster;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;


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
    public boolean isReplay;
    public int replayIndex;

    private FighterList<? extends Fighter> humans;
    private FighterList<? extends Fighter> monsters;
    public ArrayList<Message> replayMsgList;

    private GameManager(){
        nc = new NetClient();
        humans = FighterList.genHumanFighters();
        monsters = FighterList.genMonsterFighters();
        replayMsgList = new ArrayList<Message>();
        isReplay = false;
        replayIndex = 0;
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

    public Stage getPrimaryStage() {
        return primaryStage;
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

    public boolean isMyTurn(){
        return turn == Turn.SELF;
    }

    public boolean inMovePhase(){ return phase == Phase.MOVE; }

    public boolean inAttackPhase(){ return phase == Phase.ATTACK; }

    public boolean gameIsOver(){
        return !humans.hasLiveFighter() || !monsters.hasLiveFighter();
    }

    public String getWinner(){
        assert(gameIsOver());
        if(humans.hasLiveFighter()) return "葫芦娃";
        else return "妖怪";
    }
}
