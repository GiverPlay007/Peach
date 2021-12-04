package net.focaenterprises.peach.entity;

import net.focaenterprises.peach.game.Peach;

import java.awt.Color;
import java.awt.Graphics;

import static java.awt.event.KeyEvent.*;

public class Player {

  private final Peach game;

  private int x;
  private int y;

  public Player(Peach game, int x, int y) {
    this.game = game;
    this.x = x;
    this.y = y;
  }

  public void update() {
    int mX = 0;
    int mY = 0;

    if(game.getInput().isKeyDown(VK_A)) {
      --mX;
    }

    if(game.getInput().isKeyDown(VK_D)) {
      ++mX;
    }

    if(game.getInput().isKeyDown(VK_W)) {
      --mY;
    }

    if(game.getInput().isKeyDown(VK_S)) {
      ++mY;
    }

    x += mX * 2;
    y += mY * 2;
  }

  public void render(Graphics graphics) {
    graphics.setColor(new Color(0x36D786));
    graphics.fillRect(x, y, 32, 32);
  }

  public void moveX(int move) {
    x += move;
  }

  public void moveY(int move) {
    y += move;
  }

  public int getX() {
    return x;
  }

  public int getY() {
    return y;
  }

  public void setX(int x) {
    this.x = x;
  }

  public void setY(int y) {
    this.y = y;
  }
}
