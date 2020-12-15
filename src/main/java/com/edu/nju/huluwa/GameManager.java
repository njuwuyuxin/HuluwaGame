package com.edu.nju.huluwa;

import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.NetClient;

public class GameManager {
    private static GameManager instance = new GameManager();
    private NetClient nc;
    private GameManager(){
        nc = new NetClient();
    }
    public static GameManager getInstance(){
        return instance;
    }

    public void handleReceiveMessage(Message msg){
        System.out.println("receive message call back");
    }
}
