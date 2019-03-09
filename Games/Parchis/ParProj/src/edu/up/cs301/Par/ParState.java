package edu.up.cs301.Par;

import java.util.Arrays;

import edu.up.cs301.game.infoMsg.GameState;


/**
 * Contains the state of a Parcheesi game.  Sent by the game when
 * a player wants to enquire about the state of the game.  (E.g., to display
 * it, or to help figure out its next move.)
 * 
 * @author Ben Forsee 
 * @version November 2013
 */
public class ParState extends GameState
{
	private static final long serialVersionUID = 357289347583247587L;

    ///////////////////////////////////////////////////
    // ************** instance variables ************
    ///////////////////////////////////////////////////
	
    private int[][] board;	// a two dimensional array telling the position of all pawns on the board
    private int waitingOn; // an int that tells whose move it is
    private int[] dice; //an array of length two telling the values of the current player's dice
    private int[] numStart;//an array of length 4 denoting how many pawns are in each players starting area
    private int[] numHome; //an array of length 4 denoting how many pawns each player has in Home
	private String[] playerNames; // and array of player Names
    
    

    /**
     * Constructor for objects of class TTTState
     */
    public ParState()
    {
    
        // initialize the state to be a brand new game
        board = new int[4][75];
        for (int i = 0; i < board.length; i++) {
        	for (int j = 0; j < board[i].length; j++) {
        		board[i][j] = 0;
        	}
        }
        
        // make it random player's move
        waitingOn = (int) Math.random()*4;
    }// constructor
    
    /**
     * Copy constructor for class TTTState
     *  
     * @param original
     * 		the TTTState object that we want to clone
     */
    public ParState(ParState original)
    {
    	// create a new 3x3 array, and copy the values from
    	// the original
    	board = new int[4][75];
    	for (int i = 0; i < board.length; i++) {
    		for (int j = 0; j < board[i].length; j++) {
    			board[i][j] = original.board[i][j];
    		}
    	}
    	
    	// copy the player-to-move information
        waitingOn = original.waitingOn;
    }
    
    /**
     * determines the possible legal moves for the current player
     * @return
     * 		true for spaces where the current player could possibly move
     */
    public boolean[] legalMoves(){
    	boolean[] legalMoves = new boolean[75];
    	Arrays.fill(legalMoves, false);
    	
    	if((dice[0]==5 || dice[1] ==5 || ((dice[0]+dice[1])==5) && dice[0] !=-1 &&dice[1]!=-1) && numStart[waitingOn]>0 && board[waitingOn][5]==0){
    		legalMoves[5] = true;
    	}
    	for(int i = 0; i < board[waitingOn].length; ++i){
    		if(board[waitingOn][i]==1){
    			if(board[waitingOn][i+dice[0]]==0 && dice[0]!=-1){
    				legalMoves[i+dice[0]]=true;
    			}
    			else if(board[waitingOn][i+dice[1]]==0 && dice[1]!=-1){
    				legalMoves[i+dice[1]]=true;
    			}
    			
    		}
    	}
    	return legalMoves;
    }
    
    /**
     * moves a selected pawn to the selected position
     * 
     */
    public void movePawn(int selectPawn, int moveTo, boolean[] legalMoves){
    	if(board[waitingOn][selectPawn]==1){
    		if(legalMoves[moveTo]){
    			board[waitingOn][moveTo]=1;
    			board[waitingOn][selectPawn]=0;
    			if(dice[1]!=-1){
	    			if((moveTo-selectPawn)==dice[1]){
	    				dice[1]=-1;
	    				return;
	    			}
    			}
    			if(dice[0]!=-1){
	    			if((moveTo-selectPawn)==(dice[0])){
	    				dice[0]=-1;
	    				return;
	    			}
    			}
    		}
    	}
    }
    
    /**
     * Tells whose move it is.
     * 
     * @return the index (0 to 3) of the player whose move it is.
     */
    public int getWhoseMove() {
        return waitingOn;
    }
    
    /**
     * set whose move it is
     * @param id
     * 		the player we want to set as to whose move it is
     */
    public void setWhoseMove(int id) {
    	waitingOn = id;
    }
    
    /**
     * Tells the values of the dice
     * 
     * @return the array of the dice
     */
    public int[] getDice(){
    	return dice;
    }
    
    /**
     * set the current player's dice
     */
    public void setDice(){
    	for(int i = 0; i< dice.length; ++i){
    		dice[i] = (int)((Math.random()*6)+1);
    	}
    }
    
    /**
     * Tells the number of pawns each player has in home
     * 
     * @return the array of numHome
     */
    public int[] getHome(){
    	return numHome;
    }
    
    public int[] getStart(){
    	return numStart;
    }
	
	public void setNames(String[] initNames){
		for(int i = 0; i<4; ++i){
			playerNames[i]= initNames[i];
		}
	}
	public int[][] getBoard(){
		return board;
	}
}