package edu.up.cs301.Par;

import edu.up.cs301.game.GameComputerPlayer;


/**
 * A computerized (automated) Parcheesi player. This is an abstract class,
 * that may be subclassed to allow different strategies.
 * 
 * @author Ben Forsee
 * @version November 2013
 */

public abstract class ParComputerPlayer extends GameComputerPlayer
    implements ParPlayer {
    /**
     * instance variable that tells which piece am I playing ('X' or 'O').
     * This is set once the player finds out which player they are, in the
     * 'initAfterReady' method.
     */
    protected char piece;

    /**
     * Constructor for objects of class TTTComputerPlayer
     */
    public ParComputerPlayer(String name) {
        // invoke superclass constructor
        super(name);
    }// constructor
    
}// class TTTComputerPlayer
