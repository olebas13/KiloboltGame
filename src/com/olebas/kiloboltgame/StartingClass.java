package com.olebas.kiloboltgame;

import com.olebas.kiloboltgame.framework.Animation;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;
import java.util.List;

public class StartingClass extends Applet implements KeyListener, Runnable{

    public final static int SCREEN_WIDTH = 800;
    public final static int SCREEN_HEIGHT = 480;

    private Robot robot;
    private Heliboy hb;
    private Heliboy hb2;
    private Image image;
    private Image currentSprite;
    private Image character;
    private Image character2;
    private Image character3;
    private Image characterDown;
    private Image characterJumped;
    private Image background;
    private Image heliboy;
    private Image heliboy2;
    private Image heliboy3;
    private Image heliboy4;
    private Image heliboy5;
    private Graphics second;
    private URL base;

    private Animation anim;
    private Animation hanim;

    private static Background bg1;
    private static Background bg2;

    @Override
    public void init() {
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Q-Bot Alpha");
        try {
            base = StartingClass.class.getResource("/data/");
        } catch (Exception e) {
            e.printStackTrace();
        }

        character = getImage(base, "character.png");
        character2 = getImage(base, "character2.png");
        character3 = getImage(base, "character3.png");

        characterDown = getImage(base, "down.png");
        characterJumped = getImage(base, "jumped.png");

        heliboy = getImage(base, "heliboy.png");
        heliboy2 = getImage(base, "heliboy2.png");
        heliboy3 = getImage(base, "heliboy3.png");
        heliboy4 = getImage(base, "heliboy4.png");
        heliboy5 = getImage(base, "heliboy5.png");

        background = getImage(base, "background.png");

        anim = new Animation();
        anim.addFrame(character, 1250);
        anim.addFrame(character2, 50);
        anim.addFrame(character3, 50);
        anim.addFrame(character2, 50);

        hanim = new Animation();
        hanim.addFrame(heliboy, 100);
        hanim.addFrame(heliboy2, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy5, 100);
        hanim.addFrame(heliboy4, 100);
        hanim.addFrame(heliboy3, 100);
        hanim.addFrame(heliboy2, 100);

        currentSprite = anim.getImage();
    }

    @Override
    public void start() {
        bg1 = new Background(0, 0);
        bg2 = new Background(2160, 0);
        robot = new Robot();
        hb = new Heliboy(340, 360);
        hb2 = new Heliboy(700, 360);
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void stop() {
        super.stop();
    }

    @Override
    public void destroy() {
        super.destroy();
    }

    @Override
    public void run() {
        while (true) {
            robot.update();
            if (robot.isJumped()) {
                currentSprite = characterJumped;
            } else if (!robot.isJumped() && !robot.isDucked()) {
                currentSprite = anim.getImage();
            }

            List<Projectile> projectiles = robot.getProjectiles();
            for (int i = 0; i < projectiles.size(); i++) {
                Projectile p = (Projectile) projectiles.get(i);
                if (p.isVisible()) {
                    p.update();
                } else {
                    projectiles.remove(i);
                }
            }

            hb.update();
            hb2.update();
            bg1.update();
            bg2.update();

            animate();
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void animate() {
        anim.update(10);
        hanim.update(50);
    }

    @Override
    public void update(Graphics g) {
        if (image == null) {
            image = createImage(this.getWidth(), this.getHeight());
            second = image.getGraphics();
        }

        second.setColor(getBackground());
        second.fillRect(0, 0, getWidth(), getHeight());
        second.setColor(getForeground());
        paint(second);

        g.drawImage(image, 0, 0, this);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(background, bg1.getBgX(), bg1.getBgY(), this);
        g.drawImage(background, bg2.getBgX(), bg2.getBgY(), this);

        List<Projectile> projectiles = robot.getProjectiles();
        for (int i = 0; i < projectiles.size(); i++) {
            Projectile p = (Projectile) projectiles.get(i);
            g.setColor(Color.YELLOW);
            g.fillRect(p.getX(), p.getY(), 10, 5);
        }

        g.drawImage(currentSprite, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
        g.drawImage(hanim.getImage(), hb.getCenterX() - 48, hb.getCenterY() - 48, this);
        g.drawImage(hanim.getImage(), hb2.getCenterX() - 48, hb2.getCenterY() - 48, this);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("Move up");
                break;
            case KeyEvent.VK_DOWN:
                currentSprite = characterDown;
                if (!robot.isJumped()) {
                    robot.setDucked(true);
                    robot.setSpeedX(0);
                }
                break;
            case KeyEvent.VK_LEFT:
                robot.moveLeft();
                robot.setMovingLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                robot.moveRight();
                robot.setMovingRight(true);
                break;
            case KeyEvent.VK_SPACE:
                robot.jump();
                break;
            case KeyEvent.VK_CONTROL:
                if (!robot.isDucked() && !robot.isJumped()) {
                    robot.shoot();
                }
                break;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                System.out.println("Stop moving up");
                break;
            case KeyEvent.VK_DOWN:
                currentSprite = anim.getImage();
                robot.setDucked(false);
                break;
            case KeyEvent.VK_LEFT:
                robot.stopLeft();
            case KeyEvent.VK_RIGHT:
                robot.stopRight();
                break;
            case KeyEvent.VK_SPACE:
                break;
        }
    }

    public static Background getBg1() {
        return bg1;
    }

    public static Background getBg2() {
        return bg2;
    }

}
