package net.focaenterprises.peach.game;

import net.focaenterprises.peach.entity.Player;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

public class Peach {

  public static final int WIDTH = 320;
  public static final int HEIGHT = 250;

  private final BufferStrategy bufferStrategy;
  private final Loop loop = new Loop(this);
  private final Input input;

  private Player player;

  protected boolean isRunning;
  protected int fps;

  public Peach() {
    Canvas canvas = new Canvas();
    canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));

    JFrame frame = new JFrame("Peach Game");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setResizable(false);
    frame.add(canvas);
    frame.pack();
    frame.setLocationRelativeTo(null);

    canvas.createBufferStrategy(3);
    bufferStrategy = canvas.getBufferStrategy();

    input = new Input();
    canvas.addKeyListener(input);

    frame.setVisible(true);
  }

  public void update() {
    player.update();
  }

  public void render() {
    Graphics graphics = bufferStrategy.getDrawGraphics();
    graphics.setColor(Color.GRAY);
    graphics.fillRect(0, 0, WIDTH, HEIGHT);

    player.render(graphics);

    graphics.dispose();
    bufferStrategy.show();
  }

  public synchronized void start() {
    loadAssets();
    loop.start();
  }

  private void loadAssets() {
    player = new Player(this, 0, 0);
  }

  public Input getInput() {
    return input;
  }
}