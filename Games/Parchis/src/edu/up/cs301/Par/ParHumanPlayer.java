package edu.up.cs301.Par;


import edu.up.cs301.animation.Animator;
import edu.up.cs301.Par.*;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.R.drawable;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.infoMsg.IllegalMoveInfo;
import edu.up.cs301.game.infoMsg.NotYourTurnInfo;
import android.app.Activity;
import android.content.Context;

/**
 * 
 * @author Anthony Prom
 * @author Ben Forsee
 * @author Alex Bowns
 * @version December 6, 2013
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;


public class ParHumanPlayer extends GameHumanPlayer implements OnGestureListener, OnTouchListener, OnClickListener, Animator{
	//instance variables...yeah
	private Activity myActivity;
	private ParSurfaceView myMainView = null;
	private ParState myState;
	protected float fullSquare;
	private Paint blackPaint = new Paint();
	private Paint whitePaint = new Paint();
	private Paint textPaint = new Paint();
	private Button quitButton;
	private Button rollButton;
	private QuitAction quitting = new QuitAction();;
	public SoundPool soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0); //Setup for SoundPool
	public int diceRolling; //Dice Rolling Sound
	public int quitSound; //Quit Warning Sound
	private Paint redPawnPaint = new Paint();
	private Paint bluePawnPaint = new Paint();
	private Paint greenPawnPaint = new Paint();
	private Paint yellowPawnPaint = new Paint();
	private Paint transPaint = new Paint();
	private Paint[] paint = new Paint[6];
	private int x,y;
	private boolean[] canMove;
	private ParPawn[] pawn = new ParPawn[129];
	private int column1 =252;
	private int column2 =338;
	private int column3 =424;
	private int row1 =252;
	private int row2 =338;
	private int row3 =424;
	private int[][] myBoard;
	private int[] myHome;
	private int[] myStart;
	private int[] myDice;
	private int x1= -1,x2 = -1,y1 = -1,y2 = -1,from =-1, to =-1;
	private GestureDetector myDetector =null;
	private boolean hasRolled =false;
	private Paint indicatorPaint = new Paint();
	private int myTurn;


	/**
	 * constructor
	 * 
	 * @param name
	 */
	public ParHumanPlayer(String name) {
		super(name);
		redPawnPaint.setColor(Color.RED);
		bluePawnPaint.setColor(Color.BLUE);
		greenPawnPaint.setColor(Color.GREEN);
		yellowPawnPaint.setColor(Color.YELLOW);
		blackPaint.setColor(Color.BLACK);
		whitePaint.setColor(Color.WHITE);
		transPaint.setARGB(0,255,255,255);
		paint[2] = redPawnPaint; 
		paint[1] = bluePawnPaint; 
		paint[3] = greenPawnPaint; 
		paint[0] = yellowPawnPaint; 
		paint[4] = transPaint;
		paint[5] = blackPaint;
		textPaint.setColor(Color.BLACK);
		textPaint.setTextSize(30);
		indicatorPaint.setColor(Color.MAGENTA);
	}

	/**
	 * setting the gui
	 */
	public void setAsGui(GameMainActivity activity) {

		myMainView = (ParSurfaceView)activity.findViewById(R.id.humanPlayerGUI); 
		// remember our activitiy
		myActivity = activity;

		// Load the layout resource for the new configuration
		activity.setContentView(R.layout.activity_main);

		// set the animator (us) for the animation surface
		myMainView = (ParSurfaceView)activity.findViewById(R.id.humanPlayerGUI); 
		myMainView.setAnimator(this);


		// "erase" the previous GUI's notion of what the screen size is
		fullSquare = -1;

		// if we have state, "simulate" that it just came from the game, and
		// hence draw it on the GUI
		if (myState != null) {
			receiveInfo(myState);
		}


		quitButton = (Button)myActivity.findViewById(R.id.buttonQuit);
		quitButton.setOnClickListener(this);
		rollButton = (Button)myActivity.findViewById(R.id.buttonRoll);
		rollButton.setOnClickListener(this);
		myMainView.setOnTouchListener(this);
		myDetector = new GestureDetector(myActivity, this);
		diceRolling = soundpool.load(myActivity, R.raw.diceroll, 1);
		quitSound = soundpool.load(myActivity, R.raw.quitsound, 1);

	}
	
/**
 * gets the state of the board at every tick
 */
	public void configBoard(){
		myBoard= myState.getBoard();
		myHome = myState.getHome();
		myStart = myState.getStart();
		myDice = myState.getDice();
		myTurn = myState.whoseTurn();
		
		if(myDice[1] == -1 && myDice[0]==-1){
			hasRolled =false;
		}
		else{
			hasRolled =true;
		}
		canMove = myState.legalMoves();
		
	}//configBoard

	/** 
	 * configure what the board looks like for the user
	 */
	public void tick(Canvas canvas) {
		if (myState == null) {
			return;
		}
		configBoard();
		drawBoard(canvas);
		playerNames(canvas);
		turnIndicator(canvas);
		pawnSetup(canvas);
		diceRoll(canvas);
		pawnDraw(canvas);
		
		//if human player can't move send the move for them
		if(hasRolled && (myStart[playerNum]+myHome[playerNum] ==4)&& myTurn == playerNum){
			boolean possibleMove =false;
			for(int i = 0; i<canMove.length; ++i){
				if(canMove[i]){
					possibleMove=true;
				}
			}
			if(!possibleMove){
				NoPossibleMoveAction mpma = new NoPossibleMoveAction(this);
				game.sendAction(mpma);
			}
		}


	}//tick

	/**
	 * This method will now draw in the pawns we want on the board
	 */
	public void pawnDraw(Canvas canvas){
		//this draws yellow pawns
		for(int i =0; i<75; ++i){
			if(myBoard[0][i]== 0){
				pawn[i+1].setColor(paint[0], paint[5]);
			}
		}
		//draws first part of blue board before overlap
		for(int i =0; i<51; ++i){
			if(myBoard[1][i]==1){
				pawn[i+18].setColor(paint[1], paint[5]);
			}
		}
		//draws second part of blue board after overlap
		for(int i = 51; i< 68; ++i){
			if(myBoard[1][i]==1){
				pawn[i-50].setColor(paint[1], paint[5]);
			}
		}
		//draws blue's home path
		for(int i = 68; i<=74; ++i){
			if(myBoard[1][i]==1){
				pawn[i+8].setColor(paint[1], paint[5]);
			}
		}
		//draws first part of red board before overlap
		for(int i =0; i<34; ++i){
			if(myBoard[2][i]==2){
				pawn[i+35].setColor(paint[2], paint[5]);
			}
		}
		//draws second part of red board after overlap
		for(int i = 34; i< 68; ++i){
			if(myBoard[2][i]==2){
				pawn[i-33].setColor(paint[2], paint[5]);
			}
		}
		//draws red's home path
		for(int i = 68; i<=74; ++i){
			if(myBoard[2][i]==2){
				pawn[i+15].setColor(paint[2], paint[5]);
			}
		}
		//draws first part of green board before overlap
		for(int i =0; i<17; ++i){
			if(myBoard[3][i]==3){
				pawn[i+52].setColor(paint[3], paint[5]);
			}
		}
		//draws second part of green board after overlap
		for(int i = 17; i< 68; ++i){
			if(myBoard[3][i]==3){
				pawn[i-16].setColor(paint[3], paint[5]);
			}
		}
		//draws green's home path
		for(int i = 68; i<=74; ++i){
			if(myBoard[3][i]==3){
				pawn[i+22].setColor(paint[3], paint[5]);
			}
		}

		pawnSwitch();

		for(int i = 1; i<=128; ++i){
			pawn[i].draw(canvas);
		}



	}//pawnDraw

	/**
	 * activates pawns at start and home positions
	 */
	public void pawnSwitch(){

		//yellow home
		switch(myHome[0]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[97].setColor(paint[0], paint[5]);
			break;
		case 2:
			pawn[97].setColor(paint[0], paint[5]);
			pawn[98].setColor(paint[0], paint[5]);
			break;
		case 3:
			pawn[97].setColor(paint[0], paint[5]);
			pawn[98].setColor(paint[0], paint[5]);
			pawn[99].setColor(paint[0], paint[5]);
			break;
		case 4:
			pawn[97].setColor(paint[0], paint[5]);
			pawn[98].setColor(paint[0], paint[5]);
			pawn[99].setColor(paint[0], paint[5]);
			pawn[100].setColor(paint[0], paint[5]);
			break;

		}
		
		//yellow start
		switch(myStart[0]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[125].setColor(paint[0], paint[5]);
			break;
		case 2:
			pawn[125].setColor(paint[0], paint[5]);
			pawn[126].setColor(paint[0], paint[5]);
			break;
		case 3:
			pawn[125].setColor(paint[0], paint[5]);
			pawn[126].setColor(paint[0], paint[5]);
			pawn[127].setColor(paint[0], paint[5]);
			break;
		case 4:
			pawn[125].setColor(paint[0], paint[5]);
			pawn[126].setColor(paint[0], paint[5]);
			pawn[127].setColor(paint[0], paint[5]);
			pawn[128].setColor(paint[0], paint[5]);
			break;
		}

		//blue home
		switch(myHome[1]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[101].setColor(paint[1], paint[5]);
			break;
		case 2:
			pawn[101].setColor(paint[1], paint[5]);
			pawn[102].setColor(paint[1], paint[5]);
			break;
		case 3:
			pawn[101].setColor(paint[1], paint[5]);
			pawn[102].setColor(paint[1], paint[5]);
			pawn[103].setColor(paint[1], paint[5]);
			break;
		case 4:
			pawn[101].setColor(paint[1], paint[5]);
			pawn[102].setColor(paint[1], paint[5]);
			pawn[103].setColor(paint[1], paint[5]);
			pawn[104].setColor(paint[1], paint[5]);
			break;

		}
		//blue start
		switch(myStart[1]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[117].setColor(paint[1], paint[5]);
			break;
		case 2:
			pawn[117].setColor(paint[1], paint[5]);
			pawn[118].setColor(paint[1], paint[5]);
			break;
		case 3:
			pawn[117].setColor(paint[1], paint[5]);
			pawn[118].setColor(paint[1], paint[5]);
			pawn[119].setColor(paint[1], paint[5]);
			break;
		case 4:
			pawn[117].setColor(paint[1], paint[5]);
			pawn[118].setColor(paint[1], paint[5]);
			pawn[119].setColor(paint[1], paint[5]);
			pawn[120].setColor(paint[1], paint[5]);
			break;
		}

		//red home
		switch(myHome[2]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[105].setColor(paint[2], paint[5]);
			break;
		case 2:
			pawn[105].setColor(paint[2], paint[5]);
			pawn[106].setColor(paint[2], paint[5]);
			break;
		case 3:
			pawn[105].setColor(paint[2], paint[5]);
			pawn[106].setColor(paint[2], paint[5]);
			pawn[107].setColor(paint[2], paint[5]);
			break;
		case 4:
			pawn[105].setColor(paint[2], paint[5]);
			pawn[106].setColor(paint[2], paint[5]);
			pawn[107].setColor(paint[2], paint[5]);
			pawn[108].setColor(paint[2], paint[5]);
			break;

		}
		//red start
		switch(myStart[2]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[113].setColor(paint[2], paint[5]);
			break;
		case 2:
			pawn[113].setColor(paint[2], paint[5]);
			pawn[114].setColor(paint[2], paint[5]);
			break;
		case 3:
			pawn[113].setColor(paint[2], paint[5]);
			pawn[114].setColor(paint[2], paint[5]);
			pawn[115].setColor(paint[2], paint[5]);
			break;
		case 4:
			pawn[113].setColor(paint[2], paint[5]);
			pawn[114].setColor(paint[2], paint[5]);
			pawn[115].setColor(paint[2], paint[5]);
			pawn[116].setColor(paint[2], paint[5]);
			break;
		}
		
		//green home
		switch(myHome[3]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[109].setColor(paint[3], paint[5]);
			break;
		case 2:
			pawn[109].setColor(paint[3], paint[5]);
			pawn[110].setColor(paint[3], paint[5]);
			break;
		case 3:
			pawn[109].setColor(paint[3], paint[5]);
			pawn[110].setColor(paint[3], paint[5]);
			pawn[111].setColor(paint[3], paint[5]);
			break;
		case 4:
			pawn[109].setColor(paint[3], paint[5]);
			pawn[110].setColor(paint[3], paint[5]);
			pawn[111].setColor(paint[3], paint[5]);
			pawn[112].setColor(paint[3], paint[5]);
			break;

		}
		//green start
		switch(myStart[3]){
		case 0:
			//do nothing
			break;
		case 1:
			pawn[121].setColor(paint[3], paint[5]);
			break;
		case 2:
			pawn[121].setColor(paint[3], paint[5]);
			pawn[122].setColor(paint[3], paint[5]);
			break;
		case 3:
			pawn[121].setColor(paint[3], paint[5]);
			pawn[122].setColor(paint[3], paint[5]);
			pawn[123].setColor(paint[3], paint[5]);
			break;
		case 4:
			pawn[121].setColor(paint[3], paint[5]);
			pawn[122].setColor(paint[3], paint[5]);
			pawn[123].setColor(paint[3], paint[5]);
			pawn[124].setColor(paint[3], paint[5]);
			break;
		}
	}//pawnSwitch



/**	
 * finds the box on the board that the user was touching
 * 
 * @param initx x position being found
 * @param inity y position being found
 * @return spot on board the user touched
 */
	public int findBox(int initx, int inity){
		//designates red start
		if(initx<=220 && inity<=220){
			return 99;
		}
		//blue start
		else if(initx>480 && initx<= 700 && inity <= 220){
			return 98;
		}
		//green start
		else if(initx<220 && inity>480 && inity <= 700){
			return 100;
		}
		//yellow start
		else if(initx>480 && initx<= 700 && inity>480 && inity <= 700){
			return 97;
		}
		//mutual trail #34
		else if(initx>306 && initx<= 392 && inity<=31){
			return 34;
		}
		//mutual trial #68
		else if(initx>306 && initx<= 392 && inity>666 && inity<=700){
			return 68;
		}
		//mt #51
		else if(initx<=31 && inity>306 && inity<= 392){
			return 51;
		}
		//mt #17
		else if(initx>666 && initx<=700 && inity>306 && inity<= 392){
			return 17;
		}
		//mt#42
		else if(initx>251 && initx<= 306 && inity>220 && inity<= 251 ){
			return 42;
		}
		//mt#26
		else if(initx>392 && initx<= 449 && inity>220 && inity<= 251 ){
			return 26;
		}
		//mt #60
		else if(initx>251 && initx<= 306 && inity>449 && inity<= 480 ){
			return 60;
		}
		//mt #8
		else if(initx>392 && initx<= 449 && inity>449 && inity<= 480 ){
			return 8;
		}
		//mt #43
		else if(initx>220 && initx<=251 && inity>251 && inity<=306){
			return 43;
		}
		//mt #59
		else if(initx>220 && initx<=251 && inity>392 && inity<=449){
			return 59;
		}
		//mt #25
		else if(initx>449 && initx<=480 && inity>251 && inity<=306){
			return 25;
		}
		//mt #9
		else if(initx>449 && initx<=480 && inity>392 && inity<=449){
			return 9;
		}
		//home 
		else if(initx>251 && initx<= 449 && inity>251 && inity<= 449){
			return 0;
		}
		//mt #35-41
		for(int i=0; i<7; ++i){
			if(initx>220 && initx<=306 && inity> (i*31) && inity<= ((i+1)*31)){
				return (35+i);
			}
		}
		//mt #27-33
		for(int i=0; i<7; ++i){
			if(initx>392 && initx<=480 && inity> (i*31) && inity<= ((i+1)*31)){
				return (33-i);
			}
		}
		//mt #1-7
		for(int i=0; i<7; ++i){
			if(initx>392 && initx<=480 && inity> (480 +(i*31)) && inity<= (480+((i+1)*31))){
				return (7-i);
			}
		}
		//mt #61-67
		for(int i=0; i<7; ++i){
			if(initx>220 && initx<=306 && inity> (480 +(i*31)) && inity<= (480+((i+1)*31))){
				return (61+i);
			}
		}
		//mt #44-50
		for(int i=0; i<7; ++i){
			if(initx> (i*31) && initx<= ((i+1)*31) && inity> 220 && inity<= 306){
				return (50-i);
			}
		}
		//mt #52-58
		for(int i=0; i<7; ++i){
			if(initx> (i*31) && initx<= ((i+1)*31) && inity> 392 && inity<= 480){
				return (52+i);
			}
		}
		//mt #18-24
		for(int i=0; i<7; ++i){
			if(initx> (480+(i*31)) && initx<= (480+((i+1)*31)) && inity> 220 && inity<= 306){
				return (24-i);
			}
		}
		//mt # 10-16
		for(int i=0; i<7; ++i){
			if(initx> (480+(i*31)) && initx<= (480+((i+1)*31)) && inity> 392 && inity<= 480){
				return (10+i);
			}
		}
		//red home trail
		for(int i=0; i<7; ++i){
			if(initx>306 && initx<=392 && inity> (31+ (i*31)) && inity<= (31+((i+1)*31))){
				return (83+i);
			}
		}
		//yellow home trail
		for(int i=0; i<7; ++i){
			if(initx>306 && initx<=392 && inity> (452+ (i*31)) && inity<= (452+((i+1)*31))){
				return (75-i);
			}
		}
		//green home trail
		for(int i=0; i<7; ++i){
			if(initx> (31+ (i*31)) && initx<= (31+((i+1)*31)) && inity>306 && inity<=392){
				return (90+i);
			}
		}
		//blue home trail
		for(int i=0; i<7; ++i){
			if(initx> (452+ (i*31)) && initx<= (452+((i+1)*31)) && inity>306 && inity<=392){
				return (82-i);
			}
		}

		//touch not in board
		return -1;

	}//findBox


	@Override
	public View getTopView() {
		return myActivity.findViewById(R.id.humanPlayerGUI);
	}

	@Override
	public void receiveInfo(GameInfo info) {
		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			//do nothing
		}
		else if (!(info instanceof ParState))
			// if we do not have a TTTState, ignore
			return;
		else {
			// update our 'state' variable with the new state
			this.myState = (ParState) info;
			Log.i("human player", "receiving");
		}
		myBoard= myState.getBoard();
		myDice = myState.getDice();
		myStart = myState.getStart();
		myHome = myState.getHome();
	}

	public void drawBoard(Canvas canvas){
		//Draw Board
		Bitmap board = BitmapFactory.decodeResource(myMainView.getResources(),R.drawable.parboard);
		Bitmap finalBoard = Bitmap.createScaledBitmap(board, 700, 700, true);
		canvas.drawBitmap(finalBoard, 0, 0, new Paint());

	}

	/**
	 * paint all possible positions of pawns onto the board and sets them invisible
	 * 
	 * @param canvas
	 */
	public void pawnSetup(Canvas canvas){
		x = 80;
		y = 70;

		//set up instance of ParPawn
		for(int i = 1; i<=128; ++i){
			pawn[i]=null;
		}
		//Mt 1-8 
		for(int i=0; i<8; ++i){
			pawn[i+1]= new ParPawn(column3, 700-(21 +(31*i)),paint[4], paint[4]);
		}
		//mt 9-16
		for(int i=0; i<8; ++i){
			pawn[16-i]= new ParPawn(700-(21 +(31*i)), row3,paint[4], paint[4]);
		}
		//MT 17
		pawn[17] = new ParPawn(700-21,row2,paint[4], paint[4]);
		//mt 18-25
		for(int i=0; i<8; ++i){
			pawn[18+i]= new ParPawn(700-(21 +(31*i)), row1,paint[4], paint[4]);
		}
		//mt 26-33
		for(int i=0; i<8; ++i){
			pawn[33-i]= new ParPawn(column3, 14 +(31*i),paint[4], paint[4]);
		}
		//mt 34
		pawn[34] = new ParPawn(column2, 14, paint[4], paint[4]);
		//MT 35-41 red1-7
		for (int i = 0; i < 7; ++i){
			pawn[i+35]= new ParPawn(column1, 14+(i*31), paint[4], paint[4]);
		}
		//MT 42 red8
		pawn[42]= new ParPawn(column1, 7*31+14, paint[4], paint[4]);
		//MT 43 red9
		pawn[43]= new ParPawn(7*31+14, row1, paint[4], paint[4]);
		//MT 44-50 red10-16
		for (int i = 0; i < 7; ++i){
			pawn[50-i]= new ParPawn((i*31)+14,row1, paint[4], paint[4]);
		}
		//Mt51 red 17
		pawn[51]= new ParPawn(14,row2, paint[4], paint[4]);
		//mt 52-59
		for (int i = 0; i < 8; ++i){
			pawn[52+i]= new ParPawn(14+(i*31), row3 , paint[4], paint[4]);
		}
		//mt 60-67
		for(int i=0; i<8; ++i){
			pawn[67-i]= new ParPawn(column1, 700-(21 +(31*i)),paint[4], paint[4]);
		}
		//mt 68
		pawn[68] = new ParPawn(column2, 700-21, paint[4], paint[4]);
		//yellow home trail
		for(int i=0; i<7; ++i){
			pawn[69+i]= new ParPawn(column2, 700-(52 +(31*i)),paint[4], paint[4]);
		}
		//blue home trail
		for(int i=0; i<7; ++i){
			pawn[76+i]= new ParPawn(700-(52 +(31*i)),row2 ,paint[4], paint[4]);
		}
		//yellow home trail
		for(int i=0; i<7; ++i){
			pawn[83+i]= new ParPawn(column2,52 +(31*i),paint[4], paint[4]);
		}
		//blue home trail
		for(int i=0; i<7; ++i){
			pawn[90+i]= new ParPawn(52 +(31*i),row2 ,paint[4], paint[4]);
		}
		//yellow home spaces
		pawn[97] = new ParPawn(column2, 432, paint[4], paint[4]);
		pawn[98] = new ParPawn(column2-30,432, paint[4], paint[4]);
		pawn[99] = new ParPawn(column2+30, 432, paint[4], paint[4]);
		pawn[100] = new ParPawn(column2, 432-30, paint[4], paint[4]);
		//blue home spaces
		pawn[101] =new ParPawn(432,row2 , paint[4], paint[4]);
		pawn[102] =new ParPawn(432,row2-30 , paint[4], paint[4]);
		pawn[103] =new ParPawn(432,row2+30 , paint[4], paint[4]);
		pawn[104] =new ParPawn(432-30,row2 , paint[4], paint[4]);
		//red home spaces
		pawn[105] = new ParPawn(column2, 268, paint[4], paint[4]);
		pawn[106] = new ParPawn(column2-30, 268, paint[4], paint[4]);
		pawn[107] = new ParPawn(column2+30, 268, paint[4], paint[4]);
		pawn[108] = new ParPawn(column2, 268+30, paint[4], paint[4]);
		//green home spaces
		pawn[109] = new ParPawn(268,row2 , paint[4], paint[4]);
		pawn[110] = new ParPawn(268,row2-30 , paint[4], paint[4]);
		pawn[111] = new ParPawn(268,row2+30 , paint[4], paint[4]);
		pawn[112] = new ParPawn(268+30,row2 , paint[4], paint[4]);

		//red starting spaces
		for (int i = 0; i < 4; ++i){
			pawn[113+i] = new ParPawn(x, y, paint[4], paint[4]);
			x = x + 60;
			if (i == 1){
				y = y + 60; 
				x = x - 120;
			}
		}
		x = 560;
		y = 70;
		//blue starting spaces
		for (int i = 0; i < 4; ++i){
			pawn[117+i] = new ParPawn(x, y, paint[4], paint[4]);
			x = x + 60;
			if (i == 1){
				y = y + 60; 
				x = x - 120;
			}
		}
		x = 80;
		y = 550;
		//green starting spaces
		for (int i = 0; i < 4; ++i){
			pawn[121+i] = new ParPawn(x, y, paint[4], paint[4]);
			x = x + 60;
			if (i == 1){
				y = y + 60; 
				x = x - 120;
			}
		}
		x = 560;
		y = 560;
		//yellow starting spaces
		for (int i = 0; i < 4; ++i){
			pawn[125+i] = new ParPawn(x, y, paint[4], paint[4]);
			x = x + 60;
			if (i == 1){
				y = y + 60; 
				x = x - 120;
			}
		}

		//draw every transparent spot
		for(int i = 1; i<=128; ++i){
			pawn[i].draw(canvas);
		}
	}//pawnSetup


	/**
	 * drawing the dice that were rolled
	 * @param canvas
	 */
	public void diceRoll(Canvas canvas){

		//Dice 1 
		canvas.drawRect(750, 250, 950, 450, blackPaint);
		canvas.drawRect(751, 251, 949, 449, whitePaint);
		//Dice 2
		canvas.drawRect(1000, 250, 1200, 450, blackPaint);
		canvas.drawRect(1001, 251, 1199, 449, whitePaint);

		//Dice 1
		if(myState.getDice()[0] == 1){
			//One
			canvas.drawCircle(850, 350, 15, blackPaint); 
		}
		else if(myState.getDice()[0] == 2){
			//Two
			canvas.drawCircle(800, 300, 15, blackPaint);
			canvas.drawCircle(900, 400, 15, blackPaint);
		}
		else if(myState.getDice()[0] == 3){
			//Three
			canvas.drawCircle(850, 350, 15, blackPaint);
			canvas.drawCircle(800, 300, 15, blackPaint);
			canvas.drawCircle(900, 400, 15, blackPaint);
		}
		else if(myState.getDice()[0] == 4){
			//Four
			canvas.drawCircle(800, 300, 15, blackPaint);
			canvas.drawCircle(900, 400, 15, blackPaint);
			canvas.drawCircle(900, 300, 15, blackPaint);
			canvas.drawCircle(800, 400, 15, blackPaint);
		}
		else if(myState.getDice()[0] == 5){
			//Five
			canvas.drawCircle(850, 350, 15, blackPaint);
			canvas.drawCircle(800, 300, 15, blackPaint);
			canvas.drawCircle(900, 300, 15, blackPaint);
			canvas.drawCircle(800, 400, 15, blackPaint);
			canvas.drawCircle(900, 400, 15, blackPaint);
		}
		else if(myState.getDice()[0] == 6){
			//Six
			canvas.drawCircle(800, 300, 15, blackPaint);
			canvas.drawCircle(800, 400, 15, blackPaint);
			canvas.drawCircle(900, 400, 15, blackPaint);
			canvas.drawCircle(900, 300, 15, blackPaint);
			canvas.drawCircle(800, 350, 15, blackPaint);
			canvas.drawCircle(900, 350, 15, blackPaint);
		}

		if(myState.getDice()[1] == 1){
			canvas.drawCircle(1100, 350, 15, blackPaint); 
		}
		else if(myState.getDice()[1] == 2){
			canvas.drawCircle(1050, 300, 15, blackPaint);
			canvas.drawCircle(1150, 400, 15, blackPaint);
		}
		else if(myState.getDice()[1] == 3){
			canvas.drawCircle(1100, 350, 15, blackPaint);
			canvas.drawCircle(1050, 300, 15, blackPaint);
			canvas.drawCircle(1150, 400, 15, blackPaint);
		}
		else if(myState.getDice()[1] == 4){
			canvas.drawCircle(1050, 300, 15, blackPaint);
			canvas.drawCircle(1150, 400, 15, blackPaint);
			canvas.drawCircle(1150, 300, 15, blackPaint);
			canvas.drawCircle(1050, 400, 15, blackPaint);
		}
		else if(myState.getDice()[1] == 5){
			canvas.drawCircle(1100, 350, 15, blackPaint);
			canvas.drawCircle(1050, 300, 15, blackPaint);
			canvas.drawCircle(1150, 300, 15, blackPaint);
			canvas.drawCircle(1050, 400, 15, blackPaint);
			canvas.drawCircle(1150, 400, 15, blackPaint);
		}
		else if(myState.getDice()[1] == 6){
			canvas.drawCircle(1050, 300, 15, blackPaint);
			canvas.drawCircle(1050, 400, 15, blackPaint);
			canvas.drawCircle(1150, 400, 15, blackPaint);
			canvas.drawCircle(1150, 300, 15, blackPaint);
			canvas.drawCircle(1150, 350, 15, blackPaint);
			canvas.drawCircle(1050, 350, 15, blackPaint);
		}

	}//diceRoll

	/**
	 * draws player names
	 * @param canvas
	 */
	public void playerNames(Canvas canvas){
		canvas.drawText(allPlayerNames[2], 50, 25, textPaint);
		canvas.drawText(allPlayerNames[1], 540, 25, textPaint);
		canvas.drawText(allPlayerNames[3], 50, 500, textPaint);
		canvas.drawText(allPlayerNames[0], 540, 500, textPaint);

	}


	/**
	 * button event actions
	 */
	public void onClick(View v) {
		if(v == rollButton && myState.whoseTurn() == playerNum && myState.getDice()[0] == -1 && myState.getDice()[1] == -1){
			ParRollAction roll = new ParRollAction(this);
			soundpool.play(diceRolling, 1, 1, 0, 0, 1);
			game.sendAction(roll);
		}
		
		else if(v == quitButton){
			soundpool.play(quitSound, 1, 1, 0, 0, 1);
			quitting.show(myActivity.getFragmentManager(), "Qutting?");
		}

	}


	
	public int interval() {
		return 50;
	}


	
	public int backgroundColor() {

		return Color.BLUE;
	}


	
	public boolean doPause() {

		return false;
	}


	
	public boolean doQuit() {

		return false;
	}

	/**
	 * pass touch events onto gesture listener
	 */
	public void onTouch(MotionEvent event) {

		if( myState.whoseTurn() == playerNum && to != -1 && from!= -1 && (myDice[1]!=-1|| myDice[0]!=-1) ){
			ParPawnMovement ppma = new ParPawnMovement(this, from, to, true);
			game.sendAction(ppma);
			to=-1;
			from=-1;
		}

	}


	/**
	 * draws purple indicator to show whose turn
	 * @param canvas
	 */
	public void turnIndicator(Canvas canvas){
		switch(myState.getWhoseMove()){
		//yellow player
		case 0 :
			canvas.drawRect(490, 490,510,510, indicatorPaint);
			break;
			//blue player
		case 1:
			canvas.drawRect(490, 10,510,30, indicatorPaint);
			break;
			//red player
		case 2:
			canvas.drawRect(10,10,30,30, indicatorPaint );
			break;
			//green player
		case 3:
			canvas.drawRect(10,490,30,510,indicatorPaint);
			break;
		}
	}

	/**
	 * pass touch events onto gesture listener
	 */
	public boolean onTouch(View view, MotionEvent event) {
		boolean gestureResult =myDetector.onTouchEvent(event);
		if(event.getAction() == event.ACTION_UP){
			if( myState.whoseTurn() == playerNum && to != -1 && from!= -1 && (myDice[1]!=-1|| myDice[0]!=-1) ){
				ParPawnMovement move = new ParPawnMovement(this, from, to, true);
				game.sendAction(move);
				to=-1;
				from=-1;
			}
		}
		return gestureResult;

	}




	/**
	 * drag and drop listener
	 */
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX,
			float distanceY) {

		x1 =(int) e1.getX();
		y1 =(int) e1.getY();

		from=findBox(x1,y1);

		x2= (int) e2.getX();
		y2 = (int) e2.getY();

		to = findBox(x2,y2);


		return true;

	}//omScroll




	
	public boolean onDown(MotionEvent e1) {

		return true;
	}




	
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
			float velocityY) {

		return false;
	}




	
	public void onLongPress(MotionEvent e) {
		//do nothing

	}




	
	public void onShowPress(MotionEvent e) {
		//do nothing

	}




	
	public boolean onSingleTapUp(MotionEvent e) {

		return false;
	}


}
