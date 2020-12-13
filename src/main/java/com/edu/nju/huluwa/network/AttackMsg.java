package com.edu.nju.huluwa.network;

public class AttackMsg implements Message {
    Kind kind = Kind.ATTACK;
    int fromX, fromY;
    int targetX, targetY;
    public AttackMsg(int fromX, int fromY, int targetX, int targetY){
        this.fromX = fromX;
        this.fromY = fromY;
        this.targetX = targetX;
        this.targetY = targetY;
    }
    @Override
    public Kind getKind() {
        return kind;
    }
}
