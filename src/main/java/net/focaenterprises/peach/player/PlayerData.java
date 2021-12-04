package net.focaenterprises.peach.player;

import java.awt.Color;

public class PlayerData {

  public final String id;
  public final Color color;

  public final int x;
  public final int y;

  public PlayerData(String id, int x, int y, String color) {
    this.id = id;
    this.x = x;
    this.y = y;
    this.color = color != null ? Color.decode(color) : null;
  }

  public PlayerData(String id, int x, int y) {
    this(id, x, y, null);
  }
}