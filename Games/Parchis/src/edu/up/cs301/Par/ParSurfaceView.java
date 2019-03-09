package edu.up.cs301.Par;

import edu.up.cs301.animation.Animator;
import android.view.View.OnTouchListener;
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
import android.view.DragEvent;
import android.view.GestureDetector;

import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnDragListener;

public class ParSurfaceView extends SurfaceView implements OnTouchListener {



	private int x,y;

	private Animator animator; // our animator
	private AnimationThread animationThread = null; // thread to generate ticks
	// instance variables
	private Paint backgroundPaint = new Paint(); // painter for painting background
	private int flashCount; // counts down ticks for background-flash
	private Paint flashPaint; // has color for background flash




	Paint xPaint = new Paint();

	public ParSurfaceView(Context context) {
		super(context);
		init();


	}

	public ParSurfaceView(Context context, AttributeSet set) {
		super(context, set);
		init();

	}

	public ParSurfaceView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();

	}

	private void init() {

		// Tell the OS that *yes* I will draw stuff
		setWillNotDraw(false);

		backgroundPaint.setColor(Color.WHITE);

		// initialize the animator instance variable animator-creation method
		animator = createAnimator();

		if (animator != null) {
			startAnimation();
		}
		this.setOnTouchListener(this);


		// initialize the animator instance variable animator-creation method

	}// init

	private void startAnimation() {

		// create and start a thread to generate "ticks" for the animator
		// with the frequency that it desires
		this.animationThread = new AnimationThread(getHolder());
		animationThread.start();

	}
	
	public void flash(int color, int mili){
		animationThread.flash(color, mili);
	}

	/**
	 * Creates the animator for the object. If this method returns null, then it will
	 * be necessary to invoke the 'setAnimator' method before the animation can start.
	 * @return the animator
	 */
	public Animator createAnimator() {
		return null;
	}

	/**
	 * Sets and starts the animator for the AnimationSurface if it does not already
	 * have an animator.
	 * 
	 * @param animator the animator to use.
	 */
	public void setAnimator(Animator animator) {
		if (this.animator == null) {
			// set the animator
			this.animator = animator;
		}
		if (this.animator != null) {
			// start the animator
			startAnimation();
		}
	}


	/**
	 * Thread subclass to control the game loop
	 * 
	 * Code adapted from Android:How to Program by Deitel, et.al., first edition
	 * copyright (C)2013.
	 * 
	 */
	private class AnimationThread extends Thread {

		// a reference to a SurfaveView's holder. This is used to "lock" the
		// canvas when we want to write to it
		private SurfaceHolder surfaceHolder;

		// controls animation stop/go based upon instructions from the Animator
		private boolean threadIsRunning = true;

		/** ctor inits instance variables */
		public AnimationThread(SurfaceHolder holder) {
			surfaceHolder = holder;
			setName("AnimationThread");
		}

		/**
		 * causes this thread to pause for a given interval.
		 * 
		 * @param interval
		 *            duration in milliseconds
		 */
		private void sleep(int interval) {
			try {
				Thread.sleep(interval); // use sleep to avoid busy wait
			} catch (InterruptedException ie) {
				// don't care if we're interrupted
			}
		}// sleep
		
		public void flash(int color, int mili){
			flashCount=mili;
			flashPaint= new Paint();
			flashPaint.setColor(color);
		}

		/**
		 * Causes the background to be changed ("flash") for the given period
		 * of time.
		 * 
		 * @param color
		 * 			the color to flash
		 * @param millis
		 * 			the number of milliseconds for this the flash should occur
		 */


		/**
		 * This is the main animation loop. It calls the Animator's draw()
		 * method at regular intervals to creation the animation.
		 */
		@Override
		public void run() {
			Canvas canvas = null;
			long lastTickEnded = 0; // when the last tick ended

			//			draw(canvas);
			//			animator.tick(canvas);

			while (threadIsRunning) {

				// stop if the animator asks for it
				if (animator.doQuit())
					break;

				// pause while the animator wishes it
				while (animator.doPause()) {
					sleep(animator.interval());
				}// while

				// Pause to honor the animator's tick frequency specification
				long currTime = System.currentTimeMillis();
				long remainingWait = animator.interval()
						- (currTime - lastTickEnded);
				if (remainingWait > 0) {
					sleep((int) remainingWait);
				}

				// Ok! We can draw now.
				try {
					// lock the surface for drawing
					canvas = surfaceHolder.lockCanvas(null);
					if (canvas != null) {
						if(flashCount>0){
							canvas.drawRect(0,0, getWidth(), getHeight(), flashPaint);
							flashCount -= animator.interval();
							if(flashCount<=0){
								flashPaint=null;
							}
						}
						else{
						canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);
						}
					}
					// tell the animator to draw the next frame
					synchronized (surfaceHolder) {
						animator.tick(canvas);
					}// synchronized
				}
				// try
				finally {
					// release the canvas
					if (canvas != null) {
						surfaceHolder.unlockCanvasAndPost(canvas);
					}
				}

				// Note when this tick ended
				lastTickEnded = System.currentTimeMillis();
			}

		}// while
	}// run


	protected void requestMove(){

	}


	protected void stateChanged(ParState state){

	}


	public boolean onTouch(View v, MotionEvent event) {
		if (animator != null) {
			this.animator.onTouch(event);
		}
		return true;
	}



}

