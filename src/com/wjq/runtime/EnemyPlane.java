package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;
import java.util.Random;

public class EnemyPlane extends BaseSprite implements Moveable, Drawable {
    private Image image1;

    private Image image2;

    private int speed = FrameConstant.GAME_SPEED * 2;

    private Random random = new Random();

    private int type;

    public EnemyPlane() {
        this(0, 0, 1);
    }

    public EnemyPlane(int x, int y, int type) {
        super(x, y);
        this.type = type;
        this.image1 = ImageMap.get("ep01");
        this.image2 = ImageMap.get("ep02");
    }

    @Override
    public void draw(Graphics g) {
        move();
        fire();
        if (type == 1) {
            g.drawImage(image1, getX(), getY(), image1.getWidth(null), image1.getHeight(null), null);

        } else if (type == 2) {
            g.drawImage(image2, getX(), getY(), image2.getWidth(null), image2.getHeight(null), null);

        }
    }

    public void fire() {

        GameFrame gameFrame = DateStore.get("gameFrame");
        if (random.nextInt(1000) > 990) {
            gameFrame.enemyBullets.add(new EnemyBullet(
                    getX() + image1.getWidth(null) / 2 - ImageMap.get("epb01").getWidth(null) / 2,
                    getY() + image1.getHeight(null),
                    type));

        }
    }


    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting1();
        borderTesting2();
    }

    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DateStore.get("gameFrame");
            gameFrame.enemyPlaneList.remove(this);
        }

    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(), getY(), image1.getWidth(null), image1.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DateStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.enemyPlaneList.remove(this);
            FrameConstant.hp--;
            if (FrameConstant.hp <= 0) {
                gameFrame.gameOver = true;
            }
        }
    }


    public void borderTesting1() {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH - image1.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH - image1.getWidth(null));
        }
    }
    public void borderTesting2 () {
        if (getX() < 0) {
            setX(0);
        }
        if (getX() > FrameConstant.FRAME_WIDTH - image2.getWidth(null)) {
            setX(FrameConstant.FRAME_WIDTH - image2.getWidth(null));
        }
    }
}
