package com.edu.nju.huluwa.roles;

public class MonsterFactory implements FighterFactory{

    @Override
    public Monster create(FighterType type, String name) {
        Monster monster = null;
        switch (type){
            case SNAKE:
                monster = new Snake();
                monster.setAttr(name, 30, 8, 3, 4, 2);
                break;
            case SCORPION:
                monster = new Scorpion();
                monster.setAttr(name, 30, 5, 3, 2, 2);
                break;
            case MINION:
                monster = new Minion();
                monster.setAttr(name, 20, 5, 3, 2, 1);
                break;
            default:
                throw new Error("wrong type when creating monster fighter, type : " + type);
        }
        return monster;
    }
}
