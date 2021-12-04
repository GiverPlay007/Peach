package net.focaenterprises.peach.scenes;

import net.focaenterprises.peach.game.Peach;

import java.awt.Graphics;

public abstract class Scene {

  protected Peach game;

  public Scene(Peach game) {
    this.game = game;
  }

  public abstract void update();

  public abstract void render(Graphics graphics);

  public Peach getGame() {
    return game;
  }
}
