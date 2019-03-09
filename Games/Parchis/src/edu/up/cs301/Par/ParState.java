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
	private int[] dice = new int[2]; //an array of length two telling the values of the current player's dice
	private int[] numStart= {4,4,4,4};//an array of length 4 denoting how many pawns are in each players starting area
	private int[] numHome= {0,0,0,0}; //an array of length 4 denoting how many pawns each player has in Home



	/**
	 * Constructor for objects of class TTTState
	 */
	public ParState()
	{

		// initialize the state to be a brand new game
		board = new int[4][75];
		for (int i = 0; i < board.length; i++) {
			for (int j = 0; j < board[i].length; j++) {

				board[i][j] = -1;
			}
		}

		// make it random player's move
		waitingOn = (int) (Math.random()*4);
		dice[0] = -1;
		dice[1] = -1;

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
		this.board = original.board;
		this.numHome = original.numHome;
		this.numStart = original.numStart;
		dice = original.getDice();

	}

	/**
	 * determines the possible legal moves for the current player
	 * @return
	 * 		true for spaces where the current player could possibly move
	 */
	public boolean[] legalMoves(){
		boolean[] legalMoves = new boolean[75];
		Arrays.fill(legalMoves, false);

		if((dice[0]==5 || dice[1] ==5 || (((dice[0]+dice[1])==5)) && dice[0] !=-1 && dice[1]!=-1)) {
			if(numStart[waitingOn]>0 && board[waitingOn][4]==-1){
				legalMoves[4] = true;
			}
		}

		for(int i = 0; i < board[waitingOn].length; ++i){
			if(dice[0]+i>=75){
				//do nothing
			}
			else if(dice[1]+i>=75){
				//do nothing
			}
			else{
				if(board[waitingOn][i]==waitingOn){
					if(board[waitingOn][i+dice[0]]==-1 && dice[0]!=-1){
						legalMoves[i+dice[0]]=true;
					}
					if(board[waitingOn][i+dice[1]]==-1 && dice[1]!=-1){
						legalMoves[i+dice[1]]=true;
					}

				}
			}
		}
		return legalMoves;
	}

	/**
	 * moves a selected pawn to the selected position
	 * 
	 */
	public boolean movePawn(int selectPawn, int moveTo, boolean[] legalMoves){
		//if moving from start
		if(selectPawn ==-1){
			if(legalMoves[4]){
				numStart[waitingOn]--;
				board[waitingOn][4]= waitingOn;
				if(dice[0]==5){
					dice[0]= -1;
				}
				else if(dice[1]==5){
					dice[1]=-1;
				}
				else{
					dice[1]=-1;
					dice[0]=-1;
				}
				return true;
			}
			else return false;
		}
		//if moving home
		if(moveTo==-2){
			if(selectPawn+dice[1]==75){
				numHome[waitingOn]++;
				dice[1]=-1;
				board[waitingOn][selectPawn] =-1;
				return true;
			}

			if(selectPawn+dice[0]==75){
				numHome[waitingOn]++;

				dice[0]=-1;
				board[waitingOn][selectPawn] =-1;
				return true;
			}
			if(selectPawn+dice[1]!=75){
				return false;
			}
			if(selectPawn+dice[0]!=75){
				return false;
			}

		}
		//moving around a normal board
		if(board[waitingOn][selectPawn]==waitingOn){
			if(legalMoves[moveTo]){
				board[waitingOn][moveTo]=waitingOn;
				capture();
				board[waitingOn][selectPawn]=-1;

				if(dice[1]!=-1 && (moveTo-selectPawn)==dice[1]){
					dice[1]=-1;
					return true;
				}


				if(dice[0]!=-1 && (moveTo-selectPawn)==(dice[0])){
					dice[0]=-1;
					return true;
				}

			}
		}
		return false;
	}//movePawn

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

	public void setDice(){
		dice[0]=-1;
		dice[1]=-1;
	}

	/**
	 * set the current player's dice
	 */
	public void rollDice(){
		for(int i = 0; i< dice.length; ++i){
			dice[i] = (int)((Math.random()*6)+1);
		}
	}

	/**
	 * calculates if the move is a capture
	 */
	public void capture(){
		if(waitingOn ==0){
			//yellow captures blue
			for(int i =17; i<=67; ++i){
				if(board[1][i-17]==1 && board[0][i]==0){
					board[1][i-17]=-1;
					numStart[1]++;
				}
			}
			for(int i = 0; i<=16; ++i){
				if(board[1][i+51]==1 && board[0][i]==0){
					board[1][i+51]=-1;
					numStart[1]++;
				}
			}
			//yellow capture red
			for( int i =34; i<=67; ++i){
				if(board[2][i-34]==2 && board[0][i]==0){
					board[2][i-34] =-1;
					numStart[2]++;
				}
			}
			for( int i =0; i<=33; ++i){
				if(board[2][i+34]==2 && board[0][i]==0){
					board[2][i+34] =-1;
					numStart[2]++;
				}
			}
			//yellow capture green
			for(int i =51; i<=67;++i){
				if(board[3][i-51]==3 && board[0][i]==0){
					board[3][i-51]=-1;
					numStart[3]++;
				}
			}
			for(int i =0; i<=50;++i){
				if(board[3][i+17]==3 && board[0][i]==0){
					board[3][i+17]=-1;
					numStart[3]++;
				}
			}
		}//if
		if (waitingOn == 1){
			//blue captures red
			for(int i =17; i<=67; ++i){
				if(board[2][i-17]==2 && board[1][i]==1){
					board[2][i-17]=-1;
					numStart[2]++;
				}
			}
			for(int i = 0; i<=16; ++i){
				if(board[2][i+51]==2 && board[1][i]==1){
					board[2][i+51]=-1;
					numStart[2]++;
				}
			}
			//blue capture green
			for( int i =34; i<=67; ++i){
				if(board[3][i-34]==3 && board[1][i]==1){
					board[3][i-34] =-1;
					numStart[3]++;
				}
			}
			for( int i =0; i<=33; ++i){
				if(board[3][i+34]==3 && board[1][i]==1){
					board[3][i+34] =-1;
					numStart[3]++;
				}
			}
			//blue capture yellow
			for(int i =51; i<=67;++i){
				if(board[0][i-51]==0 && board[1][i]==1){
					board[0][i-51]=-1;
					numStart[0]++;
				}
			}
			for(int i =0; i<=50;++i){
				if(board[0][i+17]==0 && board[1][i]==1){
					board[0][i+17]=-1;
					numStart[0]++;
				}
			}
		}//if
		if (waitingOn == 2){
			//red captures green
			for(int i =17; i<=67; ++i){
				if(board[3][i-17]==3 && board[2][i]==2){
					board[3][i-17]=-1;
					numStart[3]++;
				}
			}
			for(int i = 0; i<=16; ++i){
				if(board[3][i+51]==3 && board[2][i]==2){
					board[3][i+51]=-1;
					numStart[3]++;
				}
			}
			//red captures yellow
			for( int i =34; i<=67; ++i){
				if(board[0][i-34]==0 && board[2][i]==2){
					board[0][i-34] =-1;
					numStart[0]++;
				}
			}
			for( int i =0; i<=33; ++i){
				if(board[0][i+34]==0 && board[2][i]==2){
					board[0][i+34] =-1;
					numStart[0]++;
				}
			}
			//red captures blue
			for(int i =51; i<=67;++i){
				if(board[1][i-51]==1 && board[2][i]==2){
					board[1][i-51]=-1;
					numStart[1]++;
				}
			}
			for(int i =0; i<=50;++i){
				if(board[1][i+17]==1 && board[2][i]==2){
					board[1][i+17]=-1;
					numStart[1]++;
				}
			}				
		}//if
		
		if(waitingOn ==3){
			//green capture yellow 
			for(int i =17; i<=67; ++i){
				if(board[0][i-17]==0 && board[3][i]==3){
					board[0][i-17]=-1;
					numStart[0]++;
				}
			}
			for(int i = 0; i<=16; ++i){
				if(board[0][i+51]==0 && board[3][i]==3){
					board[0][i+51]=-1;
					numStart[0]++;
				}
			}
			//green capture blue
			for( int i =34; i<=67; ++i){
				if(board[1][i-34]==1 && board[3][i]==3){
					board[1][i-34] =-1;
					numStart[1]++;
				}
			}
			for( int i =0; i<=33; ++i){
				if(board[1][i+34]==1 && board[3][i]==3){
					board[1][i+34] =-1;
					numStart[1]++;
				}
			}
			//green capture red
			for(int i =51; i<=67;++i){
				if(board[2][i-51]==2 && board[3][i]==3){
					board[2][i-51]=-1;
					numStart[2]++;
				}
			}
			for(int i =0; i<=50;++i){
				if(board[2][i+17]==2 && board[3][i]==3){
					board[2][i+17]=-1;
					numStart[2]++;
				}
			}		
		}
	}//capture


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

	public int[][] getBoard(){
		return board;
	}
	public int whoseTurn(){
		return waitingOn;
	}
}