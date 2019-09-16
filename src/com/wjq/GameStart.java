package com.wjq;

import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;

public class GameStart {


    public static void main(String[] args) {
        GameFrame gameFrame = new GameFrame();
        DateStore.put("gameFrame",gameFrame);
        gameFrame.innit();
    }
}
