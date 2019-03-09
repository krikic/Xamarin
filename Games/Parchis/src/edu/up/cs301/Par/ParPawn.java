package edu.up.cs301.Par;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
/**
 * is able to create a new pawn to add to display
 * @author forsee16
 *
 */
public class ParPawn {
	
	private int x;
	private int y;
	private Paint myPaint;
	private int size = 10;
	private Paint background;
	
	public ParPawn(int initX, int initY, Paint initPaint, Paint initBackground) {
		x = initX;
		y = initY;
		myPaint = initPaint;
		background = initBackground;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, 11, background); 
		canvas.drawCircle(x, y, getSize(), myPaint);
	}
	
	public void setPos(int newX, int newY)
	{
		x = newX;
		y = newY;
		
	}
	
	public int getSize() {
		return size;
	}
	
	public void setColor(Paint paint, Paint other) {
		myPaint = paint;
		background = other;
	}
}
