package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.util.ImageMap;
import com.wjq.util.ImageUtil;

import java.awt.*;

public class Background extends BaseSprite implements Moveable, Drawable {

    private Image image;

    public Background() {
        this(0, FrameConstant.FRAME_HEIGHT - ImageMap.get("bg01").getHeight(null), ImageMap.get("bg01"));
    }


    public Background(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void move() {
        setY(getY() + FrameConstant.GAME_SPEED);

    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);
        move();
    }

}

