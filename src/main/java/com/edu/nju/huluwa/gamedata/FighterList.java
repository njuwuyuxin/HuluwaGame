package com.edu.nju.huluwa.gamedata;

import com.edu.nju.huluwa.roles.*;
import static com.edu.nju.huluwa.roles.FighterFactory.FighterType.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class FighterList<T extends Fighter> implements Iterable{
    List<T> roles;
    private FighterList(){
        roles = new ArrayList<>();
    }
    public boolean add(T e){
        return roles.add(e);
    }

    public boolean remove(T e){
        return roles.remove(e);
    }

    public boolean hasLiveFighter(){
        for(T fighter : roles){
            if(fighter.isAlive()){
                return true;
            }
        }
        return false;
    }

    public static FighterList<Human> genHumanFighters(){
        FighterList<Human> fighters = new FighterList<>();
        HumanFactory factory = new HumanFactory();
        fighters.add(factory.create(ATKHULUWA, "大娃", "boy1"));
        fighters.add(factory.create(SPDHULUWA, "二娃", "boy2"));
        fighters.add(factory.create(SPDHULUWA, "三娃", "boy3"));
        fighters.add(factory.create(HULUWA, "四娃", "boy4"));
        fighters.add(factory.create(HULUWA, "五娃", "boy5"));
        fighters.add(factory.create(DEFHULUWA, "六娃", "boy6"));
        fighters.add(factory.create(DEFHULUWA, "七娃", "boy7"));
        // fighters.add(factory.create(GRANDPA, "爷爷", "grandpa"));
        // set pos
        int x = 0, y = 1;
        for(Object f : fighters){
            ((Human) f).moveTo(x, y);
            ++y;
        }
        return fighters;
    }

    public static FighterList<Monster> genMonsterFighters(){
        FighterList<Monster> fighters = new FighterList<>();
        MonsterFactory factory = new MonsterFactory();
        fighters.add(factory.create(MINION, "喽啰", "wugong1"));
        fighters.add(factory.create(MINION, "喽啰", "wugong2"));
        fighters.add(factory.create(SCORPION, "蝎子精", "xiezi"));
        fighters.add(factory.create(SNAKE, "蛇精", "shejing"));
        fighters.add(factory.create(MINION, "喽啰", "wugong3"));
        fighters.add(factory.create(MINION, "喽啰", "wugong4"));
        fighters.add(factory.create(MINION, "喽啰", "wugong5"));
        // set pos
        int x = 9, y = 1;
        for(Object f : fighters){
            ((Monster) f).moveTo(x, y);
            ++y;
        }
        return fighters;
    }

    @Override
    public Iterator<T> iterator() {
        return roles.iterator();
    }

}
