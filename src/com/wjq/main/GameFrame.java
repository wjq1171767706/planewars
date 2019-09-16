package com.wjq.main;

import com.wjq.constant.FrameConstant;
import com.wjq.runtime.*;
import com.wjq.util.ImageMap;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameFrame extends Frame {


    //创建背景对象
    private Background background = new Background();

    //创建飞机对象
    private Plane plane = new Plane();

    //创建血包集合
    public final List<AddBlood> addBloods = new CopyOnWriteArrayList<>();

    //创建子弹集合
    public final CopyOnWriteArrayList<Bullet> bulletCopyOnWriteArrayList = new CopyOnWriteArrayList<>();

    //创建敌方子弹集合
    public final List<EnemyBullet> enemyBullets = new CopyOnWriteArrayList<>();

    //创建敌方飞机
    public final List<EnemyPlane> enemyPlaneList = new CopyOnWriteArrayList<>();

    //创建Boss
    public final List<Boss> bossList = new CopyOnWriteArrayList<>();

    //创建Boss子弹集合
    public final List<BossBullets> bossBullets = new CopyOnWriteArrayList<BossBullets>();

    public boolean gameOver = false;

    public static int score;
    private Boss boss = new Boss();

    Random a = new Random();

    @Override
    public void paint(Graphics g) {


        if (!gameOver) {
            background.draw(g);
            plane.draw(g);
            if(a.nextInt(1000)>990){
            enemyPlaneList.add(new EnemyPlane(a.nextInt(800), 0, 1));
            enemyPlaneList.add(new EnemyPlane(a.nextInt(800), 0, 2));
            addBloods.add(new AddBlood(a.nextInt(800),0,ImageMap.get("blood")));
        }
            if (!gameOver ) {
                if(score == 20){

                }
            }
            //加血包
            for (AddBlood addBlood : addBloods) {
                addBlood.draw(g);
            }
            //我方子弹
            for (Bullet bullet : bulletCopyOnWriteArrayList) {
                bullet.draw(g);

            }
            for (Bullet bullet : bulletCopyOnWriteArrayList) {
                bullet.collisionTesting(boss);
            }
            //敌方子弹
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.draw(g);
            }
            //敌机
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.draw(g);
            }
            //Boss
            for (Boss boss : bossList) {
                boss.draw(g);
            }

            for (BossBullets bossBullet : bossBullets) {
                bossBullet.draw(g);
            }

            g.setColor(Color.red);
            g.setFont(new Font("楷体", Font.BOLD, 24));
            g.drawString("得分" + score, 700, 75);
            //血包碰撞检测
            for (AddBlood addBlood : addBloods) {
                addBlood.collisionTesting(plane);
            }
            //我方子弹碰撞检测
            for (Bullet bullet : bulletCopyOnWriteArrayList) {
                bullet.collisionTesting(enemyPlaneList);
            }
            //敌方子弹碰撞检测
            for (EnemyBullet enemyBullet : enemyBullets) {
                enemyBullet.collisionTesting(plane);
            }
            //敌机碰撞检测
            for (EnemyPlane enemyPlane : enemyPlaneList) {
                enemyPlane.collisionTesting(plane);
            }
//            Boss碰撞检测
            for (Boss boss : bossList) {
            }

            for (BossBullets bossBullet : bossBullets) {
                bossBullet.collisionTesting(plane);
            }

            g.setColor(Color.red);
            g.setFont(new Font("楷体", Font.PLAIN, 24));
            g.drawString("hp:" + FrameConstant.hp, 50, 75);
        }else
        {
            g.setColor(Color.red);
            g.setFont(new Font("微软雅黑", Font.PLAIN, 48));
            g.drawString("YOU  WIN", 300, 500);
        }
    }


    public void innit() {
        //设置好尺寸
        setSize(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        //设置居中
        setLocationRelativeTo(null);

        enableInputMethods(false);

        setResizable(false);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                plane.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                plane.keyReleased(e);
            }
        });

        bossList.add(boss);
        new Thread() {
            @Override
            public void run() {

                while (true) {
                    if (FrameConstant.hp>0){
                    repaint();
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                }
                }

                }

        }.start();


        setVisible(true);
    }

    private Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(FrameConstant.FRAME_WIDTH, FrameConstant.FRAME_HEIGHT);
        }
        Graphics gOff = offScreenImage.getGraphics();

        paint(gOff);
        g.drawImage(offScreenImage, 0, 0, null);
    }

}
