package com.olebas.kiloboltgame;

import java.applet.Applet;
import java.awt.*;

public class StartingClass extends Applet implements Runnable {

    @Override
    public void init() {
        setSize(800, 400);
        setBackground(Color.BLACK);
        setFocusable(true);
        Frame frame = (Frame) this.getParent().getParent();
        frame.setTitle("Q-Bot Alpha");
    }

    @Override
    public void start() {
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
}
