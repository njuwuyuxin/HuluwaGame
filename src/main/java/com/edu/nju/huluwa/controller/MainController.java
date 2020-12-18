package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class MainController {
    private Button currentButton;
    private int currentBoyIndex;
    private Scene selfScene;

    @FXML
    public void initialize(Scene scene){
        selfScene = scene;
        System.out.println(GameManager.getInstance().getNetMode());
        if(GameManager.getInstance().getNetMode()== GameManager.NetMode.SERVER) {
            ((Label)selfScene.lookup("#Camp")).setText("妖怪阵营");
            ((Label)selfScene.lookup("#Turn")).setText("对方回合");
            GameManager.getInstance().turn = GameManager.Turn.OPPOSITE;
            waitForResponse();
        }
        else{
            ((Label)selfScene.lookup("#Camp")).setText("葫芦娃阵营");
            ((Label)selfScene.lookup("#Turn")).setText("你的回合");
            GameManager.getInstance().turn = GameManager.Turn.SELF;
        }
    }

    @FXML
    public void handleObjectButton(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        System.out.println(btnSource.getId());
        if(checkObjectCamp(btnSource)){
            currentButton = btnSource;
        }
        else{
            currentButton = null;
        }
        //TODO: get boy object using row,col info
        //currentObject = xxx.getObject(row,col);
    }

    @FXML
    public void gridClicked(MouseEvent event) {
        //cal grid position with mouse position
        int clickCol = (int)event.getSceneX()/100;
        int clickRow = (int)event.getSceneY()/100;
        int currentX = 0;
        int currentY = 0;
        if(GameManager.getInstance().turn==GameManager.Turn.SELF) {
            if (currentButton != null) {
                //Update UI first
                GridPane.setColumnIndex(currentButton, clickCol);
                GridPane.setRowIndex(currentButton, clickRow);
                //Then send message
                currentX = GridPane.getColumnIndex(currentButton);
                currentY = GridPane.getRowIndex(currentButton);
                Message moveMsg = new MoveMsg(currentButton.getId(),currentX, currentY, clickCol, clickRow);
                GameManager.getInstance().getNetClient().sendMsg(moveMsg);
                currentButton = null;
                waitForResponse();
            }
        }
        else{
            currentButton = null;
        }
    }

    private void waitForResponse(){
        ((Label)selfScene.lookup("#Turn")).setText("对方回合");
        GameManager.getInstance().turn = GameManager.Turn.OPPOSITE;
        Task<Void> t = new Task<Void>() {
            Message m;
            @Override
            protected Void call() throws Exception {
                while (true){
                    m = GameManager.getInstance().getNetClient().recvMsg();
                    if(m!=null){
                        System.out.println("message type:"+m.getKind());
                        break;
                    }
                }
                return null;
            }

            @Override
            protected void succeeded(){
                if(m.getKind()==Message.Kind.MOVE){
                    MoveMsg moveMsg = (MoveMsg)m;
                    //TODO: main logic after receiving message
                    System.out.println("Receive Move Message:");
                    System.out.println("object:"+moveMsg.getObjectId()+" from:("+moveMsg.getFromX()+","+moveMsg.getFromY()+") to:("+moveMsg.getToX()+","+moveMsg.getToY()+")");
                    Button object = (Button)selfScene.lookup("#"+moveMsg.getObjectId());
                    moveObject(object,moveMsg.getToX(),moveMsg.getToY());
                    ((Label)selfScene.lookup("#Turn")).setText("你的回合");
                    GameManager.getInstance().turn=GameManager.Turn.SELF;
                }
            }
        };
        new Thread(t).start();
    }

    boolean checkObjectCamp(Button object){
        //server = monster camp
        if(GameManager.getInstance().getNetMode()==GameManager.NetMode.SERVER){
            if(object.getId().equals("wugong1")||object.getId().equals("wugong2")||object.getId().equals("wugong3")||object.getId().equals("wugong4")||
                    object.getId().equals("wugong5")||object.getId().equals("xiezi")||object.getId().equals("shejing"))
                return true;
            else
                return false;
        }
        //client = huluwa camp
        else if(GameManager.getInstance().getNetMode()==GameManager.NetMode.CLIENT){
            if(object.getId().equals("boy1")||object.getId().equals("boy2")||object.getId().equals("boy3")||object.getId().equals("boy4")||
                    object.getId().equals("boy5")||object.getId().equals("boy6")||object.getId().equals("boy7"))
                return true;
            else
                return false;
        }
        return false;
    }


    private void moveObject(Button object,int toX,int toY){
        GridPane.setColumnIndex(object,toX);
        GridPane.setRowIndex(object,toY);
    }
}
