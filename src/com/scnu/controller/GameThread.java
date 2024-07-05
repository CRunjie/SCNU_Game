package com.scnu.controller;

import com.scnu.element.ElementObj;
import com.scnu.element.Enemy;
import com.scnu.element.Play;
import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;
import com.scnu.manager.GameLoad;

import javax.swing.*;
import java.util.List;
import java.util.Map;

//说明：游戏的主线程，用于控制游戏加载，游戏关卡，游戏运行时自动化
//     游戏判定，游戏地图切换  资源释放和重新读取
// 使用继承的方式实现多线程(一般建议使用接口实现)
public class GameThread extends Thread {
    private ElementManager em;

    public GameThread() {
        em = ElementManager.getManager();
    }
    @Override
    public void run() { //游戏的run方法，主线程
        while (true) {  //扩展，可以将true变为一个变量用于控制结束
//  游戏开始前      读进度条，加载游戏资源（场景资源）
            gameLoad();
//  游戏进行时      游戏过程中
            gameRun();
//  游戏场景结束     游戏资源回收（场景资源）
            gameOver();

            try {
                sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


//    游戏加载
    private void gameLoad() {
        GameLoad.loadImg(); //加载图片
        GameLoad.MapLoad(5);    //加载地图，可以变为变量，每一关重新加载
//        加载主角
        GameLoad.loadPlay();//也可以带参数，选择单人玩家还是双人玩家
//        加载敌人NPC

//        全部加载完成，游戏启动
    }
//    游戏进行时
//    任务说明：1.自动化玩家的移动，碰撞，死亡
//             2.新元素的增加（NPC死亡之后出现道具）
//              3.暂停等等
//

    private  long gameTime = 0L;
    private void gameRun() {
        while(true){//预留扩展：true可以变为变量，用于控制关卡结束等
            Map<GameElement, List<ElementObj>> all = em.getGameElements();
            List<ElementObj> files = em.getElementsByKey(GameElement.PLAYFILE);
            List<ElementObj> enemys = em.getElementsByKey(GameElement.ENEMY);
            List<ElementObj> maps = em.getElementsByKey(GameElement.MAPS);

            moveAndUpdate(all, gameTime);//游戏元素自动化方法
            ElementPK(enemys,files);
            ElementPK(maps,files);
            gameTime++;//唯一的时间控制
            try {
                sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void ElementPK(List<ElementObj> listA, List<ElementObj> listB ) {
//      在这里使用循环，做一对一判定，如果为真，就设置两个对象的死亡状态
        for(int i = 0; i < listA.size(); i++) {
            ElementObj file = listA.get(i);
            for(int j = 0; j<listB.size();j++){
                ElementObj hitted = listB.get(j);
                if(hitted.pk(file) == true){
//                    问题：如果是boss，不是一枪一个，应该怎么修改？
//                    将setLive(false)变为一个受攻击方法，还可以传入另外一个对象的攻击力
//                    当受攻击方法里执行时，如果血量减为0，再设置生存状态为false
//                    if(hitted.getClass().toString() == "")
                    file.setLive(false);
                    hitted.setLive(false);
                    break; //只要一个对方死就跳出循环
                }
            }
        }

    }

    //    游戏元素自动化方法
    public void moveAndUpdate(Map<GameElement, List<ElementObj>> all,long gameTime){
        //GameElement.values();   //这是一个隐藏方法，返回值是一个数组，数组的顺序就是定义枚举的顺序
        for(GameElement ge:GameElement.values()){
            List<ElementObj> list = all.get(ge);
//                for(int i = 0;i<list.size();i++){
//                编写这样直接操作集合数据的代码建议不要使用迭代器
            for(int i = list.size()-1;i>=0;i--){
                ElementObj obj = list.get(i);//读取为基类
                if(!obj.isLive()){
//                        list.remove(i--); //如果生存状态是false的话，直接将其从列表里面删除
//                        启动一个死亡方法（方法中可以做很多事情，例如：死亡动画、掉装备）
                    obj.die();
                    list.remove(i);
                    continue;
                }
                obj.model(gameTime);//调用模板方法
            }
        }
    }

//    游戏切换关卡
    private void gameOver() {
    }

}
