package edu.up.cs301.Par;

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
 * @version November 2013
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.hardware.SensorEvent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnDragListener;
import android.view.View.OnTouchListener;
import android.widget.Button;


public class ParHumanPlayer extends GameHumanPlayer implements OnDragListener, OnClickListener{

	private Activity myActivity;

	private ParSurfaceView myMainView = null;
	private ParState state;
	protected float fullSquare;
	private Paint blackPaint = new Paint();
	private Paint whitePaint = new Paint();
	private Button quitButton;
	private Button rollButton;
	private NotYourTurnDialog nope;
	private QuitAction quitting = new QuitAction();;
	public SoundPool soundpool = new SoundPool(5, AudioManager.STREAM_MUSIC, 0); //Setup for SoundPool
	public int diceRolling; //Dice Rolling Sound
	public int quitSound; //Quit Warning Sound
	//private ParPawn[][] pawn = new ParPawn[][];


	/**
	 * constructor
	 * 
	 * @param name
	 */
	public ParHumanPlayer(String name) {
		super(name);

	}


	@Override
	public void setAsGui(GameMainActivity activity) {
		

		myMainView = (ParSurfaceView)activity.findViewById(R.id.humanPlayerGUI); 
		// remember our activitiy
		myActivity = activity;

		// Load the layout resource for the new configuration
		activity.setContentView(R.layout.activity_main);

		// set the animator (us) for the animation surface
		myMainView = (ParSurfaceView)activity.findViewById(R.id.humanPlayerGUI); 
		myMainView.animate();

		// "erase" the previous GUI's notion of what the screen size is
		fullSquare = -1;

		// if we have state, "simulate" that it just came from the game, and
		// hence draw it on the GUI
		if (state != null) {
			receiveInfo(state);
		}
		quitButton = (Button)myActivity.findViewById(R.id.buttonQuit);
		quitButton.setOnClickListener(this);
		rollButton = (Button)myActivity.findViewById(R.id.buttonRoll);
		rollButton.setOnClickListener(this);
		diceRolling = soundpool.load(myActivity, R.raw.diceroll, 1);
		quitSound = soundpool.load(myActivity, R.raw.quitsound, 1);
		myMainView.setOnDragListener(this);

	}



	@Override
	public View getTopView() {
		// TODO Auto-generated method stub
		return myActivity.findViewById(R.id.humanPlayerGUI);
	}

	@Override
	public void receiveInfo(GameInfo info) {
		if (info instanceof IllegalMoveInfo || info instanceof NotYourTurnInfo) {
			nope = new NotYourTurnDialog();
		}
		else if (!(info instanceof ParState))
			// if we do not have a TTTState, ignore
			return;
		else {
			// update our 'state' variable with the new state
			this.state = (ParState) info;
			Log.i("human player", "receiving");
		}
	}
	
	//drawing the new pawns to the game board
//	public void drawBoard(Canvas canvas){
//		int[][] board= state.getBoard();
//		int[] start = state.getStart();
//		for(int i = 1; i<=8; ++i){
//			if(board[0][i]==1){
//				
//			}
//		}
//		//draws the pawns at the start
//		for (int p = 0; p < 4; ++p){
//			for (int i = 0; i < start[p]; ++i){
//				pawn[p][i].draw(canvas);
//			}
//		}
//		
//	}
	
//	public void diceRoll(Canvas canvas){
//		blackPaint.setColor(Color.BLACK);
//		whitePaint.setColor(Color.WHITE);
//		
//		//Dice 1 
//		canvas.drawRect(750, 250, 950, 450, blackPaint);
//		canvas.drawRect(751, 251, 949, 449, whitePaint);
//		//Dice 2
//		canvas.drawRect(1000, 250, 1200, 450, blackPaint);
//		canvas.drawRect(1001, 251, 1199, 449, whitePaint);
//		
//		//Dice has been used
//		if(state.getDice()[0] == -1){
//			//do nothing			
//		}
//		
//		//Dice 1
//		else if(state.getDice()[0] == 1){
//			//One
//			canvas.drawCircle(850, 350, 15, blackPaint); 
//		}
//		else if(state.getDice()[0] == 2){
//			//Two
//			canvas.drawCircle(800, 300, 15, blackPaint);
//			canvas.drawCircle(900, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[0] == 3){
//			//Three
//			canvas.drawCircle(850, 350, 15, blackPaint);
//			canvas.drawCircle(800, 300, 15, blackPaint);
//			canvas.drawCircle(900, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[0] == 4){
//			//Four
//			canvas.drawCircle(800, 300, 15, blackPaint);
//			canvas.drawCircle(900, 400, 15, blackPaint);
//			canvas.drawCircle(900, 300, 15, blackPaint);
//			canvas.drawCircle(800, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[0] == 5){
//			//Five
//			canvas.drawCircle(850, 350, 15, blackPaint);
//			canvas.drawCircle(800, 300, 15, blackPaint);
//			canvas.drawCircle(900, 300, 15, blackPaint);
//			canvas.drawCircle(800, 400, 15, blackPaint);
//			canvas.drawCircle(900, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[0] == 6){
//			//Six
//			canvas.drawCircle(800, 300, 15, blackPaint);
//			canvas.drawCircle(800, 400, 15, blackPaint);
//			canvas.drawCircle(900, 400, 15, blackPaint);
//			canvas.drawCircle(900, 300, 15, blackPaint);
//			canvas.drawCircle(800, 350, 15, blackPaint);
//			canvas.drawCircle(900, 350, 15, blackPaint);
//		}
//		
//		//Dice has been used
//		if(state.getDice()[1] == -1){
//			//do nothing			
//		}
//		else if(state.getDice()[1] == 1){
//			canvas.drawCircle(1100, 350, 15, blackPaint); 
//		}
//		else if(state.getDice()[1] == 2){
//			canvas.drawCircle(1050, 300, 15, blackPaint);
//			canvas.drawCircle(1150, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[1] == 3){
//			canvas.drawCircle(1100, 350, 15, blackPaint);
//			canvas.drawCircle(1050, 300, 15, blackPaint);
//			canvas.drawCircle(1150, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[1] == 4){
//			canvas.drawCircle(1050, 300, 15, blackPaint);
//			canvas.drawCircle(1150, 400, 15, blackPaint);
//			canvas.drawCircle(1150, 300, 15, blackPaint);
//			canvas.drawCircle(1050, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[1] == 5){
//			canvas.drawCircle(1100, 350, 15, blackPaint);
//			canvas.drawCircle(1050, 300, 15, blackPaint);
//			canvas.drawCircle(1150, 300, 15, blackPaint);
//			canvas.drawCircle(1050, 400, 15, blackPaint);
//			canvas.drawCircle(1150, 400, 15, blackPaint);
//		}
//		else if(state.getDice()[1] == 6){
//			canvas.drawCircle(1050, 300, 15, blackPaint);
//			canvas.drawCircle(1050, 400, 15, blackPaint);
//			canvas.drawCircle(1150, 400, 15, blackPaint);
//			canvas.drawCircle(900, 300, 15, blackPaint);
//			canvas.drawCircle(1150, 350, 15, blackPaint);
//			canvas.drawCircle(900, 350, 15, blackPaint);
//		}
//		
//}
	
	public int findBox(int x, int y){
		//designates red start
		if(x<=220 && y<=220){
			return 99;
		}
		//blue start
		else if(x>480 && x<= 700 && y <= 220){
			return 98;
		}
		//green start
		else if(x>220 && y>480 && y <= 700){
			return 100;
		}
		//yellow start
		else if(x>480 && x<= 700 && y>480 && y <= 700){
			return 97;
		}
		//mutual trail #34
		else if(x>306 && x<= 392 && y<=31){
			return 34;
		}
		//mutual trial #68
		else if(x>306 && x<= 392 && y>666 && y<=700){
			return 68;
		}
		//mt #51
		else if(x<=31 && y>306 && y<= 392){
			return 51;
		}
		//mt #17
		else if(x>666 && x<=700 && y>306 && y<= 392){
			return 17;
		}
		//mt#42
		else if(x>251 && x<= 306 && y>220 && y<= 251 ){
			return 42;
		}
		//mt#26
		else if(x>392 && x<= 449 && y>220 && y<= 251 ){
			return 26;
		}
		//mt #60
		else if(x>251 && x<= 306 && y>449 && y<= 480 ){
			return 60;
		}
		//mt #8
		else if(x>392 && x<= 449 && y>449 && y<= 480 ){
			return 8;
		}
		//mt #43
		else if(x>220 && x<=251 && y>251 && y<=306){
			return 43;
		}
		//mt #59
		else if(x>220 && x<=251 && y>392 && y<=449){
			return 59;
		}
		//mt #25
		else if(x>449 && x<=480 && y>251 && y<=306){
			return 25;
		}
		//mt #9
		else if(x>449 && x<=480 && y>392 && y<=449){
			return 9;
		}
		//home 
		else if(x>251 && x<= 449 && y>251 && y<= 449){
			return 0;
		}
		//mt #35-41
		for(int i=0; i<7; ++i){
			if(x>220 && x<=306 && y> (i*31) && y<= ((i+1)*31)){
				return (35+i);
			}
		}
		//mt #27-33
		for(int i=0; i<7; ++i){
			if(x>392 && x<=480 && y> (i*31) && y<= ((i+1)*31)){
				return (33-i);
			}
		}
		//mt #1-7
		for(int i=0; i<7; ++i){
			if(x>392 && x<=480 && y> (480 +(i*31)) && y<= (480+((i+1)*31))){
				return (7-i);
			}
		}
		//mt #61-67
		for(int i=0; i<7; ++i){
			if(x>220 && x<=306 && y> (480 +(i*31)) && y<= (480+((i+1)*31))){
				return (61+i);
			}
		}
		//mt #44-50
		for(int i=0; i<7; ++i){
			if(x> (i*31) && x<= ((i+1)*31) && y> 220 && y<= 306){
				return (50-i);
			}
		}
		//mt #52-58
		for(int i=0; i<7; ++i){
			if(x> (i*31) && x<= ((i+1)*31) && y> 392 && y<= 480){
				return (52+i);
			}
		}
		//mt #18-24
		for(int i=0; i<7; ++i){
			if(x> (480+(i*31)) && x<= (480+((i+1)*31)) && y> 220 && y<= 306){
				return (24-i);
			}
		}
		//mt # 10-16
		for(int i=0; i<7; ++i){
			if(x> (480+(i*31)) && x<= (480+((i+1)*31)) && y> 392 && y<= 480){
				return (10+i);
			}
		}
		//red home trail
		for(int i=0; i<7; ++i){
			if(x>306 && x<=392 && y> (31+ (i*31)) && y<= (31+((i+1)*31))){
				return (83+i);
			}
		}
		//yellow home trail
		for(int i=0; i<7; ++i){
			if(x>306 && x<=392 && y> (452+ (i*31)) && y<= (452+((i+1)*31))){
				return (75-i);
			}
		}
		//green home trail
		for(int i=0; i<7; ++i){
			if(x> (31+ (i*31)) && x<= (31+((i+1)*31)) && y>306 && y<=392){
				return (90+i);
			}
		}
		//blue home trail
		for(int i=0; i<7; ++i){
			if(x> (452+ (i*31)) && x<= (452+((i+1)*31)) && y>306 && y<=392){
				return (82-i);
			}
		}

		return -1;

	}

	@Override
	public void onClick(View v) {
		if(v == rollButton){
			ParRollAction action = new ParRollAction(this);
			soundpool.play(diceRolling, 1, 1, 0, 0, 1);
			game.sendAction(action);
			
		}
		else if(v == quitButton){
			soundpool.play(quitSound, 1, 1, 0, 0, 1);
			quitting.show(myActivity.getFragmentManager(), "Qutting?");
		}

	}


	@Override
	public boolean onDrag(View v, DragEvent event) {
		int x1,x2,y1,y2,from =-1, to =-1;
		int action = event.getAction();
		switch (action) {
		case DragEvent.ACTION_DRAG_ENTERED:
			x1= (int) event.getX();
			y1= (int) event.getY();
			from=findBox(x1,y1);
			break;
		case DragEvent.ACTION_DROP:
			x2= (int) event.getX();
			y2= (int) event.getY();
			to = findBox(x2,y2);
			break;
		}
		ParPawnMovement ppma = new ParPawnMovement(this, from, to);
		game.sendAction(ppma);
		return true;
	}

}
