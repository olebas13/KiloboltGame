package com.olebas.kiloboltgame;

public class Robot {

    private final int JUMP_SPEED = -15;
    private final int MOVE_SPEED = 5;
    private final int GROUND = 382;

    private int centerX = 100;
    private int centerY = GROUND;
    private boolean jumped = false;
    private boolean movingLeft = false;
    private boolean movingRight = false;
    private boolean ducked = false;

    private static Background bg1 = StartingClass.getBg1();
    private static Background bg2 = StartingClass.getBg2();

    private int speedX = 0;
    private int speedY = 1;

    public void update() {

        // Moves Character or Scrolls Background accordingly.
        if (speedX < 0) {
            centerX += speedX;
        }

        if (speedX <= 0) {
            bg1.setSpeedX(0);
            bg2.setSpeedX(0);
        }

        if (centerX <= 200 && speedX > 0) {
            centerX += speedX;
        }

        if (speedX > 0 && centerX > 200) {
            bg1.setSpeedX(-MOVE_SPEED);
            bg2.setSpeedX(-MOVE_SPEED);
        }

        // Update Y position
        centerY += speedY;
        if (centerY + speedY >= GROUND) {
            centerY = GROUND;
        }

        // Handles Jumping
        if (jumped) {
            speedY += 1;

            if (centerY + speedY >= GROUND) {
                centerY = GROUND;
                speedY = 0;
                jumped = false;
            }
        }

        // Prevents going beyond X coordinate of 0
        if (centerX + speedX <= 60) {
            centerX = 61;
        }
    }

    public void moveRight() {
        if (!ducked) {
            speedX = MOVE_SPEED;
        }
    }

    public void moveLeft() {
        if (!ducked) {
            speedX = -MOVE_SPEED;
        }
    }

    public void stopRight() {
        setMovingRight(false);
        stop();
    }

    public void stopLeft() {
        setMovingLeft(false);
        stop();
    }

    public void stop() {
        if (!isMovingRight() && !isMovingLeft()) {
            speedX = 0;
        }

        if (!isMovingRight() && isMovingLeft()) {
            moveLeft();
        }

        if (isMovingRight() && !isMovingLeft()) {
            moveRight();
        }
    }

    public void jump() {
        if (!jumped) {
            speedY = JUMP_SPEED;
            jumped = true;
        }
    }

    public int getCenterX() {
        return centerX;
    }

    public void setCenterX(int centerX) {
        this.centerX = centerX;
    }

    public int getCenterY() {
        return centerY;
    }

    public void setCenterY(int centerY) {
        this.centerY = centerY;
    }

    public boolean isJumped() {
        return jumped;
    }

    public void setJumped(boolean jumped) {
        this.jumped = jumped;
    }

    public int getSpeedX() {
        return speedX;
    }

    public void setSpeedX(int speedX) {
        this.speedX = speedX;
    }

    public int getSpeedY() {
        return speedY;
    }

    public void setSpeedY(int speedY) {
        this.speedY = speedY;
    }

    public boolean isMovingLeft() {
        return movingLeft;
    }

    public void setMovingLeft(boolean movingLeft) {
        this.movingLeft = movingLeft;
    }

    public boolean isMovingRight() {
        return movingRight;
    }

    public void setMovingRight(boolean movingRight) {
        this.movingRight = movingRight;
    }

    public boolean isDucked() {
        return ducked;
    }

    public void setDucked(boolean ducked) {
        this.ducked = ducked;
    }
}
