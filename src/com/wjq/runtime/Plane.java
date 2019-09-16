package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyEvent;

public class Plane extends BaseSprite implements Moveable, Drawable {

    private Image image;

    private boolean up, right, down, left;

    private boolean fire;

    private int speed = FrameConstant.GAME_SPEED * 8;

    private int index = 0;


    public Plane() {
        this((FrameConstant.FRAME_WIDTH - ImageMap.get("my01").getWidth(null)) / 2,
                FrameConstant.FRAME_HEIGHT - ImageMap.get("my01").getHeight(null),
                ImageMap.get("my01"));
    }


    public Plane(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {
        move();
        fire();
        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);
        index++;
        if (index >= 10) {
            index = 0;
        }
    }

    public void fire() {
        if (fire && index == 0) {
            GameFrame gameFrame = DateStore.get("gameFrame");
            gameFrame.bulletCopyOnWriteArrayList.add(new Bullet(
                    getX() + image.getWidth(null) / 2 - ImageMap.get("myb01").getWidth(null) / 2,
                    getY() - ImageMap.get("myb01").getHeight(null),
                    ImageMap.get("myb01")
            ));
        }
    }

    @Override
    public void move() {
        if (up) {
            setY(getY() - speed);
        }
        if (right) {
            setX(getX() + speed);
        }
        if (down) {
            setY(getY() + speed);
        }
        if (left) {
            setX(getX() - speed);
        }
        borderTesting();
    }

    public void borderTesting() {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH - image.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH - image.getWidth(null));
        }
        if (getY() < 37) {
            setY(37);
        }
        if (getY() > FrameConstant.FRAME_HEIGHT - image.getHeight(null)) {
            setY(FrameConstant.FRAME_HEIGHT - image.getHeight(null));
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            fire = true;
        }
    }

    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            up = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            right = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_S) {
            down = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_A) {
            left = false;
        }
        if (e.getKeyCode() == KeyEvent.VK_J) {
            fire = false;
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), image.getWidth(null), image.getHeight(null));

    }
}