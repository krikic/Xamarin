package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;

/**
 *  passes a request pawn move along from GamePlayer to Local game
 */

public class ParPawnMovement extends ParMoveAction {

	private static final long serialVersionUID = 375413779265145L;

	private int moveFrom;
	private int moveTo;
	private boolean isHuman;

	public ParPawnMovement(GamePlayer player, int initMoveFrom, int initMoveTo, boolean initIsHuman) {
		super(player);

		moveFrom = initMoveFrom;
		moveTo = initMoveTo;
		isHuman = initIsHuman;
	}


	@Override
	public boolean isPawn()
	{
		return true;
	}
	
	public int getMoveFrom(){
		return moveFrom;
	}
	
	public int getMoveTo(){
		return moveTo;
	}
	
	public boolean getHuman(){
		return isHuman;
	}


}
