package com.edu.nju.huluwa.gamedata;

import com.edu.nju.huluwa.roles.Fighter;

class Grid{
    Fighter fighter = null;

    public Fighter getFighter() {
        return fighter;
    }

    public void setFighter(Fighter fighter) {
        this.fighter = fighter;
    }
}

public class BattleGround {
    public static final int length = 10;
    public static final int width = 10;
    private static Grid[][] ground;

    private static BattleGround INSTANCE = new BattleGround();

    private BattleGround(){
        ground = new Grid[length][width];
        for(int i = 0; i < length; ++i) {
            for (int j = 0; j < width; ++j) {
                ground[i][j] = new Grid();
            }
        }
    }

    public static BattleGround v(){
        return INSTANCE;
    }

    public static Fighter getFighterOn(int x, int y){
        return ground[x][y].getFighter();
    }

    public static void removeFighterOn(int x, int y){
        ground[x][y].setFighter(null);
    }

    public static void setFighterOn(int x, int y, Fighter fighter){
        assert(ground[x][y].getFighter() == null);
        ground[x][y].setFighter(fighter);

    }

}
