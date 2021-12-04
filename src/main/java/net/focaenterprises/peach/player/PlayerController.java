package net.focaenterprises.peach.player;

import net.focaenterprises.peach.game.Input;
import net.focaenterprises.peach.game.Peach;

import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_W;

public class PlayerController {

  private final Peach game;
  private final Input input;
  private final Player player;

  public PlayerController(Peach game) {
    this.game = game;
    this.player = game.getPlayer();
    this.input = game.getInput();
  }

  public void update() {
    int mX = 0;
    int mY = 0;

    if(input.isKeyDown(VK_A)) {
      --mX;
    }

    if(input.isKeyDown(VK_D)) {
      ++mX;
    }

    if(input.isKeyDown(VK_W)) {
      --mY;
    }

    if(input.isKeyDown(VK_S)) {
      ++mY;
    }

    if(mX != 0 || mY != 0) {
      player.moveX(mX * 2);
      player.moveY(mY * 2);

      game.getNetworkManager().sendPlayerMove(player.getX(), player.getY());
    }
  }
}
