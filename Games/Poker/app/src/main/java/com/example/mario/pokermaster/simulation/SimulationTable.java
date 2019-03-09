package com.example.mario.pokermaster.simulation;

import com.example.mario.pokermaster.actions.Action;
import com.example.mario.pokermaster.actions.BetAction;
import com.example.mario.pokermaster.actions.RaiseAction;
import com.example.mario.pokermaster.ai.AiPlayer;
import com.example.mario.pokermaster.util.Card;
import com.example.mario.pokermaster.util.Deck;
import com.example.mario.pokermaster.util.GameState;
import com.example.mario.pokermaster.util.Hand;
import com.example.mario.pokermaster.util.HandValue;
import com.example.mario.pokermaster.util.Player;
import com.example.mario.pokermaster.util.Pot;
import com.example.mario.pokermaster.util.TableType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Borja-Trabajo on 21/01/2018.
 */

public class SimulationTable {
    /**
     * The maximum number of rounds to play in one game
     */
    private static final int MAX_ROUNDS = 20;

    /**
     * In fixed-limit games, the maximum number of raises per betting round.
     */
    private static final int MAX_RAISES = 3;

    /**
     * Whether players will always call the showdown, or fold when no chance.
     */
    private static final boolean ALWAYS_CALL_SHOWDOWN = false;

    /**
     * Table type (poker variant).
     */
    private final TableType tableType;

    /**
     * The size of the big blind.
     */
    private final int bigBlind;

    /**
     * The players at the table.
     */
    private final List<Player> players;

    /**
     * The active players in the current hand.
     */
    private final List<Player> activePlayers;

    /**
     * The deck of cards.
     */
    private final Deck deck;

    /**
     * The community cards on the board.
     */
    private final List<Card> board;

    /**
     * The current dealer position.
     */
    private int dealerPosition;

    /**
     * The current dealer.
     */
    private Player dealer;

    /**
     * The position of the acting player.
     */
    private int actorPosition;

    /**
     * The acting player.
     */
    private Player actor;

    /**
     * The minimum bet in the current hand.
     */
    private int minBet;

    /**
     * The current bet in the current hand.
     */
    private int bet;

    /**
     * All pots in the current hand (main pot and any side pots).
     */
    private final List<Pot> pots;

    /**
     * The player who bet or raised last (aggressor).
     */
    private Player lastBettor;

    /**
     * Number of raises in the current betting round.
     */
    private int raises;

    private GameState gameState;

    /**
     * Constructor.
     *
     * @param bigBlind The size of the big blind.
     */
    public SimulationTable(TableType type, int bigBlind) {
        this.tableType = type;
        this.bigBlind = bigBlind;
        players = new ArrayList<>();
        activePlayers = new ArrayList<Player>();
        deck = new Deck();
        board = new ArrayList<Card>();
        pots = new ArrayList<Pot>();
    }

    /**
     * Adds a player.
     *
     * @param player The player.
     */
    public void addPlayer(Player player) {
        players.add(player);
    }

    /**
     * Main game loop.
     */
    public void run() {
        int rounds_played = 0;

        for (Player player : players) {
            System.out.println(player.getName() + " joined the table.");
        }

        dealerPosition = -1;
        actorPosition = -1;

        int noOfActivePlayers = 0;
        for (Player player : players) {
            if (player.getCash() >= bigBlind) {
                noOfActivePlayers++;
            }
        }
        if (noOfActivePlayers > 1) {
            playHand();
            rounds_played++;
        }


        // Game Over
        board.clear();
        pots.clear();
        bet = 0;
        for (Player player : players) {
            player.resetHand();
        }
        System.out.println("Game OVER");
    }

    /**
     * Plays a single hand.
     */
    private void playHand() {
        resetHand();

        // Small blind.
        if (activePlayers.size() > 2) {
            rotateActor();
        }
        postSmallBlind();

        // Big blind.
        rotateActor();
        postBigBlind();

        // Pre-Flop.
        gameState = GameState.preFlop;
        dealHoleCards();
        doBettingRound();

        // Flop.
        if (activePlayers.size() > 1) {
            gameState = GameState.Flop;
            bet = 0;
            dealCommunityCards("Flop", 3);
            minBet = bigBlind;
            doBettingRound();

            // Turn.
            if (activePlayers.size() > 1) {
                gameState = GameState.Turn;
                bet = 0;
                dealCommunityCards("Turn", 1);
                if (tableType == TableType.FIXED_LIMIT) {
                    minBet = 2 * bigBlind;
                } else {
                    minBet = bigBlind;
                }
                doBettingRound();

                // River.
                if (activePlayers.size() > 1) {
                    gameState = GameState.River;
                    bet = 0;
                    dealCommunityCards("River", 1);
                    if (tableType == TableType.FIXED_LIMIT) {
                        minBet = 2 * bigBlind;
                    } else {
                        minBet = bigBlind;
                    }
                    doBettingRound();

                    // Showdown.
                    if (activePlayers.size() > 1) {
                        bet = 0;
                        minBet = bigBlind;
                        doShowdown();
                    }
                }
            }
        }
    }

    /**
     * Resets the game for a new hand.
     */
    private void resetHand() {
        // Clear the board.
        board.clear();
        pots.clear();

        // Determine the active players.
        activePlayers.clear();
        for (Player player : players) {
            player.resetHand();
            // Player must be able to afford at least the big blind.
            if (player.getCash() >= bigBlind) {
                activePlayers.add(player);
            }
        }

        // Rotate the dealer button.
        dealerPosition = (dealerPosition + 1) % activePlayers.size();
        dealer = activePlayers.get(dealerPosition);

        // Shuffle the deck.
        deck.shuffle();

        // Determine the first player to act.
        actorPosition = dealerPosition;
        actor = activePlayers.get(actorPosition);

        // Set the initial bet to the big blind.
        minBet = bigBlind;
        bet = minBet;

        System.out.printf("\nNew hand, %s is the dealer.\n\n", dealer);
    }

    /**
     * Rotates the position of the player in turn (the actor).
     */
    private void rotateActor() {
        actorPosition = (actorPosition + 1) % activePlayers.size();
        actor = activePlayers.get(actorPosition);
    }

    /**
     * Posts the small blind.
     */
    private void postSmallBlind() {
        final int smallBlind = bigBlind / 2;
        actor.postSmallBlind(smallBlind);
        contributePot(smallBlind);
        System.out.println(actor.getName() + " posts the small blind.");
    }

    /**
     * Posts the big blind.
     */
    private void postBigBlind() {
        actor.postBigBlind(bigBlind);
        contributePot(bigBlind);
        System.out.println(actor.getName() + " posts the big blind.");

    }

    /**
     * Deals the Hole Cards.
     */
    private void dealHoleCards() {
        for (Player player : activePlayers) {
            player.setCards(deck.deal(2));
        }
        System.out.printf("\n%s deals the hole cards.", dealer);
    }

    /**
     * Deals a number of community cards.
     *
     * @param phaseName The name of the phase.
     * @param noOfCards The number of cards to deal.
     */
    private void dealCommunityCards(String phaseName, int noOfCards) {
        for (int i = 0; i < noOfCards; i++) {
            board.add(deck.deal());
        }
        System.out.printf("\n%s deals the %s.\n", dealer, phaseName);
        System.out.println(board);
    }

    /**
     * Performs a betting round.
     */
    private void doBettingRound() {
        // Determine the number of active players.
        int playersToAct = activePlayers.size();
        // Determine the initial player and bet size.
        if (board.size() == 0) {
            // Pre-Flop; player left of big blind starts, bet is the big blind.
            bet = bigBlind;
        } else {
            // Otherwise, player left of dealer starts, no initial bet.
            actorPosition = dealerPosition;
            bet = 0;
        }

		/*
         * if (playersToAct == 2) { //removed, fix by IW // Heads Up mode; player who is
		 * not the dealer starts. actorPosition = dealerPosition; }
		 */

        lastBettor = null;
        raises = 0;

        while (playersToAct > 0) {
            rotateActor();
            Action action = null;
            if (actor.isAllIn()) {
                // Player is all-in, so must check.
                action = Action.CHECK;
                playersToAct--;
            } else {
                // Otherwise allow client to act.
                Set<Action> allowedActions = getAllowedActions(actor);
                ((AiPlayer) actor).setGameState(gameState);
                action = actor.act(minBet, bet, allowedActions, new Hand(board));
                System.out.println("Selected action = " + action);
                // Verify chosen action to guard against broken clients (accidental or on
                // purpose).
                if (!allowedActions.contains(action)) {
                    if (!(action instanceof BetAction && allowedActions.contains(Action.BET))
                            && !(action instanceof RaiseAction && allowedActions.contains(Action.RAISE))) {
                        throw new IllegalStateException(
                                String.format("Player '%s' acted with illegal %s action", actor, action));
                    }
                }
                playersToAct--;
                if (action == Action.CHECK) {
                    // Do nothing.
                } else if (action == Action.CALL) {
                    int betIncrement = bet - actor.getBet();
                    if (betIncrement > actor.getCash()) {
                        betIncrement = actor.getCash();
                    }
                    actor.payCash(betIncrement);
                    actor.setBet(actor.getBet() + betIncrement);
                    contributePot(betIncrement);
                } else if (action instanceof BetAction) {
                    int amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                    if (amount < minBet && amount < actor.getCash()) {
                        throw new IllegalStateException("Illegal client action: bet less than minimum bet!");
                    }
                    if (amount > actor.getCash() && actor.getCash() >= minBet) {
                        throw new IllegalStateException("Illegal client action: bet more cash than you own!");
                    }
                    bet = amount;
                    minBet = amount;
                    int betIncrement = bet - actor.getBet();
                    if (betIncrement > actor.getCash()) {
                        betIncrement = actor.getCash();
                    }
                    actor.setBet(bet);
                    actor.payCash(betIncrement);
                    contributePot(betIncrement);
                    lastBettor = actor;
                    playersToAct = (tableType == TableType.FIXED_LIMIT) ? activePlayers.size()
                            : (activePlayers.size() - 1);
                } else if (action instanceof RaiseAction) {
                    int amount = (tableType == TableType.FIXED_LIMIT) ? minBet : action.getAmount();
                    if (amount < minBet && amount < actor.getCash()) {
                        throw new IllegalStateException("Illegal client action: raise less than minimum bet!");
                    }
                    if (amount > actor.getCash() && actor.getCash() >= minBet) {
                        throw new IllegalStateException("Illegal client action: raise more cash than you own!");
                    }
                    bet += amount;
                    minBet = amount;
                    int betIncrement = bet - actor.getBet();
                    if (betIncrement > actor.getCash()) {
                        betIncrement = actor.getCash();
                    }
                    actor.setBet(bet);
                    actor.payCash(betIncrement);
                    contributePot(betIncrement);
                    lastBettor = actor;
                    raises++;
                    if (tableType == TableType.FIXED_LIMIT && (raises < MAX_RAISES || activePlayers.size() == 2)) {
                        // All players get another turn.
                        playersToAct = activePlayers.size();
                    } else {
                        // Max. number of raises reached; other players get one more turn.
                        playersToAct = activePlayers.size() - 1;
                    }
                } else if (action == Action.FOLD) {
                    ((AiPlayer) actor).Refuerzo(-10);

                    ((AiPlayer) actor).EstadoAccionAnterior = null;
                    ((AiPlayer) actor).Refuerzo_turn(-10);

                    ((AiPlayer) actor).EstadoAccionAnterior_turn = null;
                    ((AiPlayer) actor).Refuerzo_river(-10);

                    ((AiPlayer) actor).EstadoAccionAnterior_river = null;
                    actor.setCards(null);
                    activePlayers.remove(actor);
                    actorPosition--;
                    if (activePlayers.size() == 1) {
                        // Only one player left, so he wins the entire pot.

                        Player winner = activePlayers.get(0);
                        int amount = getTotalPot();
                        winner.win(amount);
                        System.out.printf("\n%s wins $ %d.", winner, amount);
                        playersToAct = 0;
                        ((AiPlayer) winner).Refuerzo(10);

                        ((AiPlayer) winner).EstadoAccionAnterior = null;
                        ((AiPlayer) winner).Refuerzo_turn(10);

                        ((AiPlayer) winner).EstadoAccionAnterior_turn = null;
                        ((AiPlayer) winner).Refuerzo_river(10);

                        ((AiPlayer) winner).EstadoAccionAnterior_river = null;

                    }
                } else {
                    // Programming error, should never happen.
                    throw new IllegalStateException("Invalid action: " + action);
                }
            }
            actor.setAction(action);
        }

        // Reset player's bets.
        for (Player player : activePlayers) {
            player.resetBet();
        }
    }

    /**
     * Returns the allowed actions of a specific player.
     *
     * @param player The player.
     * @return The allowed actions.
     */
    private Set<Action> getAllowedActions(Player player) {
        Set<Action> actions = new HashSet<Action>();
        if (player.isAllIn()) {
            actions.add(Action.CHECK);
        } else {
            int actorBet = actor.getBet();
            if (bet == 0) {
                actions.add(Action.CHECK);
                if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                    actions.add(Action.BET);
                }
            } else {
                if (actorBet < bet) {
                    actions.add(Action.CALL);
                    if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                        actions.add(Action.RAISE);
                    }
                } else {
                    actions.add(Action.CHECK);
                    if (tableType == TableType.NO_LIMIT || raises < MAX_RAISES || activePlayers.size() == 2) {
                        actions.add(Action.RAISE);
                    }
                }
            }
            actions.add(Action.FOLD);
        }
        return actions;
    }

    /**
     * Contributes to the pot.
     *
     * @param amount The amount to contribute.
     */
    private void contributePot(int amount) {
        for (Pot pot : pots) {
            if (!pot.hasContributer(actor)) {
                int potBet = pot.getBet();
                if (amount >= potBet) {
                    // Regular call, bet or raise.
                    pot.addContributer(actor);
                    amount -= pot.getBet();
                } else {
                    // Partial call (all-in); redistribute pots.
                    pots.add(pot.split(actor, amount));
                    amount = 0;
                }
            }
            if (amount <= 0) {
                break;
            }
        }
        if (amount > 0) {
            Pot pot = new Pot(amount);
            pot.addContributer(actor);
            pots.add(pot);
        }
    }

    /**
     * Performs the showdown.
     */
    private void doShowdown() {
        // System.out.println("\n[DEBUG] Pots:");
        // for (Pot pot : pots) {
        // System.out.format(" %s\n", pot);
        // }
        // System.out.format("[DEBUG] Total: %d\n", getTotalPot());

        // Determine show order; start with all-in players...
        List<Player> showingPlayers = new ArrayList<Player>();
        for (Pot pot : pots) {
            for (Player contributor : pot.getContributors()) {
                if (!showingPlayers.contains(contributor) && contributor.isAllIn()) {
                    showingPlayers.add(contributor);
                }
            }
        }
        // ...then last player to bet or raise (aggressor)...
        if (lastBettor != null) {
            if (!showingPlayers.contains(lastBettor)) {
                showingPlayers.add(lastBettor);
            }
        }
        // ...and finally the remaining players, starting left of the button.
        int pos = (dealerPosition + 1) % activePlayers.size();
        while (showingPlayers.size() < activePlayers.size()) {
            Player player = activePlayers.get(pos);
            if (!showingPlayers.contains(player)) {
                showingPlayers.add(player);
            }
            pos = (pos + 1) % activePlayers.size();
        }

        // Players automatically show or fold in order.
        boolean firstToShow = true;
        int bestHandValue = -1;
        for (Player playerToShow : showingPlayers) {
            Hand hand = new Hand(board);
            hand.addCards(playerToShow.getCards());
            HandValue handValue = new HandValue(hand);
            boolean doShow = ALWAYS_CALL_SHOWDOWN;
            if (!doShow) {
                if (playerToShow.isAllIn()) {
                    // All-in players must always show.
                    doShow = true;
                    firstToShow = false;
                } else if (firstToShow) {
                    // First player must always show.
                    doShow = true;
                    bestHandValue = handValue.getValue();
                    firstToShow = false;
                } else {
                    // Remaining players only show when having a chance to win.
                    if (handValue.getValue() >= bestHandValue) {
                        doShow = true;
                        bestHandValue = handValue.getValue();
                    }
                }
            }
            if (doShow) {
                // Show hand.
                System.out.printf("\n%s has %s.", playerToShow, handValue.getDescription());
            } else {
                // Fold.
                playerToShow.setCards(null);
                activePlayers.remove(playerToShow);
                System.out.printf("\n%s folds.", playerToShow);
            }
        }

        // Sort players by hand value (highest to lowest).
        Map<HandValue, List<Player>> rankedPlayers = new TreeMap<HandValue, List<Player>>();
        for (Player player : activePlayers) {
            // Create a hand with the community cards and the player's hole cards.
            Hand hand = new Hand(board);
            hand.addCards(player.getCards());
            // Store the player together with other players with the same hand value.
            HandValue handValue = new HandValue(hand);
            // System.out.format("[DEBUG] %s: %s\n", player, handValue);
            List<Player> playerList = rankedPlayers.get(handValue);
            if (playerList == null) {
                playerList = new ArrayList<Player>();
            }
            playerList.add(player);
            rankedPlayers.put(handValue, playerList);
        }

        // Per rank (single or multiple winners), calculate pot distribution.
        int totalPot = getTotalPot();
        Map<Player, Integer> potDivision = new HashMap<Player, Integer>();
        for (HandValue handValue : rankedPlayers.keySet()) {
            List<Player> winners = rankedPlayers.get(handValue);
            for (Pot pot : pots) {
                // Determine how many winners share this pot.
                int noOfWinnersInPot = 0;
                for (Player winner : winners) {
                    if (pot.hasContributer(winner)) {
                        noOfWinnersInPot++;
                    }
                }
                if (noOfWinnersInPot > 0) {
                    // Divide pot over winners.
                    int potShare = pot.getValue() / noOfWinnersInPot;
                    for (Player winner : winners) {
                        if (pot.hasContributer(winner)) {
                            Integer oldShare = potDivision.get(winner);
                            if (oldShare != null) {
                                potDivision.put(winner, oldShare + potShare);
                            } else {
                                potDivision.put(winner, potShare);
                            }

                        }
                    }
                    // Determine if we have any odd chips left in the pot.
                    int oddChips = pot.getValue() % noOfWinnersInPot;
                    if (oddChips > 0) {
                        // Divide odd chips over winners, starting left of the dealer.
                        pos = dealerPosition;
                        while (oddChips > 0) {
                            pos = (pos + 1) % activePlayers.size();
                            Player winner = activePlayers.get(pos);
                            Integer oldShare = potDivision.get(winner);
                            if (oldShare != null) {
                                potDivision.put(winner, oldShare + 1);
                                // System.out.format("[DEBUG] %s receives an odd chip from the pot.\n", winner);
                                oddChips--;
                            }
                        }

                    }
                    pot.clear();
                }
            }
        }

        // Divide winnings.
        StringBuilder winnerText = new StringBuilder();
        int totalWon = 0;
        for (Player winner : potDivision.keySet()) {
            int potShare = potDivision.get(winner);
            winner.win(potShare);
            totalWon += potShare;
            if (winnerText.length() > 0) {
                winnerText.append(", ");
            }
            winnerText.append(String.format("%s wins $ %d", winner, potShare));
            if (winner instanceof AiPlayer) {
                ((AiPlayer) winner).Refuerzo(10);

                ((AiPlayer) winner).EstadoAccionAnterior = null;
                ((AiPlayer) winner).Refuerzo_turn(10);

                ((AiPlayer) winner).EstadoAccionAnterior_turn = null;
                ((AiPlayer) winner).Refuerzo_river(10);

                ((AiPlayer) winner).EstadoAccionAnterior_river = null;
            }
        }

        for (Player player : players) {
            if (!potDivision.containsKey(player)) {
                if (player instanceof AiPlayer) {
                    ((AiPlayer) player).Refuerzo(-10);

                    ((AiPlayer) player).EstadoAccionAnterior = null;
                    ((AiPlayer) player).Refuerzo_turn(-10);

                    ((AiPlayer) player).EstadoAccionAnterior_turn = null;
                    ((AiPlayer) player).Refuerzo_river(-10);

                    ((AiPlayer) player).EstadoAccionAnterior_river = null;
                }
            }
        }

        winnerText.append('.');
        System.out.println(winnerText.toString());

        // Sanity check.
        if (totalWon != totalPot) {
            throw new IllegalStateException("Incorrect pot division!");
        }
    }

    /**
     * Returns the total pot size.
     *
     * @return The total pot size.
     */
    private int getTotalPot() {
        int totalPot = 0;
        for (Pot pot : pots) {
            totalPot += pot.getValue();
        }
        return totalPot;
    }
}
