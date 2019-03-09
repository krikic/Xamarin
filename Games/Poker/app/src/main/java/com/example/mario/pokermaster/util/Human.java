package com.example.mario.pokermaster.util;

import android.view.View;
import android.widget.Button;

import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

import com.example.mario.pokermaster.actions.Action;

public class Human extends Player {

	private Button btn_check_call, btn_bet_raise, btn_fold;

	public Human(String name, int cash) {
		super(name, cash);
	}

	public void showActions(Set<Action> allowedActions) {
		Action action = null;
		Iterator<Action> it = allowedActions.iterator();
		while (it.hasNext()) {
			action = it.next();
			if (action.getName().equals(Action.CHECK.getName()) || action.getName().equals(Action.CALL.getName())) {
				btn_check_call.setText(action.getName());
				btn_check_call.setVisibility(View.VISIBLE);
			}
			if (action.getName().equals(Action.BET.getName()) || action.getName().equals(Action.RAISE.getName())) {
				btn_bet_raise.setText(action.getName());
				btn_bet_raise.setVisibility(View.VISIBLE);
			}
			if (action.getName().equals(Action.FOLD.getName())) {
				btn_fold.setText(action.getName());
				btn_fold.setVisibility(View.VISIBLE);
			}
		}
	}
	
	public int doBet() {
		int amount_temp = 0;
		//Scanner scanner = new Scanner(System.in);
		System.out.println("Select the amount to bet: ");
		amount_temp = 30;
		//amount_temp = Integer.parseInt(scanner.nextLine());
		return amount_temp;
	}
	
	public int doRaise() {
		int amount_temp = 0;
		//Scanner scanner = new Scanner(System.in);
		System.out.println("Select the amount to raise: ");
		amount_temp = 30;
		//amount_temp = Integer.parseInt(scanner.nextLine());
		return amount_temp;
	}

	public Button getBtn_check_call() {
		return btn_check_call;
	}

	public void setBtn_check_call(Button btn_check_call) {
		this.btn_check_call = btn_check_call;
	}

	public Button getBtn_bet_raise() {
		return btn_bet_raise;
	}

	public void setBtn_bet_raise(Button btn_bet_raise) {
		this.btn_bet_raise = btn_bet_raise;
	}

	public Button getBtn_fold() {
		return btn_fold;
	}

	public void setBtn_fold(Button btn_fold) {
		this.btn_fold = btn_fold;
	}
}
