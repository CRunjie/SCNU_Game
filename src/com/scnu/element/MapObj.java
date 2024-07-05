package com.scnu.element;

import javax.swing.*;
import java.awt.*;

public class MapObj extends ElementObj{
    private int hp; //血量
    private String name; //墙的类型，这个也可以使用枚举

    @Override
    public void showElement(Graphics g) {
        g.drawImage(this.getIcon().getImage(),
                this.getX(),this.getY(),this.getW(),this.getH(),null);
    }

//    如果可以传入    墙类型,x,y
    @Override
    public ElementObj createElement(String str) {
        String[] arr = str.split(",");
//        先写一个假图片
        ImageIcon icon = null;
        switch (arr[0]){    //设置图片信息，图片还未加载到内存中
            case "GRASS":icon = new ImageIcon("image/wall/grass.png");break;
            case "BRICK":icon = new ImageIcon("image/wall/brick.png");break;
            case "RIVER":icon = new ImageIcon("image/wall/river.png");break;
            case "IRON": icon = new ImageIcon("image/wall/iron.png");
                this.hp = 4;
                name = "IRON";
            break;
        }
        int x = Integer.parseInt(arr[1]);
        int y = Integer.parseInt(arr[2]);
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        this.setX(x);
        this.setY(y);
        this.setW(w);
        this.setH(h);
        this.setIcon(icon);
        return this;
    }
    @Override   //说明：这个设置扣血等的方法，需要自己思考重新编写
    public void setLive(boolean live) {
//        被调用一次就减少一次血
        if("IRON".equals(name)){// 水泥墙需要4枪
            this.hp--;
            if(this.hp>0){
                return;
            }
        }
        super.setLive(live);
    }

}
