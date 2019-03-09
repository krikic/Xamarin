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
	protected GamePlayer[] players; 
	protected String[] playerNames;
	protected ParState state; 
	protected GameConfig gConfig; 
	protected final int[] endTurn = {-1,-1};
	
	
	public ParGameLocal()
	{
		super();
		state = new ParState();
		state.setNames(playerNames);
	}
	
	/**
	 * 
	 */
//	protected void initializeGame(){
//		//runs the first surface view
//	
//	}//initializeGame
//	
	protected boolean canMove(int playerIdx){
		return playerIdx == state.getWhoseMove();
	}//canMove
	
	
	/**
	 * @return
	 */
	protected boolean makeMove(GameAction action){
		ParMoveAction pm = (ParMoveAction) action;
		int whoseMove = state.getWhoseMove();
		
		if(pm.isRoll()){
			state.setDice();
		}
		else if(pm.isPawn()){
			ParPawnMovement ppm = (ParPawnMovement) action;
			int from = ppm.getMoveFrom();
			int to = ppm.getMoveTo();
			if(whoseMove==0){
				if(to==0){
					to=76;
				}
			}
			if(whoseMove==1){
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
			if(whoseMove==2){
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
			if(whoseMove==3){
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
			
			
			if(state.getDice()== endTurn){
				if(whoseMove >= (players.length-1)){
					state.setWhoseMove(0);
				}
				state.setWhoseMove(whoseMove+1);
			}
			
		}
	
		
	// return true, indicating the it was a legal move
		return true;
	}//makeMove
	
	protected void sendUpdatedStateTo(GamePlayer p){
		// make a copy of the state, and send it to the player
				p.sendInfo(new ParState(state));
	}//sendUpdatedStateTo
	
	/**
	 * 
	 * @param p
	 */
//	protected int getPlayerIdx(GamePlayer p){
//		super();
//	}//getPlayerIdx
	
//	private void checkAndHandleAction(ParMoveAction action){
//		// get the player and player ID
//				GamePlayer player = action.getPlayer();
//				int playerId = getPlayerIdx(player);
//				
//				// if the player is NOT a player who is presently allowed to
//				// move, send the player a message
//				if (!canMove(playerId)) {;
//					player.sendInfo(new NotYourTurnInfo());
//					return;
//				}
//
//				// attempt to make the move; if the move was not a legal one,
//				// send the player a message to that effect
//				if (!makeMove()) {
//					player.sendInfo(new IllegalMoveInfo());
//					return;
//				}
//
//				// The move was a legal one, so presumably the state of the game was
//				// changed. Send all players the updated state. 
//				sendUpdatedStateTo(player);
//				
//				// determine whether there is a winner; if so, finish up the game
//				String overMsg = checkIfGameOver();
//				if (overMsg != null) {
//					finishUpGame(overMsg);
//				}
//	}//checkAndHandleAction
	
	
	/**
	 * probably shouldn't return a string. should probably become a reference for 
	 * Anthony's MainActivity so he can create a pop up box.
	 * Easiest way would be by making this a boolean, and having his reference towards it
	 * in the M.A. 
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
