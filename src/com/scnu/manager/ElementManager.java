package com.scnu.manager;

import com.scnu.element.ElementObj;

import java.util.*;

//说明：本类是元素管理器，专门存储所有的元素，同时提供方法给予视图和控制获取数据
//问题1：存储所有元素数据，要怎么存放？   list map set 三大集合
//问题2：管理器是视图和控制要访问，管理器就必须有一个，单例模式
public class ElementManager {
    //使用枚举类型，当作map的key用来区分不一样的资源，用于获取资源
    //List集合中的泛型应该是元素的基类
    //所有元素都可以存放到map集合，显示模块只需要获取到这个map就可以
    //显示所有的界面需要的元素（调用元素基类的showElement()方法）
    private Map<GameElement,List<ElementObj>> gameElements; //在后面会有一点小问题？
    public Map<GameElement, List<ElementObj>> getGameElements() {
        return gameElements;
    }
    //添加元素（多半由加载器调用）
    public void addElement(GameElement ge, ElementObj obj){
        List<ElementObj> list = gameElements.get(ge);
        list.add(obj);
    }
    //依据key返回list集合，取出某一类元素
    public List<ElementObj> getElementsByKey(GameElement ge){
        return gameElements.get(ge);
    }



    //单例模式
    private static ElementManager EM = null;
    //synchronized线程锁：保证本方法执行中只有一个线程
    //饱汉模式（需要使用的时候才去加载实例）
    public static  synchronized ElementManager getManager(){
        if(EM == null){
            //空值判定
            EM = new ElementManager();
        }
        return  EM;
    }
    private ElementManager(){   //构造方法私有化
        init(); //实例化方法（原因：构造方法不能被继承重写，这样可以利于重写）
    }

//    static{//饿汉模式，静态语句块是在类被加载的时候就直接执行
//        EM = new ElementManager();//这段代码只会执行一次
//    }

    //本方法是为将来可能出现的功能扩展，重写init方法准备的
    public void init(){//在这里实现实例化
        gameElements = new HashMap<GameElement,List<ElementObj>>();
        //将每种元素集合都放到map中
//        gameElements.put(GameElement.PLAY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.MAPS, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.ENEMY, new ArrayList<ElementObj>());
//        gameElements.put(GameElement.BOSS, new ArrayList<ElementObj>());

        for(GameElement ge: GameElement.values()){//通过循环读取枚举类型的方式添加集合
            gameElements.put(ge,new ArrayList<ElementObj>());
        }
        //还可能会有道具、子弹、爆炸效果等等
    }
}
