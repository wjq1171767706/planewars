package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;

public class BossBullets extends BaseSprite implements Moveable, Drawable {

    private  Image image = ImageMap.get("bossbullet");




    private  int speed = FrameConstant.GAME_SPEED*3;

    public BossBullets() {

    }

    public BossBullets(int x, int y, Image image,  int speed) {
        super(x, y);
        this.image = image;
        this.speed = speed;
    }



    @Override
    public void draw(Graphics g) {

        if (GameFrame.score >= 20) {
            move();

            g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);
        }
    }

    @Override
    public void move() {
        setY(getY() + speed);
        borderTesting();
    }
    public void borderTesting() {
        if (getY() > FrameConstant.FRAME_HEIGHT) {
            GameFrame gameFrame = DateStore.get("gameFrame");
            gameFrame.bossBullets.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DateStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.bossBullets.remove(this);
            FrameConstant.hp-=5;
            if (FrameConstant.hp == 0) {
                gameFrame.gameOver = true;
            }
        }
    }

}
