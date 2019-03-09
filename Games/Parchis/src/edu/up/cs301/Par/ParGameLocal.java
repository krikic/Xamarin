package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;

/**
 * ParGameLocal is the object class that interacts with players,
 * handles actions, and enforces rules. 
 * 
 * @author Alex Bowns
 * @version November 10, 2013
 */

public class ParGameLocal extends LocalGame implements ParGame{

	//instance variables
	protected ParState state; 
	protected GameConfig gConfig; 
	protected final int[] endTurn = {-1,-1};
	protected int[] gamePlayerInd;


	public ParGameLocal()
	{
		super();
		state = new ParState();

	}

	/** 
	 * which player can move
	 */
	protected boolean canMove(int playerIdx){
		return playerIdx == state.getWhoseMove();
	}//canMove


	/**
	 * receiving a move action and handling what to do with it
	 * 
	 * @return
	 */
	@Override
	protected boolean makeMove(GameAction action){
		ParMoveAction pm = (ParMoveAction) action;
		int whoseMove = state.getWhoseMove();
		int[] initDice = state.getDice();
		boolean start = false;
		boolean end = false;

		//handle dice roll action
		if(pm.isRoll() && initDice[0] == -1 && initDice[1] == -1){
			state.rollDice();
		}

		//handle move pawn action
		else if(pm.isPawn()){
			ParPawnMovement ppm = (ParPawnMovement) action;
			int from = ppm.getMoveFrom();
			int to = ppm.getMoveTo();
			//moving from the start
			if(from == -1){
				start = true;
			}
			
			//moving to home
			if(to ==0 || to==-2){
				end=true;
			}
			//handles special case for human
			if(ppm.getHuman()){
				//yellow player
				if(whoseMove==0){
					if(from ==97){
						start =true;
						
					}
					for(int i=76; i<=100; ++i){
						if(i!=97 && (from ==i || to == i)){
							return false;
						}
						
					}
					
				}
				//blue player
				if(whoseMove==1){
					if(from ==98){
						start =true;
					}
					for(int i=69; i<=75; ++i){
						if(from ==i || to == i){
							return false;
						}
						
					}
					for(int i=83; i<=100; ++i){
						if(i!=98 && (from ==i || to == i)){
							return false;
						}
						
					}
					if(to>=22&&to<=68){
						to=to-17;
					}
					else if(to>=1 && to<=17){
						to = to +51;
					}
					else if(to>=76 && to<=82){
						to= to - 7;
					}
					else if(to==0){
						to=76;
					}
					if(from>=22&&from<=68){
						from=from-17;
					}
					else if(from>=1 && from<=17){
						from = from +51;
					}
					else if(from>=76 && from<=82){
						from= from - 7;
					}
				}
				//red player
				if(whoseMove==2){
					if(from ==99){
						start =true;
					}
					for(int i=69; i<=82; ++i){
						if(from ==i || to == i){
							return false;
						}
						
					}
					for(int i=90; i<=100; ++i){
						if(i!=99 && (from ==i || to == i)){
							return false;
						}
						
					}
					if(to>=39&&to<=68){
						to=to-34;
					}
					else if(to>=1 && to<=34){
						to = to +34;
					}
					else if(to>=83 && to<=89){
						to= to - 14;
					}
					else if(to==0){
						to=76;
					}
					if(from>=39&&from<=68){
						from=from-34;
					}
					else if(from>=1 && from<=34){
						from = from +34;
					}
					else if(from>=83 && from<=89){
						from= from - 14;
					}
				}
				//green player
				if(whoseMove==3){
					if(from ==100){
						start =true;
					}
					for(int i=69; i<=89; ++i){
						if(from ==i || to == i){
							return false;
						}
						
					}
					for(int i=97; i<100; ++i){
						if((from ==i || to == i)){
							return false;
						}
						
					}
					if(to>=56&&to<=68){
						to=to-51;
					}
					else if(to>=1 && to<=51){
						to = to +17;
					}
					else if(to>=90 && to<=96){
						to= to - 21;
					}
					else if(to==0){
						to=76;
					}
					if(from>=56&&from<=68){
						from=from-51;
					}
					else if(from>=1 && from<=51){
						from = from +17;
					}
					else if(from>=90 && from<=96){
						from= from - 21;
					}
				}
			}
			if(ppm.getHuman()){
				to = to-1;
				from = from-1;
			}
			if(start){
				from = -1;
			}
			if(end){
				to=-2;
			}
			
			//tells state that a movement was requested
			state.movePawn(from,to, state.legalMoves());



			// if next players turn, move on
			int[] dice = state.getDice();
			if(dice[0] ==-1 && dice[1]==-1 ){
				if(whoseMove >= (players.length-1)){
					state.setWhoseMove(0);
				}
				else{
					state.setWhoseMove(whoseMove+1);
				}
			}

		}
		//action passed if player has no moves
		//move to next player
		else if(pm.noMove()){
			state.setDice();
			if(whoseMove >= (players.length-1)){
				state.setWhoseMove(0);
			}
			else{
				state.setWhoseMove(whoseMove+1);
			}
		}//makeMove


		// return true, indicating the it was a legal move
		return true;
	}//makeMove

	protected void sendUpdatedStateTo(GamePlayer p){
		// make a copy of the state, and send it to the player
		p.sendInfo(new ParState(state));
	}//sendUpdatedStateTo



	


	/**
	 * check if game is won and return winner
	 * @return
	 */
	protected String checkIfGameOver(){
		for (int j = 0; j <= 3; j++) {
			if (state.getHome()[j] >= 4){
				return "The winner is "+playerNames[j];

			}
		}
		{
			return null; 
		}
	}//checkIfGameOver

	//finish game
	private void finishUpGame(String msg)
	{
		if (checkIfGameOver() != null){
			msg = checkIfGameOver();
		}
		else {
			msg = null;
		}

	}
}//ParGameLocal
