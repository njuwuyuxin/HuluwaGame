package com.edu.nju.huluwa.network;

public class RanNumMsg implements Message{
    Kind kind = Kind.RANNUM;
    int num;
    public RanNumMsg(int num){
        this.num = num;
    }

    @Override
    public Kind getKind() {
        return kind;
    }
}
