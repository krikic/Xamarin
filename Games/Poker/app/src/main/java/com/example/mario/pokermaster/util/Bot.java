package com.example.mario.pokermaster.util;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.example.mario.pokermaster.actions.Action;

public class Bot extends Player {

	public Bot(String name, int cash) {
		super(name, cash);
		// TODO Auto-generated constructor stub
	}
	
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
	public Action act(int minBet, int bet, Set<Action> allowedActions) {
		Action action = null;

		System.out.println("\nIt's " + getName() + "'s turn.");
		System.out.println("The minimum bet:\tThe current bet:");
		System.out.println(minBet + "\t\t\t" + bet);

		Iterator<Action> it = allowedActions.iterator();
		while (it.hasNext()) {
			action = it.next();
			if(action.getName().equals(Action.CHECK.getName()) || action.getName().equals(Action.CALL.getName()))
				return action;
		}

		return action;
	}
	
}
