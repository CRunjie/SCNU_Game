package com.scnu.manager;

import com.scnu.element.ElementObj;
import com.scnu.element.MapObj;
import com.scnu.element.Play;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

//说明：加载器（工具：用户读取配置文件的工具）工具类，大多提供的是static方法
public class GameLoad {
//    得到资源管理器
    private static ElementManager em = ElementManager.getManager();

    //    图片集合  使用map来进行存储      枚举类型配合移动（扩展）
    public static Map<String, ImageIcon> imgMap = new HashMap<>();

//    用户读取文件的类
    private static Properties pro = new Properties();


//    说明：传入地图ID，由加载方法依据文件规则自动产生地图文件名称，加载文件
    public static void MapLoad(int mapId){
//        得到我们的文件路径
        String mapName = "com/scnu/text/" +mapId+".map";
//        使用IO流来获取文件对象  得到类加载器
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream maps = classLoader.getResourceAsStream(mapName);
        if(maps == null){
            System.out.println("配置文件读取异常，请重新安装");
            return;
        }
        try {
//            以后用到xml和json
            pro.clear();
            pro.load(maps);
//            可以直接动态的获取所有的key，有key就可以获取value
            Enumeration<?> names = pro.propertyNames();
            while(names.hasMoreElements()){ //获取是无序的
//                注意：这样的迭代都有一个问题：一次迭代一个元素
                String key = (String) names.nextElement();
//                接下来可以自动创建和加载地图
                String[] arrs = pro.getProperty(key).split(";");
                for(int i = 0 ; i<arrs.length;i++){
                    ElementObj element = new MapObj().createElement(key + "," + arrs[i]);
                    em.addElement(GameElement.MAPS,element);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    说明：加载图片代码
//    加载图片  代码和图片之间差一个路径问题
    public static void loadImg(){   //可以带参数，因为不同的关卡可能需要不一样的图片资源
        String texturl = "com/scnu/text/GameData";  //文件的命名可以更加有规律
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = classLoader.getResourceAsStream(texturl);
//        imgMap用于存放数据
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet(); //是一个set集合
            for(Object o : set){
                String url = pro.getProperty(o.toString());
                imgMap.put(o.toString(), new ImageIcon(url));  //通过类加载器来得到图片
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    public static void loadPlay() {
        loadObj();
        String playStr = "500,500,up";
        ElementObj obj = getObj("play");
        ElementObj play = obj.createElement(playStr);
//        ElementObj play = new Play().createElement(playStr);
//        解耦，降低代码和代码之间的耦合度  可以通过接口或者是抽象父类就可以获取到实体对象
        em.addElement(GameElement.PLAY, play);
    }

    public static ElementObj getObj(String str) {
        try {
            Class<?> class1 = objMap.get(str);
            Object newInstance = class1.newInstance();
            if(newInstance instanceof ElementObj){
                return  (ElementObj) newInstance; //这个对象就和 new Play()　　　  等价
//              新建立了一个叫GamePlay的类
            }
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return null;
    }


//    扩展：使用配置文件来实例化对象，通过固定的key（字符串来实例化）
    private static Map<String,Class<?>> objMap = new HashMap<>();
    public static void loadObj(){
        String textUrl = "com/scnu/text/obj";
        ClassLoader classLoader = GameLoad.class.getClassLoader();
        InputStream texts = ClassLoader.getSystemResourceAsStream(textUrl);
        pro.clear();
        try {
            pro.load(texts);
            Set<Object> set = pro.keySet(); //是一个set集合
            for(Object o : set){
                String classUrl = pro.getProperty(o.toString());
//                使用反射的方式直接将类进行获取
                Class<?> forName = Class.forName(classUrl);
                objMap.put(o.toString(), forName);
            };
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }



//    用于测试的一个类
//    public static void main(String[] args) {
//        MapLoad(5);
//    }

}