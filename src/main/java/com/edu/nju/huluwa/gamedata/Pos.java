package com.edu.nju.huluwa.gamedata;

public class Pos {
    int x;
    int y;
    public Pos(int x, int y){
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPos(Pos pos){
        x = pos.getX();
        y = pos.getY();
    }

    public void setPos(int x, int y){
        this.x = x;
        this.y = y;
    }
}
