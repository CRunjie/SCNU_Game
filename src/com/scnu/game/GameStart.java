package com.scnu.game;

import com.scnu.controller.GameListener;
import com.scnu.controller.GameThread;
import com.scnu.show.GameJFrame;
import com.scnu.show.GameMainJPanel;

//程序的唯一入口
public class GameStart {
    public static void main(String[] args) {
        GameJFrame gj = new GameJFrame();
        //实例化面板，注入到jFrame中
        GameMainJPanel jp = new GameMainJPanel();
        //实例化监听
        GameListener listener = new GameListener();
        //实例化主线程
        GameThread thread = new GameThread();
        //注入
        gj.setjPanel(jp);
        gj.setKeyListener(listener);
        gj.setThread(thread);
        gj.start();
        Enum em = null;
    }
}
