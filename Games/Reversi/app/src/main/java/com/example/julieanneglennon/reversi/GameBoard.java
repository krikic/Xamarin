package com.example.julieanneglennon.reversi;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * Created by julieanneglennon on 3/24/17.
 */
public class GameBoard extends View {

    int BOARD_SCREEN_SIZE = 500;
    int BOARD_SQUARES = 8;
    int CELL_SIZE = BOARD_SCREEN_SIZE/BOARD_SQUARES;
    int PIECE_RADIUS = 4*CELL_SIZE /10;
    int CELL_PADDING = (CELL_SIZE)/2;

    Paint paint = new Paint();
    GameLogic state;
    Context context;



    public GameBoard(Context context) {
        super(context);
        init(context);
    }

    public GameBoard(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public GameBoard(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {

        Log.e("GameBoardView", "Starting");
        this.context = context;
        state = new GameLogic(BOARD_SQUARES, this);


        // listen to touch events so we can handle the user's move.
        this.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // on touch, find the square the user tried to touch.
                if(event.getAction() == MotionEvent.ACTION_DOWN) {
                    int x = (int)(event.getX() * BOARD_SQUARES / BOARD_SCREEN_SIZE);
                    int y = (int)(event.getY() * BOARD_SQUARES / BOARD_SCREEN_SIZE);
                    if (x >= BOARD_SQUARES || y >= BOARD_SQUARES || x<0 || y<0) {
                        return false;
                    }
                    // pass the onTouch location to handleMove
                    handleMove(x, y);
                    return true;
                }
                return false;
            }
        });
    }



    // handle an attempted move.
    public void handleMove(int x, int y) {
        Log.v("GameBoardView", "User tried to move at " + x + ", " + y);

        // is the selected square a valid move? (unoccupied square with relevant flips)
        int location = state.move(x, y, true);
        if (location == 0) {
            Log.e("GameBoardView", "User's move at " + x + ", " + y + " was not valid.");
            Toast.makeText(this.context, "Can't move there", Toast.LENGTH_SHORT).show();
            return;
        }
        Log.i("GameBoardView", "User's move at " + x + "," + y + " was valid w/take of " + location + " piece(s)");

        this.invalidate();

        state.nextTurn(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
        Log.i("GameBoardView", "onMeasure called with " + widthMeasureSpec + "x" + heightMeasureSpec);

        // The square board should fully fill the smaller dimension
        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight = MeasureSpec.getSize(heightMeasureSpec);
        if(parentHeight > parentWidth) {
            BOARD_SCREEN_SIZE = parentWidth;
        } else {
            BOARD_SCREEN_SIZE = parentHeight;
        }

        Log.i("GameBoardView", "board size of " + BOARD_SCREEN_SIZE);
        this.setMeasuredDimension(BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE);
        CELL_SIZE = BOARD_SCREEN_SIZE/BOARD_SQUARES;
        PIECE_RADIUS = 4 * CELL_SIZE / 10;
        CELL_PADDING = CELL_SIZE / 2;
    }

    //draw the Game Board
    @Override
    public void onDraw(Canvas canvas) {
        int i, j;

        // draw vertical board lines
        paint.setColor(Color.WHITE);
        paint.setStrokeWidth(2);
        for(i=0; i<BOARD_SQUARES; i++) {
            canvas.drawLine(i*CELL_SIZE, 0, i*CELL_SIZE, BOARD_SCREEN_SIZE, paint);
        }
        canvas.drawLine(BOARD_SCREEN_SIZE, 0, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, paint);

        // draw horizontal board lines
        for(i=0; i<BOARD_SQUARES; i++) {
            canvas.drawLine(0, i*CELL_SIZE, BOARD_SCREEN_SIZE, i*CELL_SIZE, paint);
        }
        canvas.drawLine(0, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, BOARD_SCREEN_SIZE, paint);


        // now draw the pieces on the board
        for(i=0; i<BOARD_SQUARES; i++){
            for(j=0; j<BOARD_SQUARES; j++){
                GamePiece piece = state.board[i][j];
                if(piece == GamePiece.PINK || piece == GamePiece.BLUE) {
                    if(piece == GamePiece.PINK) {
                        paint.setColor(Color.parseColor("#FF4081"));
                    }
                    if(piece == GamePiece.BLUE) {
                        paint.setColor(Color.parseColor("#39d4e5"));
                    }
                    canvas.drawCircle(
                            (i * CELL_SIZE) + CELL_PADDING,
                            (j * CELL_SIZE) + CELL_PADDING,
                            PIECE_RADIUS, // radius
                            paint);
                }
            }
        }
    }


}