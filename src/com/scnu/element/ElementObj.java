package com.scnu.element;

import javax.swing.*;
import java.awt.*;

//说明：所有元素的基类
public abstract  class ElementObj {
    private int x;
    private int y;
    private int w;
    private int h;
    private ImageIcon icon;

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    //还有各种必要的状态值，例如是否生存
    private boolean live = true;   //生存状态  true代表存在，false代表死亡
                            //可以采用枚举值来定义（生存、死亡、隐身、无敌）

//    注明：当重新定义一个用于判定状态的变量，需要思考：1.初始化    2.值的改变  3.值的判定
    //这个构造其实没有作用，只为继承的时候不报错写的
    public  ElementObj(){
    }
    /*
    * @说明：带参数的构造方法，可以由子类传输数据到父类
    *
    */
    public ElementObj(int x, int y, int w, int h, ImageIcon icon) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
        this.icon = icon;
    }

    //抽象方法，显示元素
    //g 画笔用于绘画
    public abstract void showElement(Graphics g);
    //说明：需要移动的子类，需要实现这个方法
    protected void move(){}
//    设计模式：模板模式；在模版模式中定义对象执行方法的先后顺序，由子类选择性重写方法
//    1.移动行为    2.换装行为  3.子弹发射行为
    public final void model(long gameTime){  //final:不允许重写
//        先换装、再移动、再发射子弹
        updateImage(gameTime);
        move();
        add(gameTime);
    }

    protected void add(long gameTime) {
    }

    // long ... aaa 不定长的数组，可以向这个方法传输N个Long类型的数据
    protected void updateImage(long gameTimes) {

    }
//  死亡方法,给子类继承的
    public void die() {//死亡也是一个对象

    }

    public  ElementObj createElement(String str){
        return null;
    }


//    说明：本方法返回元素的碰撞矩形对象（实时返回）
    public Rectangle getRectangle(){
//        可以将这个数据进行处理
        return new Rectangle(x, y, w, h);
    }
//    说明：碰撞方法
//    碰撞检测，一个是this对象，一个是传入值obj
//    返回true说明有碰撞，返回false说明没有碰撞
    public boolean pk(ElementObj obj){
        return this.getRectangle().intersects(obj.getRectangle());
    }


    //只要是VO类，都要为属性生成set和get方法
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getW() {
        return w;
    }

    public void setW(int w) {
        this.w = w;
    }

    public int getH() {
        return h;
    }

    public void setH(int h) {
        this.h = h;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    //使用父类定义接收键盘事件的方法
    //只有需要实现键盘监听的子类，重写这个方法（约定）
    //方式2：使用定义接口的方式；使用接口方式需要在监听类进行类型转换
    //@param bl     点击的类型 true 代表按下，false代表松开
    //@param key    代表触发的键盘的code值
    //扩展    本方法是否可以分为两个方法？1个接收按下，一个接收松开
    public void keyClick(boolean bl, int key){ //这个方法不是强制必须重写

    }


}
