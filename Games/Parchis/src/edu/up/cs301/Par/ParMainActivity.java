package edu.up.cs301.Par;

import java.util.ArrayList;

import android.content.pm.ActivityInfo;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * @author Ben Forsee
 * @version November 2013
 */
public class ParMainActivity extends GameMainActivity {
	
	public static final int PORT_NUMBER = 5213;

	/**
	 * a Parcheesi game is for two to four players. The default is human vs. computer
	 */
	@Override
	public GameConfig createDefaultConfig() {

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();
		//lock screen
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		
		// Player GUI
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new ParHumanPlayer(name);
			}
		});
			
		// dumb computer player
		playerTypes.add(new GamePlayerType("Computer Player (slow)") {
			public GamePlayer createPlayer(String name) {
				return new ParComputerPlayer1(name);
			}
		});
		
		// smarter computer player
		playerTypes.add(new GamePlayerType("Computer Player (speedy)") {
			public GamePlayer createPlayer(String name) {
				return new ParComputerPlayer2(name);
			}
		});

		// Create a game configuration class for Parcheesi
		GameConfig defaultConfig = new GameConfig(playerTypes, 4,4, "Parcheesi", PORT_NUMBER);

		// Add the default players
		defaultConfig.addPlayer("Human", 0); // yellow-on-blue GUI
		defaultConfig.addPlayer("Computer", 1); // slow computer player
		defaultConfig.addPlayer("Computer2", 2); // fast computer player
		defaultConfig.addPlayer("Computer3", 1); // slow computer player

		// Set the initial information for the remote player
		defaultConfig.setRemoteData("Remote Player", "", 0);
		
		//done!
		return defaultConfig;
		
	}//createDefaultConfig


	/**
	 * createLocalGame
	 * 
	 * Creates a new game that runs on the server tablet,
	 * 
	 * @return a new, game-specific instance of a sub-class of the LocalGame
	 *         class.
	 */
	@Override
	public LocalGame createLocalGame() {
		return new ParGameLocal();
	}

}
