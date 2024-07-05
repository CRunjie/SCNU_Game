package com.scnu.manager;

public enum GameElement {
    //玩家、地图、敌人、大boss
    MAPS,PLAY,ENEMY, BOSS,PLAYFILE,DIE; //枚举类型的顺序是声明的顺序
    //我们定义的枚举类型，在编译的时候，虚拟机会自动帮助生成class文件，并且会加载很多的代码和方法
}
