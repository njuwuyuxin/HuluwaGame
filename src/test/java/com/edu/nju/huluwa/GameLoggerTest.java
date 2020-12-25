package com.edu.nju.huluwa;

import com.edu.nju.huluwa.network.AttackMsg;
import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import javafx.scene.control.Button;
import org.junit.Test;

import java.io.*;
import java.nio.*;

public class GameLoggerTest {
    @Test
    public void IOTest() throws Exception{
        File f = new File("saves/a.txt");
            f.createNewFile();
            FileOutputStream out = new FileOutputStream(f);
            out.write(2);
            out.write(3);
            out.close();

    }

    @Test
    public void testLogger(){
        File saveFile = new File("saves/test.txt");
        try (
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile));
        ){
            Message m;
            in.available();
            while(in.available() > 0){
                m = (Message) in.readObject();
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
