package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;

/**
 *  not using isCapture() or numMoves() from UML diagram, may want to implement later
 */

public class ParPawnMovement extends ParMoveAction {

	private static final long serialVersionUID = 375413242779265145L;

	private int moveFrom;
	private int moveTo;

	public ParPawnMovement(GamePlayer player, int initMoveFrom, int initMoveTo) {
		super(player);

		moveFrom = initMoveFrom;
		moveTo = initMoveTo;
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
}
