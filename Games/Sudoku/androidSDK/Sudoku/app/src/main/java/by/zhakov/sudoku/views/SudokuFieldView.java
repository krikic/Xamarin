package by.zhakov.sudoku.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import by.zhakov.sudoku.controllers.GameController;
import by.zhakov.sudoku.util.IntPoint;

public class SudokuFieldView extends View{
    private Rect picker;

    {
        picker = new Rect();
        this.setOnTouchListener(new TouchListener());
    }

    public SudokuFieldView(Context context) {
        super(context);
    }

    public SudokuFieldView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SudokuFieldView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint p = new Paint();
        Paint n = new Paint();
        n.setColor(Color.BLACK);
        n.setTextAlign(Paint.Align.CENTER);
        p.setColor(Color.BLUE);
        p.setAlpha(50);
        canvas.drawRect(picker, p);
        int[][] field = GameController.getInstance().getNumbers();
        float widthStep = (float)getMeasuredWidth()/9;
        float heightStep = (float)getMeasuredHeight()/9;
        n.setTextSize(heightStep);
        int[][] initial = GameController.getInstance().getInitialNumber();
        for (int i = 0; i < 9; i++){
            for (int q = 0; q < 9; q++){
                if (initial[i][q] != 0){
                    canvas.drawText(initial[i][q] + "", widthStep*q + (widthStep / 2),
                            heightStep*i + heightStep - (heightStep*0.1f), n);
                }
            }
        }
        n.setAlpha(150);
        for (int i = 0; i < 9; i++){
            for (int q = 0; q < 9; q++){
                if (field[i][q] != 0){
                    canvas.drawText(field[i][q] + "", widthStep*i + (widthStep / 2),
                            heightStep*q + heightStep - (heightStep*0.1f), n);
                }
            }
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int orientation = getResources().getConfiguration().orientation;
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int height = getMeasuredHeight();
        final int width = getMeasuredWidth();
        if (height < width){
            setMeasuredDimension(height, height);
        } else {
            setMeasuredDimension(width, width);
        }

    }

    public void onDigitsPress(int num){
        GameController controller = GameController.getInstance();
        controller.setCell(num);
        invalidate();
    }

    class TouchListener implements OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN)
            {
                GameController controller = GameController.getInstance();
                double widthStep = (double)view.getMeasuredWidth()/9;
                double heightStep = (double)view.getMeasuredHeight()/9;
                int x = (int)(motionEvent.getX()/widthStep);
                int y = (int)(motionEvent.getY()/heightStep);
                controller.touch(x, y);
                IntPoint pickerPoint = controller.getActive();
                if (pickerPoint.getX() == -1){
                    picker.set(0, 0, 0, 0);
                } else {
                    picker.set((int)(x * widthStep), (int)(y * heightStep),
                            (int)(x * widthStep + widthStep), (int)(y * heightStep + heightStep));
                }
                invalidate();
            }
            return true;
        }
    }
}
