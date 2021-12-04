package net.focaenterprises.peach.game;

public class Loop implements Runnable {
  private Thread loopThread;
  private final Peach game;

  public Loop(Peach game) {
    this.game = game;
  }

  public void start() {
    loopThread = new Thread(this, "Loop Thread");
    game.isRunning = true;
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

    while(game.isRunning) {
      now = System.nanoTime();
      unprocessed += (now - lastTime) / nsPerTick;
      lastTime = now;

      while(unprocessed >= 1) {
        game.update();
        game.render();
        --unprocessed;
        ++currentFps;
      }

      if(System.currentTimeMillis() - timer >= 1000) {
        game.fps = currentFps;
        currentFps = 0;
        timer += 1000;
        System.out.println("FPS: " + game.fps);
      }

      try {
        Thread.sleep(2);
      } catch (InterruptedException ignore) { }
    }
  }
}
