package com.example.mario.pokermaster.util;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

import com.example.mario.pokermaster.actions.Action;

public interface IPlayer {

	/**
	 * Prepares the player for another hand.
	 */
	public void resetHand();

	/**
	 * Resets the player's bet.
	 */
	public void resetBet();

	/**
	 * Sets the hole cards.
	 */
	public void setCards(List<Card> cards);

	/**
	 * Returns whether the player has his hole cards dealt.
	 * 
	 * @return True if the hole cards are dealt, otherwise false.
	 */
	public boolean hasCards();

	/**
	 * Returns the player's name.
	 * 
	 * @return The name.
	 */
	public String getName();

	/**
	 * Returns the player's current amount of cash.
	 * 
	 * @return The amount of cash.
	 */
	public int getCash();

	/**
	 * Returns the player's current bet.
	 * 
	 * @return The current bet.
	 */
	public int getBet();

	/**
	 * Sets the player's current bet.
	 * 
	 * @param bet
	 *            The current bet.
	 */
	public void setBet(int bet);

	/**
	 * Returns the player's most recent action.
	 * 
	 * @return The action.
	 */
	public Action getAction();
	/**
	 * Sets the player's most recent action.
	 * 
	 * @param action
	 *            The action.
	 */
	public void setAction(Action action);

	/**
	 * Indicates whether this player is all-in.
	 * 
	 * @return True if all-in, otherwise false.
	 */
	public boolean isAllIn();
	/**
	 * Returns the player's hole cards.
	 * 
	 * @return The hole cards.
	 */
	public Card[] getCards();

	/**
	 * Posts the small blind.
	 * 
	 * @param blind
	 *            The small blind.
	 */
	public void postSmallBlind(int blind);

	/**
	 * Posts the big blinds.
	 * 
	 * @param blind
	 *            The big blind.
	 */
	public void postBigBlind(int blind);

	/**
	 * Pays an amount of cash.
	 * 
	 * @param amount
	 *            The amount of cash to pay.
	 */
	public void payCash(int amount);

	/**
	 * Wins an amount of money.
	 * 
	 * @param amount
	 *            The amount won.
	 */
	public void win(int amount);

	/**
	 * Returns a clone of this player with only public information.
	 * 
	 * @return The cloned player.
	 */
	public Player publicClone();
	
	/**
	 * Requests this player to act, selecting one of the allowed actions.
	 * 
	 * @param minBet
	 *            The minimum bet.
	 * @param bet
	 *            The current bet.
	 * @param allowedActions
	 *            The allowed actions.
	 * 
	 * @return The selected action.
	 */
	public Action act(int minBet, int bet, Set<Action> allowedActions);

	public Action act(int minBet, int bet, Set<Action> allowedActions, Hand board);

	public int doBet();
	
	public int doRaise();
	
	/** {@inheritDoc} */
	@Override
	public String toString();
	
}
