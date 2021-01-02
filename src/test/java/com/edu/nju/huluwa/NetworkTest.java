package com.edu.nju.huluwa;

import com.edu.nju.huluwa.network.Message;
import com.edu.nju.huluwa.network.MoveMsg;
import org.junit.Test;

import java.io.*;

public class NetworkTest {

    @Test
    public void testNet(){
        Message msg = new MoveMsg("boy1", 1, 2, 6, 5);
        Message msg_ = new MoveMsg("boy2", 3, 3, 4, 4);
        System.out.println(msg);
        try(
                ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("temp.test"));
        ){
            out.writeObject(msg);
            out.writeObject(msg_);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try(
                ObjectInputStream in = new ObjectInputStream(new FileInputStream("temp.test"));
        ){
            Message msg1 = (Message) in.readObject();
            System.out.println(msg1);
            MoveMsg msg2 = (MoveMsg) in.readObject();
            System.out.println(msg2);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
