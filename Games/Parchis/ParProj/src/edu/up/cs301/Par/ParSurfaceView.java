package edu.up.cs301.Par;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.R.drawable;
import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import android.content.Context;

/**
 * 
 * @author Anthony Prom
 * @author Alex Bowns
 * @version November 2013
 */

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.View;


public class ParSurfaceView extends SurfaceView {

	private Paint redPawnPaint = new Paint();
	private Paint bluePawnPaint = new Paint();
	private Paint greenPawnPaint = new Paint();
	private Paint yellowPawnPaint = new Paint();
	private ParPawn[][] pawn = new ParPawn[4][4];
	private Paint[] paint = new Paint[4];
	int x,y;


	Paint xPaint = new Paint();

	public ParSurfaceView(Context context) {
		super(context);
		startup();

	}

	public ParSurfaceView(Context context, AttributeSet set) {
		super(context, set);
		startup();

	}

	public ParSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		startup();

	}
	public void startup(){
		setWillNotDraw(false);
		paint[0] = redPawnPaint; 
		paint[1] = bluePawnPaint; 
		paint[2] = greenPawnPaint; 
		paint[3] = yellowPawnPaint; 
		pawnSetup();

	}


	//this method is used to give all of the coordinates for each pawn in associated with each player
	public void pawnSetup(){
		x = 80;
		y = 70;
		for (int p = 0; p < paint.length; ++p){
			if (p == 3){
				x = 560;
				y = 550;
			}
			if (p == 2){
				x = 80;
				y = 550;
			}
			if (p == 1){
				x = 560;
				y = 80;
			}
			if (p == 0) {
				x = 80;
				y = 70;
			}
			for (int i = 0; i < paint.length; ++i){
				pawn[p][i] = new ParPawn(x, y, paint[p]);
				x = x + 60;
				if (i == 1){
					y = y + 60; 
					x = x - 120;
				}
			}
		}
	}



	/**
	 * Need to Implement
	 */
	//protected Component createApplComponent(){

	//}

	/**
	 * Need to Implement
	 */
	//protected void moreActionPerformed(ActionEvent ae){

	//}

	/**
	 * Need to Implement
	 */
	protected void requestMove(){

	}

	/**
	 * Need to Implement
	 */
	protected void stateChanged(ParState state){

	}



	@Override
	public void draw(Canvas canvas) {
		redPawnPaint.setColor(Color.RED);
		bluePawnPaint.setColor(Color.BLUE);
		greenPawnPaint.setColor(Color.GREEN);
		yellowPawnPaint.setColor(Color.YELLOW);

		//Draw Board
		Bitmap board = BitmapFactory.decodeResource(getResources(),R.drawable.parboard);
		Bitmap finalBoard = Bitmap.createScaledBitmap(board, 700, 700, true);
		canvas.drawBitmap(finalBoard, 0, 0, new Paint());



		//		//Initialize Dice
		//		Bitmap d1 = BitmapFactory.decodeResource(getResources(),R.drawable.one);
		//		Bitmap d2 = BitmapFactory.decodeResource(getResources(),R.drawable.two);
		//		Bitmap d3 = BitmapFactory.decodeResource(getResources(),R.drawable.three);
		//		Bitmap d4 = BitmapFactory.decodeResource(getResources(),R.drawable.four);
		//		Bitmap d5 = BitmapFactory.decodeResource(getResources(),R.drawable.five);
		//		Bitmap d6 = BitmapFactory.decodeResource(getResources(),R.drawable.six);
		//		

		//Draw 1st Dice
		//		switch(0){
		//			case 0:
		//				canvas.drawBitmap(d1, 750, 250, new Paint());
		//				break;
		//			case 1:
		//				canvas.drawBitmap(d2, 750, 250, new Paint());
		//				break;
		//			case 2:
		//				canvas.drawBitmap(d3, 750, 250, new Paint());
		//				break;
		//			case 3:
		//				canvas.drawBitmap(d4, 750, 250, new Paint());
		//				break;
		//			case 4:	
		//				canvas.drawBitmap(d5, 750, 250, new Paint());
		//				break;
		//			case 5:
		//				canvas.drawBitmap(d6, 750, 250, new Paint());
		//				break;
		//		}
		//		
		//		//Draw 2nd Dice
		//		switch(0){
		//			case 0:
		//				canvas.drawBitmap(d1, 1000, 250, new Paint());
		//				break;
		//			case 1:
		//				canvas.drawBitmap(d2, 1000, 250, new Paint());
		//				break;
		//			case 2:	
		//				canvas.drawBitmap(d3, 1000, 250, new Paint());
		//				break;
		//			case 3:	
		//				canvas.drawBitmap(d4, 1000, 250, new Paint());
		//				break;
		//			case 4:
		//				canvas.drawBitmap(d5, 1000, 250, new Paint());
		//				break;
		//			case 5:	
		//				canvas.drawBitmap(d6, 1000, 250, new Paint());
		//				break;
		//		}


		//To Implement Later
		//X across 1st Dice
		//		xPaint.setColor(Color.RED);
		//		canvas.drawLine(750, 250, 950, 450, xPaint);
		//		canvas.drawLine(750, 440, 950, 240, xPaint);
		//		
		//To Implement Later
		//X across 2nd Dice
		//		xPaint.setColor(Color.RED);
		//		canvas.drawLine(1000, 250, 1200, 450, xPaint);
		//		canvas.drawLine(1000, 440, 1200, 240, xPaint);
		//	

		//drawing the pawns
		for (int p = 0; p < 4; ++p){
			for (int i = 0; i < 4; ++i){
				pawn[p][i].draw(canvas);
			}
		}	

		//set up instance of ParPawn
		ParPawn[] pPawn = new ParPawn[76];
		//MT 35-41 red1-7
		for (int i = 0; i < 7; ++i){
			pPawn[i]= new ParPawn(252, 9+i*31, redPawnPaint);
		}//MT 42 red8
		pPawn[8]= new ParPawn(252, 7*31+9, redPawnPaint);
		//MT 43 red9
		pPawn[9]= new ParPawn(7*31+9,252, redPawnPaint);
		//MT 44-50 red10-16
		for (int i = 0; i < 7; ++i){
				pPawn[i+10]= new ParPawn(i*31+9,252, redPawnPaint);
		}
		//Mt51 red 17
		pPawn[17]= new ParPawn(9,344, redPawnPaint);
		invalidate();
	}





}
