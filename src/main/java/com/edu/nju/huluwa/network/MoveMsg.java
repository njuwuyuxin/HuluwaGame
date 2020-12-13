package com.edu.nju.huluwa.network;

public class MoveMsg implements Message{
    Kind kind = Kind.MOVE;
    int fromX, fromY;
    int toX, toY;
    public MoveMsg(int fromX, int fromY, int toX, int toY){
        this.fromX = fromX;
        this.fromY = fromY;
        this.toX = toX;
        this.toY = toY;
    }
    @Override
    public Kind getKind() {
        return kind;
    }
}
