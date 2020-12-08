package com.edu.nju.huluwa.roles;


import com.edu.nju.huluwa.gamedata.BattleGround;

public abstract class Fighter {
    // init by setAttr()
    String name = null;
    int hp;
    int atk;
    int def;
    int moveRange;
    int attackRange;
    String picturePath = null;

    int x, y;
    // init automatically
    int id; // the index of Human/Monster in FighterList<Human>/FighterList<Monster>


    void setAttr(String name, int hp, int atk, int def, int moveRange, int attackRange){
        this.name = name;
        this.hp = hp;
        this.atk = atk;
        this.def = def;
        this.moveRange = moveRange;
        this.attackRange = attackRange;
        this.picturePath = null; // TODO - 修改具体文件路径
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getAtk() {
        return atk;
    }

    public void setAtk(int atk) {
        this.atk = atk;
    }

    public int getDef() {
        return def;
    }

    public void setDef(int def) {
        this.def = def;
    }

    public int getMoveRange() {
        return moveRange;
    }

    public void setMoveRange(int moveRange) {
        this.moveRange = moveRange;
    }

    public int getAttackRange() {
        return attackRange;
    }

    public void setAttackRange(int attackRange) {
        this.attackRange = attackRange;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isLive() {
        return hp > 0;
    }

    public int calDamage(Fighter target){
        return getAtk() - target.getDef();
    }

    public void moveTo(int x, int y){
        BattleGround.v().removeFighterOn(this.x, this.y);
        this.x = x;
        this.y = y;
        BattleGround.v().setFighterOn(x, y, this);
    }
}
