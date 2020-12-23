package com.edu.nju.huluwa.roles;

public abstract class Human extends Fighter {

    public void talk(){
        System.out.println("I am human");
    }
}

class Huluwa extends Human{

    @Override
    public void talk(){
        System.out.println("I am Huluwa");
    }
}

class Grandpa extends Human{
    @Override
    public void talk(){
        System.out.println("I am grandpa");
    }
}
