package com.wjq.runtime;

import com.wjq.base.BaseSprite;
import com.wjq.base.Drawable;
import com.wjq.base.Moveable;
import com.wjq.constant.FrameConstant;
import com.wjq.main.GameFrame;
import com.wjq.util.DateStore;
import com.wjq.util.ImageMap;

import java.awt.*;

public class Boss extends BaseSprite implements Moveable, Drawable {

    private static Image[] image = {
            ImageMap.get("boss1"),
            ImageMap.get("boss2"),
            ImageMap.get("boss3"),
            ImageMap.get("boss4"),
            ImageMap.get("boss5"),
            ImageMap.get("boss6"),
            ImageMap.get("boss7"),
            ImageMap.get("boss8"),
            ImageMap.get("boss9"),
    };
    private int step;

    int i;

    private boolean right;


    public Boss() {

            this((FrameConstant.FRAME_WIDTH - ImageMap.get("boss1").getWidth(null)) / 2,
                    0 - ImageMap.get("boss1").getHeight(null)-30);

    }

    public Boss(int x, int y) {
        super(x, y);
    }

//    public Boss(int x, int y) {
//
//    }



    @Override
    public void draw(Graphics g) {
        i++;
        if (GameFrame.score >= 20) {
            move();
        }
        fire();

        g.drawImage(image[step], getX(), getY(),
                image[step].getWidth(null), image[step].getHeight(null), null);
        step++;
        if (step >= 9) {
            step=0;
        }

    }

    @Override
    public void move() {
        if (getY() <= 75) {
            setY(getY() + 2);
        } else {
            if (!right) {
                setX(getX() - 2);
                if (getX() <= 0) {
                    right = true;
                }
            }
            if (right) {
                setX(getX() + 2);
                if (getX() >= FrameConstant.FRAME_WIDTH - image[0].getWidth(null)) {
                    right = false;
                }
            }
        }

    }


    public void fire() {
      if (i == 50){ GameFrame gameFrame = DateStore.get("gameFrame");
          gameFrame.bossBullets.add(new BossBullets(
                  getX() + image[0].getWidth(null) / 2 - ImageMap.get("bossbullet").getWidth(null) / 2,
                  getY() + image[0].getHeight(null),
                  ImageMap.get("bossbullet"),FrameConstant.GAME_SPEED * 4));
          i = 0 ;
      }


    }

    @Override
    public Rectangle getRectangle() {
        return new Rectangle(getX(),getY(),image[0].getWidth(null),image[0].getHeight(null));
    }
}