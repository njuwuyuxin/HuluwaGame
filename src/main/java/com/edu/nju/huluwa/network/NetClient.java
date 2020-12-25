package com.edu.nju.huluwa.network;

import com.edu.nju.huluwa.GameManager;
import com.edu.nju.huluwa.utils.GameLogger;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NetClient {
    Socket socket = null;
    String localAddr;
    int localPort;
    String remoteAddr;
    int remotePort;
    Queue<Message> sendQueue = new LinkedList<>();
    Queue<Message> recvQueue = new LinkedList<>();
    private Lock sendQueueLock = new ReentrantLock();
    private Lock recvQueueLock = new ReentrantLock();
    boolean running = true;

    public NetClient(){
        // get local address by establishing a UDP connection (the target address can be unreachable)
        try(final DatagramSocket socket = new DatagramSocket()){
            socket.connect(InetAddress.getByName("8.8.8.8"), 80);
            localAddr = socket.getLocalAddress().getHostAddress();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
        try(ServerSocket ss = new ServerSocket(0);) {
            localPort = ss.getLocalPort();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("port:" + localPort);
    }

    public String getLocalAddr() {
        return localAddr;
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setRemoteAddr(String remoteAddr) {
        this.remoteAddr = remoteAddr;
    }

    public void setRemotePort(int remotePort) {
        this.remotePort = remotePort;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    public void sendMsg(Message msg){
        sendQueueLock.lock();
        try{
            //GameLogger.v().log(msg);
            sendQueue.offer(msg);
            System.out.println("add msg to sendQueue");
        } finally {
            sendQueueLock.unlock();
        }
    }

    public Message recvMsg(){
        Message msg = null;
        recvQueueLock.lock();
        try {
            if(!recvQueue.isEmpty()){
                msg = recvQueue.poll();
                //GameLogger.v().log(msg);
            }
        } finally {
            recvQueueLock.unlock();
        }
        return msg;
    }

    public boolean start(String asClientOrServer){
        if(asClientOrServer.equals("client")){
            boolean succ = connectAsClient(remoteAddr, remotePort);
            if(!succ) return false;
        }
        else if(asClientOrServer.equals("server")){
            connectAsServer();
        }
        else {
            System.err.println("Wrong argument when starting NetClient, need `client` or `server`");
            return false;
        }
        new SendPktThread().start();
        new RecvPktThread().start();
        return true;
    }

    boolean connectAsClient(String hostname, int port){
        int retry = 0;
        while(socket == null && retry++ < 10){
            try{
                socket = new Socket(hostname, port);
            } catch (UnknownHostException e) {
                System.err.println("Unknown host, please input the hostname again.");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("fail to connect to server `" + hostname + ":" + port + "`, trying to reconnect...");
            }
        }
        if(socket != null) return true;
        else return false;
    }

    boolean connectAsServer(){
        int retry = 0;
        while(socket == null && retry++ < 10) {
            try (
                    ServerSocket ss = new ServerSocket(localPort); // get a free port randomly
            ) {
                socket = ss.accept();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(socket != null) return true;
        else return false;
    }

    private class SendPktThread extends Thread {
        public SendPktThread(){
            setDaemon(true);
        }

        @Override
        public void run(){
            try(
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ) {
                while(running){
                    sendQueueLock.lock();
                    try {
                        if(!sendQueue.isEmpty()){
                            Message msg = sendQueue.poll();
                            out.writeObject(msg);
                            GameLogger.v().log(msg);
                            System.out.println("already send the msg");
                            // TODO - add log
                            out.flush();
                        }
                    } finally {
                        sendQueueLock.unlock();
                    }
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private class RecvPktThread extends Thread {
        public RecvPktThread(){
            setDaemon(true);
        }
        @Override
        public void run(){
            try(
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ) {
                while(running){
                    Message msg = (Message) in.readObject();
                    System.out.println("receive msg, msg type : " + msg.getKind());
                    GameLogger.v().log(msg);
                    GameManager.getInstance().handleReceiveMessage(msg);
                    recvQueueLock.lock();
                    try {
                        recvQueue.offer(msg);
                    } finally {
                        recvQueueLock.unlock();
                    }
                }
            } catch (IOException e) {
                System.err.println("the connection broke");
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
