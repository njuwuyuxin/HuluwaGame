package com.edu.nju.huluwa.roles;


import com.edu.nju.huluwa.gamedata.BattleGround;

public abstract class Fighter {
    // init by setAttr()
    String name;
    String id;
    int hp;
    int maxHp;
    int atk;
    int def;
    int moveRange;
    int attackRange;

    int x, y;


    void setAttr(String name, String id, int hp, int atk, int def, int moveRange, int attackRange){
        this.name = name;
        this.id = id;
        this.hp = hp;
        this.maxHp = hp;
        this.atk = atk;
        this.def = def;
        this.moveRange = moveRange;
        this.attackRange = attackRange;
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getX() { return x; }

    public int getY() { return y; }

    public boolean isAlive() {
        return hp > 0;
    }

    public void moveTo(int x, int y){
        BattleGround.v().removeFighterOn(this.x, this.y);
        this.x = x;
        this.y = y;
        BattleGround.v().setFighterOn(x, y, this);
    }

    public boolean canMoveTo(int x, int y){
        int dist = Math.abs(this.x - x) + Math.abs(this.y - y);
        return dist <= this.moveRange;
    }

    public boolean canAttack(Fighter f){
        int dist = Math.abs(x - f.getX()) + Math.abs(y - f.getY());
        return dist <= this.attackRange;
    }

    public void getDemage(int demage){
        this.hp -= demage;
        return;
    }

    public void attack(Fighter defender){
        int demage = atk - defender.getDef();
        defender.getDemage(demage);
        System.out.println(name + " 攻击了 " + defender.getName() + " 造成了" + demage + "点伤害，还剩" + defender.getHp());
        return;
    }
}
