package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;

import java.awt.*;
import java.util.Random;

public class AddBlood extends BaseSprite implements Moveable, Drawable {
    private Image image;

    private int speed = FrameConstant.GAME_SPEED * 1;

    private Random random = new Random();

    public AddBlood() {
    }

    public AddBlood(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);

    }


    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting3();
    }

    public void borderTesting3() {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH - image.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH - image.getWidth(null));
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DateStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.addBloods.remove(this);
            FrameConstant.hp++;
            if (FrameConstant.hp == 0) {
                gameFrame.gameOver = true;
            }
            if (FrameConstant.hp >= 50) {
                FrameConstant.hp = 50;
            }
        }
    }

}
