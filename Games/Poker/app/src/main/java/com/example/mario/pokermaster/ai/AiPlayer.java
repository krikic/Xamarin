package com.example.mario.pokermaster.ai;

import android.content.SharedPreferences;

import com.example.mario.pokermaster.Game;
import com.example.mario.pokermaster.actions.Action;
import com.example.mario.pokermaster.actions.BetAction;
import com.example.mario.pokermaster.actions.RaiseAction;
import com.example.mario.pokermaster.util.Card;
import com.example.mario.pokermaster.util.GameState;
import com.example.mario.pokermaster.util.Hand;
import com.example.mario.pokermaster.util.HandValue;
import com.example.mario.pokermaster.util.Player;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.Vector;

/**
 * Created by Borja on 04/12/2017.
 */

public class AiPlayer extends Player {

    // Parameters to QLearning
    double alpha = 0.6, alpha_turn = 0.6, alpha_river=0.6 ;  //Valores del Codigo
    double gamma=0.75;
    double epsilon=0.05;
    final double dMINLearnRate = 0.4;
    final double dDecFactorLR = 0.99;
    double dQmax;
    private Vector<StateAction> vEstadosAcciones = new Vector<StateAction>();
    private Vector<StateAction> vEstadosAcciones_turn = new Vector<StateAction>();
    private Vector<StateAction> vEstadosAcciones_river = new Vector<StateAction>();
    public StateAction EstadoAccionActual, EstadoAccionActual_turn, EstadoAccionActual_river;
    public StateAction EstadoAccionAnterior, EstadoAccionAnterior_turn, EstadoAccionAnterior_river;
    public int AccionAnterior, AccionAnterior_turn, AccionAnterior_river;
    public int newAction, newAction_turn, newAction_river;
    public ArrayList<Integer> newActionRound,  newActionRound_turn, newActionRound_river;

    private float random_play;
    private GameState gameState;
    private int thinking_Time;
    private int random_prob;

    /**
     * Tightness (0 = loose, 100 = tight).
     */
    private int tightness;

    /**
     * Betting aggression (0 = safe, 100 = aggressive).
     */
    private int aggression;

    public AiPlayer(String name, int cash) {
        super(name, cash);
    }

    public AiPlayer(String name, int cash, int tightness, int aggression) {
        super(name, cash);
        this.tightness = tightness;
        this.aggression = aggression;
        newActionRound = new ArrayList<>();
        newActionRound_turn = new ArrayList<>();
        newActionRound_river = new ArrayList<>();
    }

    /**
     * Requests this player to act, selecting one of the allowed actions.
     *
     * @param minBet         The minimum bet.
     * @param bet            The current bet.
     * @param allowedActions The allowed actions.
     * @return The selected action.
     */
    public Action act(int minBet, int bet, Set<Action> allowedActions, Hand board) {
        Action action = null;

        System.out.println("\nIt's " + getName() + "'s turn.");
        System.out.println("The minimum bet:\tThe current bet:");
        System.out.println(minBet + "\t\t\t" + bet);

        System.out.println(gameState.name());

        if (allowedActions.size() == 1) {
            action = Action.CHECK;
        } else {

            // PRE FLOP => CHEN
            if (gameState.name().equals(GameState.preFlop.name())) {
                action = actChen(minBet, bet, allowedActions);
            }

            // FLOP => CACTUS
            else if (gameState.name().equals(GameState.Flop.name())) {
                if((Math.random()*100) >= random_prob) {
                    action = vGetNewActionQLearning(minBet, bet, allowedActions, board);
                    System.out.println("\n\nSTATE\tCHECK/CALL\tBET/RAISE\tFOLD");
                    for (StateAction sa : vEstadosAcciones) {
                        System.out.println(sa);
                    }
                    System.out.println("\n");
                } else {
                    System.out.println("Action elegida aleatoriamente (" + random_prob + ")");
                    action = actionRandom(minBet, allowedActions);
                }
            }

            else if (gameState.name().equals(GameState.Turn.name())) {
                if((Math.random()*100) >= random_prob) {
                    action = vGetNewActionQLearning_turn(minBet, bet, allowedActions, board);
                    System.out.println("\n\nSTATE\tCHECK/CALL\tBET/RAISE\tFOLD");
                    for (StateAction sa : vEstadosAcciones_turn) {
                        System.out.println(sa);
                    }
                    System.out.println("\n");
                } else {
                    System.out.println("Action elegida aleatoriamente (" + random_prob + ")");
                    action = actionRandom(minBet, allowedActions);
                }
            }

            else if (gameState.name().equals(GameState.River.name())) {
                if((Math.random()*100) >= random_prob) {
                    action = vGetNewActionQLearning_river(minBet, bet, allowedActions, board);
                    System.out.println("\n\nSTATE\tCHECK/CALL\tBET/RAISE\tFOLD");
                    for (StateAction sa : vEstadosAcciones_river) {
                        System.out.println(sa);
                    }
                    System.out.println("\n");
                } else {
                    System.out.println("Action elegida aleatoriamente (" + random_prob + ")");
                    action = actionRandom(minBet, allowedActions);
                }
            }
        }
        return action;
    }

    public Action actionRandom(int minBet, Set<Action> allowedActions) {
        Action action = null;
        int action_temp;
        action_temp = (int) (Math.random() * (double) 3);
        if(action_temp == 0) {
            if(allowedActions.contains(Action.CHECK)) {
                action = Action.CHECK;
            }
            else {
                action = Action.CALL;
            }
        } else if(action_temp == 1) {
            if(allowedActions.contains(Action.BET)) {
                action = new BetAction(minBet);
            }else {
                if(this.getCash()<minBet*2){
                    action=Action.CALL;
                }else {
                    action = new RaiseAction(minBet);
                }
            }
        } else {
            action = Action.FOLD;
        }
        return action;
    }

    public Action vGetNewActionQLearning (int minBet, int bet, Set<Action> allowedActions, Hand board) {
        Action action = null;
        int amount = minBet;

        boolean bFound;
        int iBest=-1, iNumBest=1;
        StateAction oStateAction;
        bFound = false;							// Searching if we already have the state
        board.addCards(getCards());
        String estado_temp =  new HandValue(board).getType().toString() + ":" + new HandValue(board).getHsValue();
        System.out.println(new HandValue(board).getType() + " = " + new HandValue(board).getHsValue());
        for (int i=0; i<vEstadosAcciones.size(); i++) {
            oStateAction = vEstadosAcciones.elementAt(i);
            if(oStateAction.getEstado().equals(estado_temp)) {
                EstadoAccionActual = oStateAction;
                bFound = true;
                break;
            }
        }

        // If we didn't find it, then we add it
        if (!bFound) {
            EstadoAccionActual = new StateAction (new HandValue(board).getType(), new HandValue(board).getHsValue());
            vEstadosAcciones.add (EstadoAccionActual);
        }

        dQmax = 0;
        // Determining the action to get Qmax{a'}
        if ((EstadoAccionActual.getAction()[0] > EstadoAccionActual.getAction()[1]) && (EstadoAccionActual.getAction()[0] > EstadoAccionActual.getAction()[2])) {
            iBest = 0;
            dQmax = EstadoAccionActual.getAction()[0];
        } else if ( (EstadoAccionActual.getAction()[0] == EstadoAccionActual.getAction()[1])) {	// If there is another one equal we must select one of them randomly
            iBest = (int) (Math.random() * (double) 2);
            dQmax = EstadoAccionActual.getAction()[iBest];
        } else if(EstadoAccionActual.getAction()[1] > EstadoAccionActual.getAction()[2]){
            iBest = 1;
            dQmax = EstadoAccionActual.getAction()[1];
        } else {
            iBest = 2;
            dQmax = EstadoAccionActual.getAction()[2];
        }

        // Adjusting Q(s,a)
        if (EstadoAccionAnterior != null ) {
            EstadoAccionAnterior.getAction()[AccionAnterior] +=  alpha * (gamma * dQmax - EstadoAccionAnterior.getAction()[AccionAnterior]);
        }

        if ( (Math.random() > epsilon) ) 			// Using the e-greedy policy to select the best action or any of the rest
            newAction = iBest;
        else newAction = (int) (Math.random() * (double) 3);

        if(newAction == 0) {
            if(allowedActions.contains(Action.CHECK)) {
                action = Action.CHECK;
            }
            else if(allowedActions.contains(Action.CALL)){
                action = Action.CALL;
            }
        } else if(newAction == 1) {
            if(allowedActions.contains(Action.BET)) {
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new BetAction(amount);
                } else {
                    newAction = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            } else if(allowedActions.contains(Action.RAISE)){
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new RaiseAction(amount);
                } else {
                    newAction = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            }
        } else {
            action = Action.FOLD;
        }

        if(action == null) {
            for(Action action_t : allowedActions) {
                if(action_t.getName().equals("Check") || action_t.getName().equals("Call")) {
                    newAction = 0;
                } else if(action_t.getName().equals("Bet") || action_t.getName().equals("Raise")) {
                    newAction = 1;
                } else {
                    newAction = 2;
                }
                action = action_t;
                break;
            }
        }

        System.out.println(action);

        if(!newActionRound.contains(newAction)) {
            newActionRound.add(newAction);
        }

        EstadoAccionAnterior = EstadoAccionActual;				// Updating values for the next time
        AccionAnterior=newAction;
        alpha *= dDecFactorLR;						// Reducing the learning rate
        if (alpha < dMINLearnRate) alpha = dMINLearnRate;

        return action;

    }  // from class LearningTools		}

    public Action vGetNewActionQLearning_turn (int minBet, int bet, Set<Action> allowedActions, Hand board) {
        Action action = null;
        int amount = minBet;

        boolean bFound;
        int iBest=-1, iNumBest=1;
        StateAction oStateAction;
        bFound = false;							// Searching if we already have the state
        board.addCards(getCards());
        //System.out.println(new HandValue(board).getType() + " = " + new HandValue(board).getValue());
        String estado_temp =  new HandValue(board).getType().toString() + ":" + new HandValue(board).getHsValue();
        for (int i=0; i<vEstadosAcciones_turn.size(); i++) {
            oStateAction = vEstadosAcciones_turn.elementAt(i);
            if(oStateAction.getEstado().equals(estado_temp)) {
                EstadoAccionActual_turn = oStateAction;
                bFound = true;
                break;
            }
        }

        // If we didn't find it, then we add it
        if (!bFound) {
            EstadoAccionActual_turn = new StateAction (new HandValue(board).getType(), new HandValue(board).getHsValue());
            vEstadosAcciones_turn.add (EstadoAccionActual_turn);
        }

        dQmax = 0;
        // Determining the action to get Qmax{a'}
        if ((EstadoAccionActual_turn.getAction()[0] > EstadoAccionActual_turn.getAction()[1]) && (EstadoAccionActual_turn.getAction()[0] > EstadoAccionActual_turn.getAction()[2])) {
            iBest = 0;
            dQmax = EstadoAccionActual_turn.getAction()[0];
        } else if ( (EstadoAccionActual_turn.getAction()[0] == EstadoAccionActual_turn.getAction()[1])) {	// If there is another one equal we must select one of them randomly
            iBest = (int) (Math.random() * (double) 2);
            dQmax = EstadoAccionActual_turn.getAction()[iBest];
        } else if(EstadoAccionActual_turn.getAction()[1] > EstadoAccionActual_turn.getAction()[2]){
            iBest = 1;
            dQmax = EstadoAccionActual_turn.getAction()[1];
        } else {
            iBest = 2;
            dQmax = EstadoAccionActual_turn.getAction()[2];
        }

        // Adjusting Q(s,a)
        if (EstadoAccionAnterior_turn != null ) {
            EstadoAccionAnterior_turn.getAction()[AccionAnterior_turn] +=  alpha_turn * (gamma * dQmax - EstadoAccionAnterior_turn.getAction()[AccionAnterior_turn]);
        }

        if ( (Math.random() > epsilon) ) 			// Using the e-greedy policy to select the best action or any of the rest
            newAction_turn = iBest;
        else newAction_turn = (int) (Math.random() * (double) 3);

        if(newAction_turn == 0) {
            if(allowedActions.contains(Action.CHECK)) {
                action = Action.CHECK;
            }
            else if(allowedActions.contains(Action.CALL)){
                action = Action.CALL;
            }
        } else if(newAction_turn == 1) {
            if(allowedActions.contains(Action.BET)) {
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new BetAction(amount);
                } else {
                    newAction_turn = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            } else if(allowedActions.contains(Action.RAISE)){
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new RaiseAction(amount);
                } else {
                    newAction_turn = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            }
        } else {
            action = Action.FOLD;
        }

        if(action == null) {
            for(Action action_t : allowedActions) {
                if(action_t.getName().equals("Check") || action_t.getName().equals("Call")) {
                    newAction_turn = 0;
                } else if(action_t.getName().equals("Bet") || action_t.getName().equals("Raise")) {
                    newAction_turn = 1;
                } else {
                    newAction_turn = 2;
                }
                action = action_t;
                break;
            }
        }

        System.out.println(action);

        if(!newActionRound_turn.contains(newAction_turn)) {
            newActionRound_turn.add(newAction_turn);
        }

        EstadoAccionAnterior_turn = EstadoAccionActual_turn;				// Updating values for the next time
        AccionAnterior_turn=newAction_turn;
        alpha_turn *= dDecFactorLR;						// Reducing the learning rate
        if (alpha_turn < dMINLearnRate) alpha_turn = dMINLearnRate;

        return action;

    }

    public Action vGetNewActionQLearning_river (int minBet, int bet, Set<Action> allowedActions, Hand board) {
        Action action = null;
        int amount = minBet;

        boolean bFound;
        int iBest=-1, iNumBest=1;
        StateAction oStateAction;
        bFound = false;							// Searching if we already have the state
        board.addCards(getCards());
        String estado_temp =  new HandValue(board).getType().toString() + ":" + new HandValue(board).getHsValue();
        //System.out.println(new HandValue(board).getType() + " = " + new HandValue(board).getValue());
        for (int i=0; i<vEstadosAcciones_river.size(); i++) {
            oStateAction = vEstadosAcciones_river.elementAt(i);
            if(oStateAction.getEstado().equals(estado_temp)) {
                EstadoAccionActual_river = oStateAction;
                bFound = true;
                break;
            }
        }

        // If we didn't find it, then we add it
        if (!bFound) {
            EstadoAccionActual_river = new StateAction (new HandValue(board).getType(), new HandValue(board).getHsValue());
            vEstadosAcciones_river.add (EstadoAccionActual_river);
        }

        dQmax = 0;
        // Determining the action to get Qmax{a'}
        if ((EstadoAccionActual_river.getAction()[0] > EstadoAccionActual_river.getAction()[1]) && (EstadoAccionActual_river.getAction()[0] > EstadoAccionActual_river.getAction()[2])) {
            iBest = 0;
            dQmax = EstadoAccionActual_river.getAction()[0];
        } else if ( (EstadoAccionActual_river.getAction()[0] == EstadoAccionActual_river.getAction()[1])) {	// If there is another one equal we must select one of them randomly
            iBest = (int) (Math.random() * (double) 2);
            dQmax = EstadoAccionActual_river.getAction()[iBest];
        } else if(EstadoAccionActual_river.getAction()[1] > EstadoAccionActual_river.getAction()[2]){
            iBest = 1;
            dQmax = EstadoAccionActual_river.getAction()[1];
        } else {
            iBest = 2;
            dQmax = EstadoAccionActual_river.getAction()[2];
        }

        // Adjusting Q(s,a)
        if (EstadoAccionAnterior_river != null ) {
            EstadoAccionAnterior_river.getAction()[AccionAnterior_river] +=  alpha_river * (gamma * dQmax - EstadoAccionAnterior_river.getAction()[AccionAnterior_river]);
        }

        if ( (Math.random() > epsilon) ) 			// Using the e-greedy policy to select the best action or any of the rest
            newAction_river = iBest;
        else newAction_river = (int) (Math.random() * (double) 3);

        if(newAction_river == 0) {
            if(allowedActions.contains(Action.CHECK)) {
                action = Action.CHECK;
            }
            else if(allowedActions.contains(Action.CALL)){
                action = Action.CALL;
            }
        } else if(newAction_river == 1) {
            if(allowedActions.contains(Action.BET)) {
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new BetAction(amount);
                } else {
                    newAction_river = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            } else if(allowedActions.contains(Action.RAISE)){
                if(amount>this.getCash()) amount= this.getCash();

                if (bet < amount) {
                    action = new RaiseAction(amount);
                } else {
                    newAction_river = 0;
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                }
            }
        } else {
            action = Action.FOLD;
        }

        if(action == null) {
            for(Action action_t : allowedActions) {
                if(action_t.getName().equals("Check") || action_t.getName().equals("Call")) {
                    newAction_river = 0;
                } else if(action_t.getName().equals("Bet") || action_t.getName().equals("Raise")) {
                    newAction_river = 1;
                } else {
                    newAction_river = 2;
                }
                action = action_t;
                break;
            }
        }

        System.out.println(action);

        if(!newActionRound_river.contains(newAction_river)) {
            newActionRound_river.add(newAction_river);
        }

        EstadoAccionAnterior_river = EstadoAccionActual_river;				// Updating values for the next time
        AccionAnterior_river=newAction_river;
        alpha_river *= dDecFactorLR;						// Reducing the learning rate
        if (alpha_river < dMINLearnRate) alpha_river = dMINLearnRate;

        return action;

    }

    //Ajustar Valores Q(s,a)
    public void Refuerzo(int R){
        if (EstadoAccionAnterior != null) {
            for(int i = 0; i < newActionRound.size(); i++) {
                EstadoAccionAnterior.getAction()[newActionRound.get(i)] +=  alpha * (R + gamma * dQmax - EstadoAccionAnterior.getAction()[newActionRound.get(i)]);
            }
        }
        newActionRound.clear();
    }

    //Ajustar Valores Q(s,a)
    public void Refuerzo_turn(int R){
        if (EstadoAccionAnterior_turn != null) {
            for(int i = 0; i < newActionRound_turn.size(); i++) {
                EstadoAccionAnterior_turn.getAction()[newActionRound_turn.get(i)] += alpha_turn * (R + gamma * dQmax - EstadoAccionAnterior_turn.getAction()[newActionRound_turn.get(i)]);
            }
        }
        newActionRound_turn.clear();
    }

    //Ajustar Valores Q(s,a)
    public void Refuerzo_river(int R){
        if (EstadoAccionAnterior_river != null) {
            for(int i = 0; i < newActionRound_river.size(); i++) {
                EstadoAccionAnterior_river.getAction()[newActionRound_river.get(i)] += alpha_river * (R + gamma * dQmax - EstadoAccionAnterior_river.getAction()[newActionRound_river.get(i)]);
            }
        }
        newActionRound_river.clear();
    }

    private Action actChen(int minBet, int bet, Set<Action> allowedActions) {
        Action action = null;

        double chenScore = getChenScore(getCards());
        double chenScoreToPlay = tightness * 0.2;

        System.out.println(chenScore + " vs " + chenScoreToPlay);

        if ((chenScore < chenScoreToPlay)) {
            if (allowedActions.contains(Action.CHECK)) {
                // Always check for free if possible.
                action = Action.CHECK;
            } else {
                // Bad hole cards; play tight.
                action = Action.FOLD;
            }
        } else {
            // Good enough hole cards, play hand.
            if ((chenScore - chenScoreToPlay) >= ((20.0 - chenScoreToPlay) / 2.0)) {
                // Very good hole cards; bet or raise!
                if (aggression == 0) {
                    // Never bet.
                    if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                } else if (aggression == 100) {
                    // Always go all-in!
                    //int amount = 100 * minBet;
                    int amount = this.getCash();
                    if (allowedActions.contains(Action.BET)) {
                        action = new BetAction(amount);
                    } else if (allowedActions.contains(Action.RAISE)) {
                        action = new RaiseAction(amount);
                    } else if (allowedActions.contains(Action.CALL)) {
                        action = Action.CALL;
                    } else {
                        action = Action.CHECK;
                    }
                } else {
                    int amount = minBet;
                    int betLevel = aggression / 20;
                    for (int i = 0; i < betLevel; i++) {
                        amount *= 2;
                    }

                    if(amount>this.getCash()) amount= this.getCash();

                    if (bet < amount) {
                        if (allowedActions.contains(Action.BET)) {
                            action = new BetAction(amount);
                        } else if (allowedActions.contains(Action.RAISE)) {
                            action = new RaiseAction(amount);
                        } else if (allowedActions.contains(Action.CALL)) {
                            action = Action.CALL;
                        } else {
                            action = Action.CHECK;
                        }
                    } else {
                        if (allowedActions.contains(Action.CALL)) {
                            action = Action.CALL;
                        } else {
                            action = Action.CHECK;
                        }
                    }
                }
            } else {
                // Decent hole cards; check or call.
                if (allowedActions.contains(Action.CHECK)) {
                    action = Action.CHECK;
                } else {
                    action = Action.CALL;
                }
            }

            return action;
        }
        return action;
    }

    private double getChenScore(Card[] cards) {
        if (cards.length != 2) {
            throw new IllegalArgumentException("Invalid number of cards: " + cards.length);
        }

        // Analyze hole cards.
        int rank1 = cards[0].getRank();
        int suit1 = cards[0].getSuit();
        int rank2 = cards[1].getRank();
        int suit2 = cards[1].getSuit();
        int highRank = Math.max(rank1, rank2);
        int lowRank = Math.min(rank1, rank2);
        int rankDiff = highRank - lowRank;
        int gap = (rankDiff > 1) ? rankDiff - 1 : 0;
        boolean isPair = (rank1 == rank2);
        boolean isSuited = (suit1 == suit2);

        double score = 0.0;

        // 1. Base score highest rank only
        if (highRank == Card.ACE) {
            score = 10.0;
        } else if (highRank == Card.KING) {
            score = 8.0;
        } else if (highRank == Card.QUEEN) {
            score = 7.0;
        } else if (highRank == Card.JACK) {
            score = 6.0;
        } else {
            score = (highRank + 2) / 2.0;
        }

        // 2. If pair, double score, with minimum score of 5.
        if (isPair) {
            score *= 2.0;
            if (score < 5.0) {
                score = 5.0;
            }
        }

        // 3. If suited, add 2 points.
        if (isSuited) {
            score += 2.0;
        }

        // 4. Subtract points for gap.
        if (gap == 1) {
            score -= 1.0;
        } else if (gap == 2) {
            score -= 2.0;
        } else if (gap == 3) {
            score -= 4.0;
        } else if (gap > 3) {
            score -= 5.0;
        }

        // 5. Add 1 point for a 0 or 1 gap and both cards lower than a Queen.
        if (!isPair && gap < 2 && rank1 < Card.QUEEN && rank2 < Card.QUEEN) {
            score += 1.0;
        }

        // Minimum score is 0.
        if (score < 0.0) {
            score = 0.0;
        }

        // 6. Round half point scores up.
        return Math.round(score);
    }

    private Action doCheckCall(Set<Action> allowedActions) {
        Action action = null;
        Iterator<Action> it = allowedActions.iterator();
        while (it.hasNext()) {
            action = it.next();
            if (action.getName().equals(Action.CHECK.getName()) || action.getName().equals(Action.CALL.getName()))
                return action;
        }
        return action;
    }

    private Action doFold(Set<Action> allowedActions) {
        Action action = null;
        Iterator<Action> it = allowedActions.iterator();
        while (it.hasNext()) {
            action = it.next();
            if (action.getName().equals(Action.FOLD.getName()))
                return action;
        }
        return action;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public int getThinking_Time() {
        return thinking_Time;
    }

    public void setThinking_Time(int thinking_Time) {
        this.thinking_Time = thinking_Time;
    }

    public int getTightness() {
        return tightness;
    }

    public void setTightness(int tightness) {
        this.tightness = tightness;
    }

    public int getAggression() {
        return aggression;
    }

    public void setAggression(int aggression) {
        this.aggression = aggression;
    }

    public double getAlpha() {
        return alpha;
    }

    public void setAlpha(double alpha) {
        this.alpha = alpha;
    }

    public double getGamma() {
        return gamma;
    }

    public void setGamma(double gamma) {
        this.gamma = gamma;
    }

    public double getEpsilon() {
        return epsilon;
    }

    public void setEpsilon(double epsilon) {
        this.epsilon = epsilon;
    }

    public int getRandom_prob() {
        return random_prob;
    }

    public void setRandom_prob(int random_prob) {
        this.random_prob = random_prob;
    }


    public Vector<StateAction> getvEstadosAcciones() {
        return vEstadosAcciones;
    }

    public void setvEstadosAcciones(Vector<StateAction> vEstadosAcciones) {
        this.vEstadosAcciones = vEstadosAcciones;
    }

    public Vector<StateAction> getvEstadosAcciones_turn() {
        return vEstadosAcciones_turn;
    }

    public void setvEstadosAcciones_turn(Vector<StateAction> vEstadosAcciones_turn) {
        this.vEstadosAcciones_turn = vEstadosAcciones_turn;
    }

    public Vector<StateAction> getvEstadosAcciones_river() {
        return vEstadosAcciones_river;
    }

    public void setvEstadosAcciones_river(Vector<StateAction> vEstadosAcciones_river) {
        this.vEstadosAcciones_river = vEstadosAcciones_river;
    }

}
