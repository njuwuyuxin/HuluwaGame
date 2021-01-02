package com.edu.nju.huluwa.utils;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.network.Message;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

import static java.lang.System.exit;

public class GameLogger {
    File log_file;
    ObjectOutputStream out;
    static GameLogger INSTANCE = null;
    private GameLogger(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd HH-mm-ss");
        File log_dir = new File("saves");
        if(!log_dir.exists()) log_dir.mkdir();
        log_file = new File("saves/" + formatter.format(date) + ".save");
        try {
            System.out.println(log_file.getAbsolutePath());

            log_file.createNewFile();
            out = new ObjectOutputStream(new FileOutputStream(log_file));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameLogger v() {
        if(INSTANCE == null) INSTANCE = new GameLogger();
        return INSTANCE;
    }

    public void log(Message msg){
        try {
            if(out == null){
                System.out.println("out == null!");
                exit(-1);
            }
            if(msg == null){
                System.out.println("msg == null!");
                exit(-1);
            }
            out.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readLog(File saveFile){
        try (
                ObjectInputStream in = new ObjectInputStream(new FileInputStream(saveFile));
        ){
            Message msg;
            while((msg = (Message) in.readObject()) != null){
                GameManager.getInstance().replayMsgList.add(msg);
            }
        } catch (EOFException e) {
            // end of file
        }
        catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
