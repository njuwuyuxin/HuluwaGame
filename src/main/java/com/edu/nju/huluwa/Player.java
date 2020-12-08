package com.edu.nju.huluwa;

import com.edu.nju.huluwa.gamedata.FighterList;
import com.edu.nju.huluwa.roles.Fighter;
import com.edu.nju.huluwa.roles.Human;

public class Player {
    FighterList<? extends Fighter> fighters;
    int id; // TODO - 区分该player是在服务器端还是客户端
    Player(){
        // example
        fighters = FighterList.genHumanFighters();
        fighters = FighterList.genMonsterFighters();
        System.out.println("player init!");
    }
}
