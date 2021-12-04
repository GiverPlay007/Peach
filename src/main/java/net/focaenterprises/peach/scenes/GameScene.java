package net.focaenterprises.peach.scenes;

import net.focaenterprises.peach.game.Peach;

import java.awt.Graphics;

public class GameScene extends Scene {
  public GameScene(Peach game) {
    super(game);
  }

  @Override
  public void update() {
    game.getPlayerController().update();
  }

  @Override
  public void render(Graphics graphics) {
    game.getPlayers().forEach(player -> player.render(graphics));
    game.getPlayer().render(graphics);
  }
}
