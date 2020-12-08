package com.edu.nju.huluwa.roles;

public class HumanFactory implements FighterFactory{

    @Override
    public Human create(FighterType type, String name) {
        Human human = null;
        switch (type){
            case HULUWA:
                human = new Huluwa();
                human.setAttr(name, 20, 5, 2, 3, 1);
                break;
            case ATKHULUWA:
                human = new Huluwa();
                human.setAttr(name, 15, 8, 2, 3, 1);
                break;
            case DEFHULUWA:
                human = new Huluwa();
                human.setAttr(name, 25, 5, 3, 2, 1);
                break;
            case SPDHULUWA:
                human = new Huluwa();
                human.setAttr(name, 20, 5, 3, 4, 1);
                break;
            case GRANDPA:
                human = new Grandpa();
                human.setAttr(name, 30, 5, 3, 3, 2);
                break;
            default:
                throw new Error("wrong type when creating human fighter, type : " + type);
        }
        return human;
    }
}
