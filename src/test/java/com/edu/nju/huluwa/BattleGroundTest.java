package com.edu.nju.huluwa;
import com.edu.nju.huluwa.gamedata.BattleGround;
import com.edu.nju.huluwa.gamedata.FighterList;
import com.edu.nju.huluwa.roles.Fighter;
import com.edu.nju.huluwa.roles.FighterFactory;
import com.edu.nju.huluwa.roles.HumanFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class BattleGroundTest {
    @Test
    public void battleGroundTest(){
        FighterFactory factory = new HumanFactory();
        Fighter huluwa = factory.create(FighterFactory.FighterType.HULUWA, "huluwa", "boy1");
        BattleGround.setFighterOn(3, 4, huluwa);
        Fighter fighter = BattleGround.getFighterOn(3, 4);
        assertSame(fighter, huluwa);
        BattleGround.removeFighterOn(3, 4);
        assertNull(BattleGround.getFighterOn(3, 4));
    }
}
