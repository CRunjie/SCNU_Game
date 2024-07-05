package com.scnu.element;

import com.scnu.manager.ElementManager;
import com.scnu.manager.GameElement;
import com.scnu.manager.GameLoad;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class Play extends ElementObj{
    //移动属性：
    //1.单属性 配合 方向枚举类型使用；一次只能移动一个方向
    //2.双属性 上下和左右   配合boolean值使用，例如：true代表上 false代表下
    //                                          需要另外一个变量来确定是否按下方向键
    //                                  约定：0 代表不动   1 代表上   2 代表下
    //3.四属性 上下左右都可以     boolean配合使用 true代表移动 false 代表不移动
    //                                 如果同时按上和下，后按的会重置先按的
//    说明：以上3种方式是代码编写和判定方式不一样
//    说明：游戏中非常多的判定，建议灵活使用判定属性，很多状态值也使用判定属性
//         多状态 可以使用map<泛型，boolean>; set<判定对象>   判定对象中有时间

//    问题：1.图片要读取到内存中：加载器        临时处理方式：手动编写存储到内存中
//         2.什么时候进行修改图片（因为图片是在父类中的属性存储
//         3.图片应该使用什么集合进行存储
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;


    //变量专门用来记录当前主角面向的方向，默认为向上up
    private String fx = "up";
    private boolean pkType = false; //攻击状态  true攻击，false停止  默认为false
    public Play(){}
    public Play(int x, int y, int w, int h, ImageIcon icon) {
        super(x, y, w, h, icon);
    }

    //面向对象的第一个思想：对象自己的事情自己做
    @Override
    public void showElement(Graphics g) {
//        绘画图片
        g.drawImage(this.getIcon().getImage(), this.getX(), this.getY(), this.getW(), this.getH(),null);
    }

    @Override
    public ElementObj createElement(String str) {
        String[] split = str.split(",");
        this.setX(new Integer(split[0]));
        this.setY(new Integer(split[1]));
        ImageIcon icon2 = GameLoad.imgMap.get(split[2]);
        this.setW(icon2.getIconWidth());
        this.setH(icon2.getIconHeight());
        this.setIcon(icon2);
        return this;
    }

    @Override
    public void move(){
        if(this.left && this.getX()>0){
                this.setX(this.getX()-2);
        }
        if(this.up && this.getY()>0){
            this.setY(this.getY()-2);
        }
        if(this.right && this.getX()<900-this.getW()){  //后面要进行一下坐标的调整
            this.setX(this.getX()+2);
        }

        if(this.down && this.getY()<600-this.getH()){   //后面要进行一下坐标的调整
            this.setY(this.getY()+2);
        }
    }


    //重写方法
    //重点：监听的数据需要改变状态值
    public void keyClick(boolean bl, int key){//只有按下或者松开才会调用这个方法
        if(bl){ //按下
            switch(key){//  怎么优化？ 监听会持续触发，有没有办法触发一次？
                case 37: this.right = false;this.left = true;this.up = false;this.down=false; this.fx = "left"; break;
                case 38: this.right = false;this.left = false;this.up = true;this.down=false; this.fx = "up"; break;
                case 39: this.right = true;this.left = false;this.up = false;this.down=false; this.fx = "right"; break;
                case 40: this.right = false;this.left = false;this.up = false;this.down=true; this.fx = "down"; break;
                case 32: this.pkType =true; break;  //开启攻击状态
            }
        }else{
            switch (key){
                case 37: this.left = false; break;
                case 38: this.up = false; break;
                case 39: this.right = false; break;
                case 40: this.down = false; break;
                case 32: this.pkType = false; break;  //关闭攻击状态
            }
        }

    }
//    重写父类中的换装方法(修改坦克朝向)
    @Override
    protected void updateImage(long gameTime) {
        this.setIcon(GameLoad.imgMap.get(fx));
    }

//    额外问题：1.请问重写的方法的访问修饰符是否可以修改
//             2.请问下面的add方法是否可以自动抛出异常
//    重写规则：1.重写方法的方法名称和返回值，必须和父类的一样
//             2.重写的方法的传入参数类型序列，必须和父类的一样
//             3.重写的方法的访问修饰符只能比父类的更加宽泛
//                 比方说：父类的方法是受保护的，但是现在需要在非子类调用
//                        可以直接子类继承，重写并super父类方法，public方法
//             4.重写的方法抛出的一场不可以比父类更加宽泛

//    子弹的添加需要的是发射者的坐标位置，发射者的方向
//    扩展：如果可以变换子弹应该怎么处理？

    private long fileTime = 0;
//    fileTime 和传入的时间gameTime进行比较，赋值等操作运算，控制子弹间隔
    @Override   //添加子弹
    public void add(long gameTime)
    {
        if(!this.pkType ){   //如果是不发射状态，就直接return
            return;
        }
        this.pkType = false;
        //new PlayFile();构造一个类，需要做比较多的工作，可以选择一种方式，使用小工厂
//        将构造对象的多个步骤进行封装成为一个方法，返回值直接是这个对象
//        传递一个固定格式｛x:3,y:5,f:up｝    json格式
        ElementObj obj = GameLoad.getObj("file");
        ElementObj element = obj.createElement(this.toString());  //以后的框架学习中会碰到，会帮助你返回对象的实体，并初始化数据
//        装入到集合当中
        ElementManager.getManager().addElement(GameElement.PLAYFILE,element);
//        如果要控制子弹速度等等，还需要进行代码编写

    }

    @Override
    public String toString(){   //这里是偷懒，直接使用toString 建议自己定义一个方法
        //{x:3,y:5,f:"up",}     json格式
        int x = this.getX(); int y = this.getY();
        switch (this.fx){   //子弹在发射的时候就一斤给予固定的轨迹。可以加上目标，修改json格式
//            一般不会写20等数值，一般情况下图片大小就是显示大小，一般情况下可以使用图片大小参与运算
            case "up": x+=20;break;
            case "left": y+=20;break;
            case "right": x+=50;y+=20;break;
            case "down": y+=50;x+=20;break;
        }
        return "x:"+x+",y:"+y+",f:"+this.fx;
    }


}
