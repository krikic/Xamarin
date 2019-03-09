package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;


/**
 * passes roll along from GamePlayer to local game
 *
 */
public class ParRollAction extends ParMoveAction {

	private static final long serialVersionUID = 3754132427779265145L;


	public ParRollAction(GamePlayer player) {
		super(player);
		
		// TODO Auto-generated constructor stub
	}

	
 @Override
 public boolean isRoll()
 {
	 return true;
 }


}
