package net.focaenterprises.peach.net;

public enum Protocol {
  PLAYER_INITIAL("player_initial"),
  PLAYER_INITIAL_LIST("player_initial_list"),
  PLAYER_CONNECTED("player_connected"),
  PLAYER_DISCONNECTED("player_disconnected"),
  PLAYER_MOVE("player_move"),
  ;

  public final String eventName;

  Protocol(String eventName) {
    this.eventName = eventName;
  }
}
