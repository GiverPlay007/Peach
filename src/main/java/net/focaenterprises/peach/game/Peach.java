package net.focaenterprises.peach.game;

import net.focaenterprises.peach.net.NetworkManager;
import net.focaenterprises.peach.player.Player;
import net.focaenterprises.peach.player.PlayerController;
import net.focaenterprises.peach.player.PlayerData;
import net.focaenterprises.peach.scenes.GameScene;
import net.focaenterprises.peach.scenes.Scene;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Peach {

  public static final int WIDTH = 640;
  public static final int HEIGHT = 480;

  private final HashMap<String, Player> playerList = new HashMap<>();
  private final BufferStrategy bufferStrategy;
  private final Loop loop = new Loop(this);
  private final Input input;

  private NetworkManager networkManager;
  private PlayerController controller;
  private Player player;

  private Scene gameScene;
  private Scene currentScene;

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
    currentScene.update();
  }

  public void render() {
    Graphics graphics = bufferStrategy.getDrawGraphics();
    graphics.setColor(Color.GRAY);
    graphics.fillRect(0, 0, WIDTH, HEIGHT);

    currentScene.render(graphics);

    graphics.dispose();
    bufferStrategy.show();
  }

  public synchronized void start() {
    load();
    connectToServer();
    loop.start();
  }

  private void load() {
    gameScene = new GameScene(this);
    currentScene = gameScene;
    player = new Player(0, 0, Color.RED);
    controller = new PlayerController(this);
  }

  private void connectToServer() {
    networkManager = new NetworkManager(this, "https://peach-s.herokuapp.com/");
    networkManager.registerCallbacks();
    networkManager.connect();
  }

  public void onPlayerAuth(PlayerData data) {
    player.setX(data.x);
    player.setY(data.y);
    player.setColor(data.color);
  }

  public void onPlayerListReceived(List<PlayerData> players) {
    players.forEach(this::onPlayerConnected);
  }

  public void onPlayerConnected(PlayerData data) {
    Player player = new Player(data.x, data.y, data.color);
    playerList.put(data.id, player);
  }

  public void onPlayerMove(PlayerData data) {
    Player player = playerList.get(data.id);
    player.setX(data.x);
    player.setY(data.y);
  }

  public void onPlayerDisconnected(PlayerData data) {
    playerList.remove(data.id);
  }

  public void setCurrentScene(Scene scene) {
    this.currentScene = scene;
  }

  public Input getInput() {
    return input;
  }

  public Player getPlayer() {
    return player;
  }

  public NetworkManager getNetworkManager() {
    return networkManager;
  }

  public PlayerController getPlayerController() {
    return controller;
  }

  public List<Player> getPlayers() {
    return new ArrayList<>(playerList.values());
  }
}
