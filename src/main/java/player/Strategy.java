package player;

import player.actual.TeamImpl;
import player.computer.CompletelyRandomPlayer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum Strategy {
  RANDOM(new CompletelyRandomPlayer(), true),
  TEAM_STRATEGY(new TeamImpl(), true);

  private final Player player;
  private final boolean enabled;

  Strategy(Player player, boolean enabled) {
    this.player = player;
    this.enabled = enabled;
  }

  public static List<Strategy> enabledStrategies() {
    return Arrays.stream(Strategy.values())
        .filter(Strategy::isEnabled)
        .collect(Collectors.toList());
  }

  public Player getPlayer() {
    return player;
  }

  public boolean isEnabled() {
    return enabled;
  }
}
