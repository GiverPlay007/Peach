package net.focaenterprises.peach;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Input implements KeyListener {

  private final boolean[] pressed = new boolean[255];

  public boolean isKeyDown(int keyCode) {
    return pressed[keyCode];
  }

  @Override
  public void keyTyped(KeyEvent e) {
  }

  @Override
  public void keyPressed(KeyEvent e) {
    pressed[e.getKeyCode()] = true;
  }

  @Override
  public void keyReleased(KeyEvent e) {
    pressed[e.getKeyCode()] = false;
  }
}
