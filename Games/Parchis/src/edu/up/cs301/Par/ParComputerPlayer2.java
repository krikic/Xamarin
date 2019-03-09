package edu.up.cs301.Par;

import edu.up.cs301.game.infoMsg.GameInfo;

public class ParComputerPlayer2 extends ParComputerPlayer{
	

	public ParComputerPlayer2(String name) {
		super(name);
	}

	@Override
	protected void receiveInfo(GameInfo info) {
		//if its not a ParState, do nothing
		//cast
		if(!(info instanceof ParState)) return;
		ParState myState = (ParState)info;
		int[] dice = myState.getDice();
		int[][] board = myState.getBoard();

		//if it's not our move do nothing
		if(myState.getWhoseMove() != this.playerNum) return; 

		//delay to appear to be thinking
		sleep(100);
		//if dice are empty, roll the dice
		if(dice[0] == -1 && dice[1] ==-1){
			ParRollAction roll = new ParRollAction(this);
			game.sendAction(roll);
			return;
		}

		//delay to appear to be thinking
		sleep(100);

		boolean[] possibleMoves = myState.legalMoves();
		boolean canMove= false;
		//if there is a possible move, get out of loop, if no possible moves are available, send noPossibleMoveAction
		for(int i = 0; i<possibleMoves.length; ++i){
			if(possibleMoves[i]){
				canMove=true;
				break;
			}

		}
		
		//check if any computer has a possible move to their home space
		if ((board[this.playerNum][74] == this.playerNum) && (dice[0] ==1 || dice[1]==1)){
			ParPawnMovement move = new ParPawnMovement(this, 74, -2, false);
			game.sendAction(move);
			return;
		}
		if ((board[this.playerNum][73] == this.playerNum) && (dice[0] ==2 || dice[1]==2)){
			ParPawnMovement move = new ParPawnMovement(this, 73, -2, false);
			game.sendAction(move);
			return;
		}
		if ((board[this.playerNum][72] == this.playerNum) && (dice[0] ==3 || dice[1]==3)){
			ParPawnMovement move = new ParPawnMovement(this, 72, -2, false);
			game.sendAction(move);
			return;
		}
		if ((board[this.playerNum][71] == this.playerNum) && (dice[0] ==4 || dice[1]==4)){
			ParPawnMovement move = new ParPawnMovement(this, 71, -2, false);
			game.sendAction(move);
			return;
		}
		if ((board[this.playerNum][70] == this.playerNum) && (dice[0] ==5 || dice[1]==5)){
			ParPawnMovement move = new ParPawnMovement(this, 70, -2, false);
			game.sendAction(move);
			return;
		}
		if ((board[this.playerNum][69] == this.playerNum) && (dice[0] ==6 || dice[1]==6)){
			ParPawnMovement move = new ParPawnMovement(this, 69, -2, false);
			game.sendAction(move);
			return;
		}

		//check all the moves, then check if the actual player is equal to the to board spot they 
		//are trying to travel to. 
		for(int i =0; i<possibleMoves.length; ++i){
			if(possibleMoves[i]){
				//moving pawn from start onto board
				if(i==4){
					ParPawnMovement move = new ParPawnMovement(this, -1, 4, false);
					game.sendAction(move);
					return;
				}
				

				int to = i;
				if(i-dice[0]>=0){
					if(dice[0] != -1){
						if((playerNum == board[playerNum][i-dice[0]])  ){
							int from = i - dice[0];
							ParPawnMovement move = new ParPawnMovement(this, from, to, false);
							game.sendAction(move);
							return;
						}
					}
				}
				if(i-dice[1] >=0 ){
					if(dice[1]!=-1){
						if((playerNum == board[playerNum][i-dice[1]])){
							int from = i - dice[1];
							ParPawnMovement move = new ParPawnMovement(this, from, to, false);
							game.sendAction(move);
							return;
						}
					}
				}
			}
		}
		
		if(!canMove){
			NoPossibleMoveAction npma = new NoPossibleMoveAction(this);
			game.sendAction(npma);
			return;
		}


		//if dice have been rolled, 
	}
	
	

}
