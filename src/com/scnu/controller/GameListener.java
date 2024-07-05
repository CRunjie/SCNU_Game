package com.scnu.controller;

import com.scnu.element.ElementObj;
import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

//说明：监听类用于监听用户的操作KeyListener
public class GameListener implements KeyListener {
    private ElementManager em = ElementManager.getManager();

//    通过一个集合来记录所有按下的键，如果重复触发，就直接结束
//    同时，第一次按下，记录到集合中，第二次判定集合中是否有。
//    松开就直接删除集合中的记录
//    set集合
    private Set<Integer> set = new HashSet<Integer>();

    @Override
    public void keyTyped(KeyEvent e) {

    }
    //left 37   up 38   right 39    down 40
    //实现主角的移动

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if(set.contains(key)){//判定集合中是否已经包含这个对象，如果包含就直接返回
            return;
        }else{
            //如果不包含，就加入到集合中
            set.add(key);
        }
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);//得到了玩家集合
        for(ElementObj obj:play){
            obj.keyClick(true,e.getKeyCode());
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if(set.contains(e.getKeyCode())){
            set.remove(e.getKeyCode());
        }
        List<ElementObj> play = em.getElementsByKey(GameElement.PLAY);//得到了玩家集合
        for(ElementObj obj:play){
            obj.keyClick(false,e.getKeyCode());
        }
    }
}
