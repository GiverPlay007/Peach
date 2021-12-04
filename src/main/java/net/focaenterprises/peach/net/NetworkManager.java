package net.focaenterprises.peach.net;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.focaenterprises.peach.game.Peach;
import net.focaenterprises.peach.player.PlayerData;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import static net.focaenterprises.peach.net.Protocol.PLAYER_CONNECTED;
import static net.focaenterprises.peach.net.Protocol.PLAYER_DISCONNECTED;
import static net.focaenterprises.peach.net.Protocol.PLAYER_INITIAL;
import static net.focaenterprises.peach.net.Protocol.PLAYER_INITIAL_LIST;
import static net.focaenterprises.peach.net.Protocol.PLAYER_MOVE;

public class NetworkManager {

  private final Peach game;
  private final Socket socket;

  public NetworkManager(Peach game, String host) {
    this.game = game;

    URI uri = URI.create(host);
    IO.Options options = IO.Options.builder().build();

    this.socket = IO.socket(uri, options);
  }

  public void connect() {
    socket.connect();
  }

  public void registerCallbacks() {
    socket.on(PLAYER_INITIAL.eventName, this::authPlayer);
    socket.on(PLAYER_INITIAL_LIST.eventName, this::playerList);
    socket.on(PLAYER_CONNECTED.eventName, this::connectPlayer);
    socket.on(PLAYER_DISCONNECTED.eventName, this::disconnectPlayer);
    socket.on(PLAYER_MOVE.eventName, this::movePlayer);
  }

  private void authPlayer(Object... data) {
    game.onPlayerAuth(parsePlayerData(data));
  }

  private void playerList(Object... data) {
    JSONArray json = (JSONArray) data[0];

    List<PlayerData> players = new ArrayList<>();

    try {
      for (int index = 0; index < json.length(); index++) {
        players.add(parsePlayerData(json.getJSONObject(index)));
      }
    } catch (JSONException e) {
      e.printStackTrace();
    }

    game.onPlayerListReceived(players);
  }

  private void connectPlayer(Object... data) {
    game.onPlayerConnected(parsePlayerData(data));
  }

  private void disconnectPlayer(Object... data) {
    game.onPlayerDisconnected(parsePlayerData(data));
  }

  private void movePlayer(Object... data) {
    game.onPlayerMove(parsePlayerData(data));
  }

  private PlayerData parsePlayerData(Object... data) {
    return parsePlayerData((JSONObject) data[0]);
  }

  private PlayerData parsePlayerData(JSONObject json) {
    String color = null;
    String id;

    int x;
    int y;

    try {
      JSONObject position = json.isNull("position") ? json : json.getJSONObject("position");

      id = json.getString("id");

      x = json.isNull("x") ? 0 : position.getInt("x");
      y = json.isNull("y") ? 0 : position.getInt("y");

      if(!json.isNull("color")) {
        color = json.getString("color");
      }
    } catch (JSONException e) {
      e.printStackTrace();
      return new PlayerData(null, 0, 0);
    }

    return new PlayerData(id, x, y, color);
  }

  public void sendPlayerMove(int x, int y) {
    JSONObject json = new JSONObject();

    try {
      json.put("x", x);
      json.put("y", y);
    } catch (JSONException e) {
      e.printStackTrace();
    }

    socket.emit(PLAYER_MOVE.eventName, json);
  }
}
