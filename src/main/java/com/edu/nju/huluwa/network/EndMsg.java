package com.edu.nju.huluwa.network;

public class EndMsg implements Message {
    Kind kind = Kind.END;
    @Override
    public Kind getKind() {
        return kind;
    }
}
