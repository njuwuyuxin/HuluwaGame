package com.edu.nju.huluwa.utils;

import com.edu.nju.huluwa.network.Message;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameLogger {
    File log_file;
    ObjectOutputStream out;
    static GameLogger INSTANCE = new GameLogger();
    private GameLogger(){
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat();
        formatter.applyPattern("yyyy-MM-dd HH-mm-ss");
        log_file = new File("saves/" + formatter.format(date) + ".txt");
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

    public static GameLogger v() { return INSTANCE; }

    public synchronized void log(Message msg){
        try {
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

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
