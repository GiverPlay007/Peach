package net.focaenterprises.peach.player;

import java.awt.Color;
import java.awt.Graphics;

public class Player {

  private Color color;

  private int x;
  private int y;

  public Player(int x, int y, Color color) {
    this.x = x;
    this.y = y;
    this.color = color;
  }

  public void render(Graphics graphics) {
    graphics.setColor(color);
    graphics.fillRect(x, y, 32, 32);
    graphics.setColor(Color.BLACK);
    graphics.drawRect(x, y, 32, 32);
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

  public Color getColor() {
    return color;
  }

  public void setColor(Color color) {
    this.color = color;
  }
}
