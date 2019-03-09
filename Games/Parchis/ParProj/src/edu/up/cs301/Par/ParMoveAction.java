package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * 
 * @author alexbowns
 * @version November 10, 2013
 */
public abstract class ParMoveAction extends GameAction {
	
	
	private static final long serialVersionUID = -7656616732779231544L;

	public ParMoveAction(GamePlayer player) {
		super(player);
	}

	public boolean isPawn(){ return false; }
	
	public boolean isRoll(){ return false; }
	
	public boolean isFives(){ return false; }
	
	public boolean isCapture(){ return false; }
}
