package com.edu.nju.huluwa.roles;

public interface FighterFactory {
    enum FighterType {
        HULUWA, ATKHULUWA, DEFHULUWA, SPDHULUWA, GRANDPA, SNAKE, SCORPION, MINION;
    }
    Fighter create(FighterType type, String name);
}

