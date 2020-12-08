package com.edu.nju.huluwa.gamedata;

import com.edu.nju.huluwa.roles.*;
import static com.edu.nju.huluwa.roles.FighterFactory.FighterType.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

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
            if(fighter.isLive()){
                return true;
            }
        }
        return false;
    }

    public static FighterList<Human> genHumanFighters(){
        FighterList<Human> fighters = new FighterList<>();
        HumanFactory factory = new HumanFactory();
        fighters.add(factory.create(ATKHULUWA, "大娃"));
        fighters.add(factory.create(SPDHULUWA, "二娃"));
        fighters.add(factory.create(SPDHULUWA, "三娃"));
        fighters.add(factory.create(HULUWA, "四娃"));
        fighters.add(factory.create(HULUWA, "五娃"));
        fighters.add(factory.create(DEFHULUWA, "六娃"));
        fighters.add(factory.create(DEFHULUWA, "七娃"));
        fighters.add(factory.create(GRANDPA, "爷爷"));
        return fighters;
    }

    public static FighterList<Monster> genMonsterFighters(){
        FighterList<Monster> fighters = new FighterList<>();
        MonsterFactory factory = new MonsterFactory();
        fighters.add(factory.create(SNAKE, "蛇精"));
        fighters.add(factory.create(SCORPION, "蝎子精"));
        fighters.add(factory.create(MINION, "喽啰"));
        fighters.add(factory.create(MINION, "喽啰"));
        fighters.add(factory.create(MINION, "喽啰"));
        fighters.add(factory.create(MINION, "喽啰"));
        fighters.add(factory.create(MINION, "喽啰"));
        fighters.add(factory.create(MINION, "喽啰"));
        return fighters;
    }

    void battleFormation(){
        // TODO - 让每个角色列阵（改变x, y的值)
    }

    @Override
    public Iterator<T> iterator() {
        return roles.iterator();
    }

}
