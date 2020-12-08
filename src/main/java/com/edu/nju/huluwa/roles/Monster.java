package com.edu.nju.huluwa.roles;

public abstract class Monster extends Fighter {
    private static int count = 0;
    protected Monster(){
        id = count++;
    }
}

class Snake extends Monster{

}

class Scorpion extends Monster{ // 蝎子精

}

class Minion extends Monster{ // 喽啰

}