package edu.up.cs301.Par;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class ParPawn {
	
	private int x;
	private int y;
	private Paint myPaint;
	private int size = 10;
	private Paint black = new Paint(Color.BLACK);
	
	public ParPawn(int initX, int initY, Paint initPaint) {
		x = initX;
		y = initY;
		myPaint = initPaint;
	}
	
	public void draw(Canvas canvas) {
		canvas.drawCircle(x, y, 11, black); 
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
	
	public void setColor(int newColor) {
		myPaint.setColor(newColor);
	}
}
