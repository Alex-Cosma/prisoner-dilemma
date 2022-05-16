package player;

import org.junit.jupiter.api.Test;
import player.actual.TeamImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class PlayerTest {

  @Test
  void getLastRound() {
    Player p = new TeamImpl();
    assertNull(p.getLastOpponentChoice());

    final Choice c1 = Choice.random();
    p.addOpponentChoice(c1);
    assertEquals(c1, p.getLastOpponentChoice());

    final Choice c2 = Choice.random();
    p.addOpponentChoice(c2);
    assertEquals(c2, p.getLastOpponentChoice());
  }

  @Test
  void getLastNRounds() {
    Player p = new TeamImpl();
    assertEquals(0, p.getLastNOpponentChoices(2).size());

    final int nrRoundsAdded = 5;
    for (int i = 0; i < nrRoundsAdded; i++) {
      p.addOpponentChoice(Choice.random());
    }

    assertEquals(3, p.getLastNOpponentChoices(3).size());
    assertEquals(nrRoundsAdded, p.getLastNOpponentChoices(nrRoundsAdded).size());
    assertEquals(nrRoundsAdded, p.getLastNOpponentChoices(nrRoundsAdded + 100).size());
  }
}
