package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.network.AttackMsg;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;

public class MainController {
    private Button currentButton;
    private Scene selfScene;
    private int availableSteps = 1;
    private int oppoSteps = 1;

    private void resetAvailableSteps(){
        availableSteps = 1;
    }

    private void resetOppoSteps(){
        oppoSteps = 1;
    }

    @FXML
    public void initialize(Scene scene){
        selfScene = scene;
        System.out.println(GameManager.getInstance().getNetMode());
        changePhaseTo(GameManager.Phase.MOVE);

        if(GameManager.getInstance().getNetMode()== GameManager.NetMode.SERVER) {
            ((Label)selfScene.lookup("#Camp")).setText("妖怪阵营");
            changeTurnTo(GameManager.Turn.OPPOSITE);
            for(int i = 0; i < oppoSteps; ++i) {
                waitForResponse();
            }
        }
        else{
            ((Label)selfScene.lookup("#Camp")).setText("葫芦娃阵营");
            changeTurnTo(GameManager.Turn.SELF);
        }
    }

    @FXML
    public void handleObjectButton(ActionEvent event){
        Button btnSource = (Button) event.getSource();
        System.out.println(btnSource.getId());
        //点击己方阵营按钮,选中单位
        if(checkObjectCamp(btnSource)){
            currentButton = btnSource;
        }
        //点击对方阵营按钮,进行攻击
        else if(GameManager.getInstance().turn == GameManager.Turn.SELF &&
                GameManager.getInstance().phase == GameManager.Phase.ATTACK && currentButton!=null){
            System.out.println(currentButton.getId()+" attack "+btnSource.getId());
            //TODO: Attack Logic and UI Update
            attack(currentButton,btnSource);

            //send attack message
            int currentX = GridPane.getColumnIndex(currentButton);
            int currentY = GridPane.getRowIndex(currentButton);
            int targetX = GridPane.getColumnIndex(btnSource);
            int targetY = GridPane.getRowIndex(btnSource);
            Message attackMsg = new AttackMsg(currentX,currentY,targetX,targetY,currentButton.getId(),btnSource.getId());
            GameManager.getInstance().getNetClient().sendMsg(attackMsg);
            changePhaseTo(GameManager.Phase.MOVE);
            waitForResponse();
        }
        else{
            currentButton = null;
        }
        //TODO: get boy object using row,col info
        //currentObject = xxx.getObject(row,col);
    }

    //handle move operation
    @FXML
    public void gridClicked(MouseEvent event) {
        //cal grid position with mouse position
        int clickCol = (int)event.getSceneX()/100;
        int clickRow = (int)event.getSceneY()/100;
        int currentX = 0;
        int currentY = 0;
        if(GameManager.getInstance().turn==GameManager.Turn.SELF&&GameManager.getInstance().phase==GameManager.Phase.MOVE) {
            if (currentButton != null) {
                availableSteps--;
                moveObject(currentButton,clickCol,clickRow);
                //Then send message
                currentX = GridPane.getColumnIndex(currentButton);
                currentY = GridPane.getRowIndex(currentButton);
                Message moveMsg = new MoveMsg(currentButton.getId(),currentX, currentY, clickCol, clickRow);
                GameManager.getInstance().getNetClient().sendMsg(moveMsg);

                changePhaseTo(GameManager.Phase.ATTACK);
                currentButton = null;
            }
        }
        else{
            currentButton = null;
        }
    }

    private void waitForResponse(){
        if(availableSteps <= 0) {
            changeTurnTo(GameManager.Turn.OPPOSITE);
            resetOppoSteps();
        }
        //创建两个线程分别处理move请求和attack请求
        Task<Void> t1 = new ReceiveMsgTask();
        Task<Void> t2 = new ReceiveMsgTask();
        new Thread(t1).start();
        new Thread(t2).start();
    }

    private boolean checkObjectCamp(Button object){
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

    //move main logic
    private void moveObject(Button object,int toX,int toY){
        GridPane.setColumnIndex(object,toX);
        GridPane.setRowIndex(object,toY);
    }

    //attack main logic
    private void attack(Button source, Button target){

    }

    private void changeTurnTo(GameManager.Turn turn){
        if(turn==GameManager.Turn.OPPOSITE){
            ((Label) selfScene.lookup("#Turn")).setText("对方回合");
            GameManager.getInstance().turn = GameManager.Turn.OPPOSITE;
        }
        else if(turn==GameManager.Turn.SELF){
            ((Label) selfScene.lookup("#Turn")).setText("你的回合");
            GameManager.getInstance().turn = GameManager.Turn.SELF;
        }
    }

    private void changePhaseTo(GameManager.Phase phase){
        if(phase==GameManager.Phase.MOVE){
            ((Label) selfScene.lookup("#Phase")).setText("移动阶段");
            GameManager.getInstance().phase = GameManager.Phase.MOVE;
        }
        else if(phase==GameManager.Phase.ATTACK){
            ((Label) selfScene.lookup("#Phase")).setText("攻击阶段");
            GameManager.getInstance().phase = GameManager.Phase.ATTACK;
        }
    }

    class ReceiveMsgTask extends Task<Void>{
        Message m;
        @Override
        protected synchronized Void call() throws Exception {
            while (true){
                m = GameManager.getInstance().getNetClient().recvMsg();
                if(m!=null){
                    System.out.println("receive message type:"+m.getKind() + " in MainController");
                    break;
                }
            }
            return null;
        }

        @Override
        protected synchronized void succeeded(){
            oppoSteps--;
            if(m.getKind()==Message.Kind.MOVE){
                MoveMsg moveMsg = (MoveMsg)m;
                System.out.println("Receive Move Message:");
                System.out.println("object:"+moveMsg.getObjectId()+" from:("+moveMsg.getFromX()+","+moveMsg.getFromY()+") to:("+moveMsg.getToX()+","+moveMsg.getToY()+")");
                Button object = (Button)selfScene.lookup("#"+moveMsg.getObjectId());
                //TODO: main logic after receiving move message
                moveObject(object,moveMsg.getToX(),moveMsg.getToY());

                changePhaseTo(GameManager.Phase.ATTACK);

            }
            else if(m.getKind()==Message.Kind.ATTACK){
                AttackMsg attackMsg = (AttackMsg)m;
                System.out.println("Receive Attack Message:"+attackMsg.getFromId()+" attack "+attackMsg.getToId());
                Button source = (Button)selfScene.lookup("#"+attackMsg.getFromId());
                Button target = (Button)selfScene.lookup("#"+attackMsg.getToId());
                //TODO: main logic after receiving attack message
                attack(source,target);

                changePhaseTo(GameManager.Phase.MOVE);
                if(oppoSteps <= 0) {
                    changeTurnTo(GameManager.Turn.SELF);
                    resetAvailableSteps();
                }
            }
        }
    }
}
