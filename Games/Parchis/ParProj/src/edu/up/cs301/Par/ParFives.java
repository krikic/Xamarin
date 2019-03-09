package edu.up.cs301.Par;

import edu.up.cs301.game.GamePlayer;



public class ParFives extends ParMoveAction {

	private static final long serialVersionUID = 375413427779265145L;


	public ParFives(GamePlayer player) {
		super(player);
		// TODO Auto-generated constructor stub
	}


	@Override
	public boolean isFives()
	{
		return true;
	}
}
