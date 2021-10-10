import player.computer.AlwaysBetrayPlayer;
import player.computer.AlwaysCooperatePlayer;
import player.Choice;
import player.computer.CompletelyRandomPlayer;
import player.Player;
import player.Round;
import player.computer.ComputerPlayer;
import player.computer.StandardStrategy;
import player.computer.TitForTatPlayer;
import result.Result;
import result.RoundPayoff;
import result.RoundPayoffCalculator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Tournament {

    public static final long MAX_ROUNDS = 1000;

    private final RoundPayoffCalculator calculator;
    private final Player player;
    private final ComputerPlayer[] strategies = new ComputerPlayer[StandardStrategy.values().length];

    private final Result result = new Result();

    public Tournament(RoundPayoffCalculator calculator, Player player) {
        this.calculator = calculator;
        this.player = player;
        initStrategies();
    }

    public Result play() {

        ComputerPlayer strategy = getRandomStrategy();
        Collection<RoundPayoff> payoffs = play(strategy);
        Result strategyResult = new Result();
        strategyResult.addPayoffs(payoffs);

        System.out.println("For strategy: " + strategy.getStrategy());
        strategyResult.printResults();

        this.result.addPayoffs(payoffs);

        return result;
    }

    private Collection<RoundPayoff> play(Player computer) {
        List<RoundPayoff> payoffs = new ArrayList<>();

        for (int i = 0; i < MAX_ROUNDS; i++) {
            payoffs.add(playRound(computer));
        }
        return payoffs;
    }

    private RoundPayoff playRound(Player computer) {
        Choice p1Choice = player.play();
        Choice p2Choice = computer.play();
        player.addRound(Round.builder().p1choice(p1Choice).p2choice(p2Choice).build());
        return calculator.computePayoff(p1Choice, p2Choice);
    }

    private void initStrategies() {
        strategies[0] = new CompletelyRandomPlayer();
        strategies[1] = new AlwaysCooperatePlayer();
        strategies[2] = new AlwaysBetrayPlayer();
        strategies[3] = new TitForTatPlayer();
    }

    private ComputerPlayer getRandomStrategy() {
        final int index = new Random().nextInt(StandardStrategy.values().length);
        return strategies[index];
    }
}
