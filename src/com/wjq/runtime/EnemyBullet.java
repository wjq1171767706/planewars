package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;

public class EnemyBullet extends BaseSprite implements Moveable, Drawable {

    private  Image image1 = ImageMap.get("epb01");

    private Image image2 = ImageMap.get("epb02");

    private int type;

    private  int speed = FrameConstant.GAME_SPEED*3;

    public EnemyBullet() {
    }

    public EnemyBullet(int x, int y, int type) {
        super(x, y);
        this.type = type;
        this.image1 = ImageMap.get("epb01");
        this.image2 = ImageMap.get("epb02");
    }




    @Override
    public void draw(Graphics g) {
        move();
        if (type == 1) {
            g.drawImage(image1, getX(), getY(), image1.getWidth(null), image1.getHeight(null), null);

        } else if (type == 2) {
            g.drawImage(image2, getX(), getY(), image2.getWidth(null), image2.getHeight(null), null);

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
            gameFrame.enemyBullets.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image1.getWidth(null),image1.getHeight(null));
    }

    public void collisionTesting(Plane plane) {
        GameFrame gameFrame = DateStore.get("gameFrame");
        if (plane.getRectangle().intersects(this.getRectangle())) {
            gameFrame.enemyBullets.remove(this);
            FrameConstant.hp--;
            if (FrameConstant.hp == 0) {
                gameFrame.gameOver = true;
            }
        }
    }

}
