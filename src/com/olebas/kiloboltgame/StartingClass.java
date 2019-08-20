package com.olebas.kiloboltgame;

import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.net.URL;

public class StartingClass extends Applet implements Runnable, KeyListener {

    private Robot robot;
    private Image image;
    private Image character;
    private Graphics second;
    private URL base;

    @Override
    public void init() {
        setSize(800, 400);
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Q-Bot Alpha");
        try {
            base = getDocumentBase();
        } catch (Exception e) {
            e.printStackTrace();
        }

        character = getImage(base, "data/character.png");
    }

    @Override
    public void start() {
        robot = new Robot();
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
            repaint();
//            System.out.println("repaint");
            try {
                Thread.sleep(17);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
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
        g.drawImage(character, robot.getCenterX() - 61, robot.getCenterY() - 63, this);
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
                System.out.println("Move down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Move left");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Move right");
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Jump");
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
                System.out.println("Stop moving down");
                break;
            case KeyEvent.VK_LEFT:
                System.out.println("Stop moving left");
                break;
            case KeyEvent.VK_RIGHT:
                System.out.println("Stop moving right");
                break;
            case KeyEvent.VK_SPACE:
                System.out.println("Stop jumping");
                break;
        }
    }
}
