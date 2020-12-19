package com.edu.nju.huluwa.network;

public class AttackMsg implements Message {
    Kind kind = Kind.ATTACK;
    int fromX, fromY;
    int targetX, targetY;
    String fromId;
    String toId;
    public AttackMsg(int fromX, int fromY, int targetX, int targetY,String fromId,String toId){
        this.fromX = fromX;
        this.fromY = fromY;
        this.targetX = targetX;
        this.targetY = targetY;
        this.fromId = fromId;
        this.toId = toId;
    }
    @Override
    public Kind getKind() {
        return kind;
    }

    public String getFromId(){
        return fromId;
    }
    public String getToId(){
        return toId;
    }
}
