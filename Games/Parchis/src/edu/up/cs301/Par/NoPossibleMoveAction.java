package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;

public class NoPossibleMoveAction extends ParMoveAction {
	private static final long serialVersionUID = 3754132427765145L;


	public NoPossibleMoveAction(GamePlayer player) {
		super(player);
		
		// TODO Auto-generated constructor stub
	}

	
 @Override
 public boolean noMove()
 {
	 return true;
 }

}
