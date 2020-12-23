package com.edu.nju.huluwa.roles;

public class HumanFactory implements FighterFactory{

    @Override
    public Human create(FighterType type, String name, String id) {
        Human human = null;
        switch (type){
            case HULUWA:
                human = new Huluwa();
                human.setAttr(name, id, 20, 5, 2, 3, 1);
                break;
            case ATKHULUWA:
                human = new Huluwa();
                human.setAttr(name, id, 15, 8, 2, 3, 2);
                break;
            case DEFHULUWA:
                human = new Huluwa();
                human.setAttr(name, id, 25, 5, 3, 2, 2);
                break;
            case SPDHULUWA:
                human = new Huluwa();
                human.setAttr(name, id, 20, 5, 3, 4, 3);
                break;
            case GRANDPA:
                human = new Grandpa();
                human.setAttr(name, id, 30, 5, 3, 3, 2);
                break;
            default:
                throw new Error("wrong type when creating human fighter, type : " + type);
        }
        return human;
    }
}
