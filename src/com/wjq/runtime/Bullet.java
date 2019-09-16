package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;
import java.util.List;

public class Bullet extends BaseSprite implements Moveable, Drawable {

    private Image image;

    int i ;

    private int speed = FrameConstant.GAME_SPEED * 5;

    public Bullet() {
        this(0, 0, ImageMap.get("myb1"));
    }

    public Bullet(int x, int y, Image image) {
        super(x, y);
        this.image = image;
    }

    @Override
    public void draw(Graphics g) {

        g.drawImage(image, getX(), getY(), image.getWidth(null), image.getHeight(null), null);
        move();
        borderTesting();
    }

    @Override
    public void move() {
        setY(getY() - speed);
    }

    public void borderTesting() {
        if (getY() < 30 - image.getHeight(null)) {
            GameFrame gameFrame = DateStore.get("gameFrame");
            gameFrame.bulletCopyOnWriteArrayList.remove(this);
        }
    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image.getWidth(null),image.getHeight(null));
    }

    public void collisionTesting(List<EnemyPlane> enemyPlaneList){
        GameFrame gameFrame = DateStore.get("gameFrame");
        for (EnemyPlane enemyPlane : enemyPlaneList) {
            if (enemyPlane.getRectangle().intersects(this.getRectangle())){
                enemyPlaneList.remove(enemyPlane);
                gameFrame.bulletCopyOnWriteArrayList.remove(this);
                gameFrame.score++;
            }


        }
    }
    public void collisionTesting(Boss boss) {
        GameFrame gameFrame = DateStore.get("gameFrame");
       /* for (Bullet bullet : gameFrame.bulletCopyOnWriteArrayList) {
            if (bullet.getRectangle().intersects(boss.getRectangle())) {
//            gameFrame.bulletCopyOnWriteArrayList.remove(this);
                FrameConstant.BossHP--;
                if (FrameConstant.BossHP == 0) {
                    gameFrame.gameOver = true;
                }
            }
        }*/

        if (this.getRectangle().intersects(boss.getRectangle())) {
            gameFrame.bulletCopyOnWriteArrayList.remove(this);
            FrameConstant.BossHP--;
            if (FrameConstant.BossHP == 0) {
                gameFrame.gameOver = true;
            }
        }
    }

}
