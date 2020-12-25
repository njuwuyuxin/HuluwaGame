package com.edu.nju.huluwa;

import com.edu.nju.huluwa.network.AttackMsg;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import javafx.scene.control.Button;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class GameLoggerTest {
    @Test
    public void IOTest(){

    }

    //@Test
    public void testLogger(){
        File saveFile = new File("save/test.txt");
        try (
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile));
        ){
            Message m;
            while((m = (Message) in.readObject()) != null){
                if(m.getKind()==Message.Kind.MOVE){
                    MoveMsg moveMsg = (MoveMsg)m;
                    System.out.println("Receive Move Message:");
                    System.out.println("object:"+moveMsg.getObjectId()+" from:("+moveMsg.getFromX()+","+moveMsg.getFromY()+") to:("+moveMsg.getToX()+","+moveMsg.getToY()+")");
                }
                else if(m.getKind()==Message.Kind.ATTACK){
                    AttackMsg attackMsg = (AttackMsg)m;
                    System.out.println("Receive Attack Message:"+attackMsg.getFromId()+" attack "+attackMsg.getToId());
                }
                else if(m.getKind() == Message.Kind.END){
                    System.out.println("Receive End Message!");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
