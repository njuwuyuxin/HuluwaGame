package com.edu.nju.huluwa.controller;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.gamedata.BattleGround;
import com.edu.nju.huluwa.gamedata.Pos;
import com.edu.nju.huluwa.network.AttackMsg;
import com.edu.nju.huluwa.network.EndMsg;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import com.edu.nju.huluwa.roles.Fighter;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import javafx.scene.input.MouseEvent;

import java.util.ArrayList;
import java.util.List;

public class MainController {
    private Button currentButton;
    private Scene selfScene;
    private int availableSteps = 1;
    private int oppoSteps = 1;
    private ArrayList<Button> rangeButtons;

    private void resetAvailableSteps(){
        availableSteps = 1;
    }

    private void resetOppoSteps(){
        oppoSteps = 1;
    }

    @FXML
    public void initialize(Scene scene){
        selfScene = scene;
        rangeButtons = new ArrayList<Button>();
        System.out.println(GameManager.getInstance().getNetMode());
        changePhaseTo(GameManager.Phase.MOVE);

        if(GameManager.getInstance().getNetMode()== GameManager.NetMode.SERVER) {
            ((Label)selfScene.lookup("#Camp")).setText("妖怪阵营");
            changeTurnTo(GameManager.Turn.OPPOSITE);
            waitForResponse();
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
        if(!checkObjectCamp(btnSource)){
            hideMoveRange();
        }
        showRoleInfo(btnSource);
        //在自己回合点击己方阵营按钮,选中单位
        if(GameManager.getInstance().turn == GameManager.Turn.SELF && checkObjectCamp(btnSource)){
            //点击新按钮
            if(currentButton == null) {
                currentButton = btnSource;
                if (GameManager.getInstance().phase == GameManager.Phase.MOVE){
                    System.out.println(GameManager.getInstance().turn);
                    showMoveRange(btnSource);
                }
            }
            //点击了其他己方按钮
            else if(currentButton != btnSource){
                currentButton = btnSource;
                if (GameManager.getInstance().phase == GameManager.Phase.MOVE){
                    // System.out.println(GameManager.getInstance().turn);
                    showMoveRange(btnSource);
                }
            }
            //二次点击同一按钮视为取消选择
            else{
                hideMoveRange();
                currentButton = null;
            }

        }
        //点击对方阵营按钮,进行攻击
        else if(GameManager.getInstance().turn == GameManager.Turn.SELF &&
                GameManager.getInstance().phase == GameManager.Phase.ATTACK && currentButton!=null &&
                getFighter(currentButton).canAttack(getFighter(btnSource))){
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
            checkGameOver();

            changePhaseTo(GameManager.Phase.MOVE);
            waitForResponse();
        }
        else{
            currentButton = null;
            hideMoveRange();
        }
    }

    @FXML
    public void gridClicked(MouseEvent event) {
        currentButton = null;
        hideMoveRange();
        hideRoleInfo();
    }

    public void HandleTurnOverButton(ActionEvent actionEvent) {
        if(!GameManager.getInstance().isMyTurn()) return;
        if(GameManager.getInstance().inMovePhase()){
            changePhaseTo(GameManager.Phase.ATTACK);
            Message endMsg = new EndMsg();
            GameManager.getInstance().getNetClient().sendMsg(endMsg);
        }
        else if(GameManager.getInstance().inAttackPhase()){
            changePhaseTo(GameManager.Phase.MOVE);
            Message endMsg = new EndMsg();
            GameManager.getInstance().getNetClient().sendMsg(endMsg);
            waitForResponse();
        }

    }

    private void waitForResponse(){
        changeTurnTo(GameManager.Turn.OPPOSITE);
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

    private void showMoveRange(Button object){
        //first clear
        hideMoveRange();

        System.out.println("show move range!");

        GridPane board = ((GridPane)selfScene.lookup("#board"));
        Fighter fighter = getFighter(object);
        //for(int i=0;i<col.size();i++){
        for(Pos pos : fighter.reachableGrid()){
            System.out.println("create range button");
            Button range = new Button();
            RangeButtonHandler handler = new RangeButtonHandler();
            range.setOnAction(handler);
            board.getChildren().add(range);
            GridPane.setColumnIndex(range, pos.getX());
            GridPane.setRowIndex(range,pos.getY());
            range.setStyle("-fx-background-color:green; -fx-opacity:0.5");
            rangeButtons.add(range);
        }
    }

    private void hideMoveRange(){
        GridPane board = ((GridPane)selfScene.lookup("#board"));
        for(Button b:rangeButtons){
            board.getChildren().remove(b);
        }
        rangeButtons.clear();
    }

    private void showRoleInfo(Button object){
        Fighter fighter = getFighter(object);
        ((Label) selfScene.lookup("#name")).setText("当前角色：" + fighter.getName());
        ((Label) selfScene.lookup("#hp")).setText("HP：" + fighter.getHp() + "/" + fighter.getMaxHp());
        ((Label) selfScene.lookup("#atk")).setText("攻击力：" + fighter.getAtk());
        ((Label) selfScene.lookup("#def")).setText("防御力：" + fighter.getDef());
        ((Label) selfScene.lookup("#atkRange")).setText("攻击范围：" + fighter.getAttackRange());
        ((Label) selfScene.lookup("#moveRange")).setText("移动范围：" + fighter.getMoveRange());
    }

    private void hideRoleInfo(){
        ((Label) selfScene.lookup("#name")).setText("");
        ((Label) selfScene.lookup("#hp")).setText("");
        ((Label) selfScene.lookup("#atk")).setText("");
        ((Label) selfScene.lookup("#def")).setText("");
        ((Label) selfScene.lookup("#atkRange")).setText("");
        ((Label) selfScene.lookup("#moveRange")).setText("");
    }

    private void checkGameOver(){
        if(GameManager.getInstance().gameIsOver()) {
            Label info = ((Label) selfScene.lookup("#GameOver"));
            String winner = GameManager.getInstance().getWinner();
            info.setText("Game Over!\n" + winner + "胜！");
            info.setVisible(true);
        }
    }

    //move main logic
    private void moveObject(Button object,int toX,int toY){
        getFighter(object).moveTo(toX, toY);
        GridPane.setColumnIndex(object,toX);
        GridPane.setRowIndex(object,toY);
    }
    //attack main logic
    private void attack(Button source, Button target){
        Fighter attacker = getFighter(source);
        Fighter defender = getFighter(target);
        attacker.attack(defender);
        if(!defender.isAlive()) {
            System.out.println(target.getId() + " go die!");
            objectDie(target);
        }
    }

    private void objectDie(Button object){
        ((GridPane)selfScene.lookup("#board")).getChildren().remove(object);
        BattleGround.removeFighter(getFighter(object));
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
                // waitForResponse();
            }
            else if(m.getKind()==Message.Kind.ATTACK){
                AttackMsg attackMsg = (AttackMsg)m;
                System.out.println("Receive Attack Message:"+attackMsg.getFromId()+" attack "+attackMsg.getToId());
                Button source = (Button)selfScene.lookup("#"+attackMsg.getFromId());
                Button target = (Button)selfScene.lookup("#"+attackMsg.getToId());
                //TODO: main logic after receiving attack message
                attack(source,target);
                checkGameOver();

                changePhaseTo(GameManager.Phase.MOVE);
                changeTurnTo(GameManager.Turn.SELF);
            }
            else if(m.getKind() == Message.Kind.END){
                if(GameManager.getInstance().inMovePhase()){
                    changePhaseTo(GameManager.Phase.ATTACK);
                }
                else if(GameManager.getInstance().inAttackPhase()){
                    changePhaseTo(GameManager.Phase.MOVE);
                    changeTurnTo(GameManager.Turn.SELF);
                }
            }
        }
    }

    //handle move operation
    private class RangeButtonHandler implements EventHandler<ActionEvent>{
        @Override
        public void handle(ActionEvent event){
            Button btnSource = (Button)event.getSource();
            if(GameManager.getInstance().turn==GameManager.Turn.SELF&&GameManager.getInstance().phase==GameManager.Phase.MOVE) {
                if (currentButton != null) {
                    availableSteps--;
                    int currentX = GridPane.getColumnIndex(currentButton);
                    int currentY = GridPane.getRowIndex(currentButton);
                    int targetX = GridPane.getColumnIndex(btnSource);
                    int targetY = GridPane.getRowIndex(btnSource);
                    moveObject(currentButton,targetX,targetY);
                    Message moveMsg = new MoveMsg(currentButton.getId(),currentX, currentY, targetX, targetY);
                    GameManager.getInstance().getNetClient().sendMsg(moveMsg);
                    changePhaseTo(GameManager.Phase.ATTACK);
                    hideMoveRange();
                    currentButton = null;
                }
            }
            else{
                currentButton = null;
            }
        }
    }

    private Fighter getFighter(Button button) {
        int x = GridPane.getColumnIndex(button);
        int y = GridPane.getRowIndex(button);
        Fighter f = BattleGround.getFighterOn(x, y);
        return f;
    }

}
