package com.edu.nju.huluwa.network;

public class MoveMsg implements Message{
    Kind kind = Kind.MOVE;
    private String objectId;
    int fromX, fromY;
    int toX, toY;
    public MoveMsg(String id, int fromX, int fromY, int toX, int toY){
        objectId = id;
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
    @Override
    public Kind getKind() {
        return kind;
    }

    public int getFromX(){
        return fromX;
    }
    public int getFromY(){
        return fromY;
    }
    public int getToX(){
        return toX;
    }
    public int getToY(){
        return toY;
    }
    public String getObjectId(){
        return objectId;
    }
}
