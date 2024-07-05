package com.scnu.element;
//说明：玩家子弹类
//本类是的实体对象是由玩家对象调用和创建
//子类的开发步骤
//1.继承于元素基类；重写show方法
//2.按照需求选择性重写其它方法 例如：move等
//3.思考并定义子类特有的属性

import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;

import javax.swing.*;
import java.awt.*;

public class PlayFile extends ElementObj{
    private int attack;//攻击力
    private int moveNum=2;//移动速度值
    private String fx;

    public PlayFile() {}   //一个空的构造方法
//    扩展：可以扩展出多种类型的导弹，例如激光、导弹等等（玩家类就需要有子弹类型）

//    对创建这个对象的过程进行封装，外界只需要传输必要的约定参数，返回值就是对象实体
    @Override
    public  ElementObj createElement(String str){//定义字符串的规则
//        进行字符串解析
        String[] split = str.split(",");
//        for(String sp: split){
//            System.out.println(sp);
//        }
        for(String str1:split){
            String[] split2 = str1.split(":");//    0下标是x, 1下标是值...
            switch (split2[0]){
            case "x": this.setX(Integer.parseInt(split2[1]));   break;
            case "y": this.setY(Integer.parseInt(split2[1])); break;
            case "f": this.fx = split2[1];  break;
            }
        }
        this.setW(10);
        this.setH(10);
        return this;
    }

    @Override
    public void showElement(Graphics g) {
        g.setColor(Color.red);
//        int x = this.getX();
//        switch (this.fx){   //依据不一样的方向进行子弹调整，问题：应该是在这里调整吗？需要自己课后进行操作
//            case "up": x+=20;break;
//        }
        g.fillOval(this.getX(),this.getY(),this.getW(),this.getH());

    }
    @Override
    protected void move(){
        if(this.getX()<0 || this.getX()>500 || this.getY()<0 || this.getY()>600){
            this.setLive(false);
            return;  //如果出界,就不用再执行move方法
        }
        switch (this.fx){
            case "up": this.setY(this.getY()-this.moveNum); break;
            case "down": this.setY(this.getY()+this.moveNum); break;
            case "left": this.setX(this.getX()-this.moveNum); break;
            case "right": this.setX(this.getX()+this.moveNum); break;
        }
    }

//    （死亡）对于子弹来说： 1.出边界     2.碰撞    3.玩家放保险

//    处理方式就是，当达到死亡的条件时，只进行修改死亡状态的操作。
//    @Override
//    public void die(){
//        ElementManager em = ElementManager.getManager();
//        ImageIcon icon = new ImageIcon("image/tank/play2/player_up.png");
//        ElementObj obj = new Play(this.getX(),this.getY(),50,50,icon);
//        em.addElement(GameElement.PLAY,obj);
//    }

////    子弹变装
//    private long time = 0;
//    protected void updateImage(long gameTime){
//        if(gameTime-time>5){
//            time = gameTime;
//            this.setW(this.getW()+2);
//            this.setH(this.getH()+2);
//            你变图片不就完了
//        }
//    }

}
