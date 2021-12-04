package net.focaenterprises.peach;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.image.BufferStrategy;

public class Peach implements Runnable {

  public static final int WIDTH = 320;
  public static final int HEIGHT = 250;

  private final BufferStrategy bufferStrategy;

  private Thread loopThread;

  private boolean isRunning;
  private int fps;

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

    frame.setVisible(true);
  }

  public void update() {

  }

  public void render() {

  }

  public synchronized void start() {
    loopThread = new Thread(this, "Loop Thread");
    isRunning = true;
    loopThread.start();
  }

  @Override
  public void run() {
    long lastTime = System.nanoTime();
    long timer = System.currentTimeMillis();
    long now;

    double nsPerTick = 1_000_000_000 / 60.0D;
    double unprocessed = 0;

    int currentFps = 0;

    while(isRunning) {
      now = System.nanoTime();
      unprocessed += (now - lastTime) / nsPerTick;
      lastTime = now;

      while(unprocessed >= 1) {
        update();
        render();
        --unprocessed;
        ++currentFps;
      }

      if(System.currentTimeMillis() - timer >= 1000) {
        fps = currentFps;
        currentFps = 0;
        timer += 1000;
        System.out.println("FPS: " + fps);
      }

      try {
        Thread.sleep(2);
      } catch (InterruptedException ignore) { }
    }
  }
}
