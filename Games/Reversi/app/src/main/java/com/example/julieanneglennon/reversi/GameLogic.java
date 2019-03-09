package com.example.julieanneglennon.reversi;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by julieanneglennon on 4/8/17.
 */

public class GameLogic {
    public GamePiece board[][];
    public int squares;
    public GamePiece currentPlayer;
    public GameBoard view;


    // Create a new Game State for a 8x8 board.
    public GameLogic(int squares, GameBoard view) {
        this.squares = squares;
        this.view = view;
        board = new GamePiece[squares][squares];

        clearBoard();
    }


    // Initialize the board to a starting state.
    public void clearBoard() {

        // empty the board.
        int i, j;
        for(i=0; i<squares; i++){
            for(j=0; j<squares; j++){
                board[i][j] = GamePiece.EMPTY;
            }
        }

        // place the 4 starting pieces in the middle.
        i = (int)((squares-1)/2);//8-1 = 7/2 = [3.5][3.5] this is the middle of the square we want
        board[i][i] = GamePiece.PINK;
        board[i+1][i+1] = GamePiece.PINK;//3.5+1=[4.5][4.5] this is the middle of the next square we want
        board[i][i+1] = GamePiece.BLUE;//[3.5][4.5]
        board[i+1][i] = GamePiece.BLUE;//[4.5][3.5]

        // Pink goes first
        currentPlayer = GamePiece.PINK;

        // redraw the board.
        view.invalidate();
    }
    // method to keep track of the score and display as a TOAST message
    public void score(){
        int pinkPieces = 0;
        int bluePieces = 0;
        int i, j;

        // tally the counts
        for(i=0;i<squares;i++){
            for(j=0;j<squares;j++){
                if(board[i][j] == GamePiece.PINK){
                    pinkPieces++;
                }
                if(board[i][j] == GamePiece.BLUE){
                    bluePieces++;
                }
            }
        }

        Toast.makeText(view.context, view.getContext().getResources().getString(R.string.score, pinkPieces, bluePieces),Toast.LENGTH_SHORT).show();
    }

    // Method to deal with GameOver and display the winner as an Alert
    public void gameOver() {
        int pinkPieces = 0;
        int bluePieces = 0;
        int i, j;
        Log.i("GameState", "Game over!");

        // tally the counts
        for(i=0;i<squares;i++){
            for(j=0;j<squares;j++){
                if(board[i][j] == GamePiece.PINK){
                    pinkPieces++;
                }
                if(board[i][j] == GamePiece.BLUE){
                    bluePieces++;
                }
            }
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        if(pinkPieces > bluePieces) {
            builder.setTitle("Game Over Pink Wins");
        }
        if(pinkPieces < bluePieces) {
            builder.setTitle("Game Over Blue Wins");
        }
        if(pinkPieces == bluePieces) {
            builder.setTitle("Game Over Draw!");
        }



        builder.setMessage(view.getContext().getResources().getString(R.string.gameover_text, pinkPieces, bluePieces));

        builder.setPositiveButton("New Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                clearBoard();
            }
        });
        builder.create().show();
        this.currentPlayer = GamePiece.EMPTY;
    }

    //method to swap Player and check the score
    public void swapPlayer() {
        assert(currentPlayer != GamePiece.EMPTY);
        currentPlayer = (currentPlayer == GamePiece.PINK)? GamePiece.BLUE : GamePiece.PINK;
        score();
    }


    // The current player has ended their turn.
    // Switch player and see if it is  gameover.
    public void nextTurn(boolean lastMoveWasForfeit) {

        swapPlayer();

        Log.i("GameState", "Now it's " + currentPlayer + "'s turn");
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            public void run() {
                Toast.makeText(view.context,"Now it's " + currentPlayer + "'s turn",Toast.LENGTH_SHORT).show();
            }
        });


        if(!this.isAMovePossible()) {
            if(lastMoveWasForfeit) {
                Log.i("GameState", "No moves left, ending game");
                Toast.makeText(view.context,"No moves left, ending game",Toast.LENGTH_SHORT).show();
                this.gameOver();
            } else {
                Log.i("GameState", "You Can't Move! Checking other Player.");
                Toast.makeText(view.context,"You Can't Move! Checking other Player.",Toast.LENGTH_SHORT).show();
                this.nextTurn(true);
            }
        } else {

            if(currentPlayer == GamePiece.BLUE) {


            }
        }
    }


    // method to see if the current player can make a move.
    public boolean isAMovePossible() {
        int i, j;
        for(i=0;i<squares;i++){
            for(j=0;j<squares;j++){
                int numCaptured = this.move(i, j, false);
                if(numCaptured > 0) {
                    return true;
                }
            }
        }
        return false;
    }


    // method to check if a move is valid,
    public int move(int i, int j, Boolean doMove) {
        // if the new space is already occupied do nothing
        if(board[i][j] != GamePiece.EMPTY){
            return 0;
        }

        //Look in the 8 different directions so that we have a line of at least
        // one opponent piece terminating in one of our own pieces.
        int dx, dy;
        int totalCaptured = 0;
        for(dx = -1; dx <= 1; dx++){
            for(dy = -1; dy <= 1; dy++){

                // explore the 8 directions for potential captures.
                for(int steps = 1; steps < squares; steps++){
                    int direction_i = i + (dx*steps);
                    int direction_j = j + (dy*steps);

                    // if the direction has gone out of bounds, stop
                    if(direction_i < 0 || direction_i >= squares || direction_j < 0 || direction_j >= squares){ break; }

                    GamePiece direction_cell = board[direction_i][direction_j];

                    // if we hit a blank cell before terminating a sequence, stop
                    if(direction_cell == GamePiece.EMPTY){ break; }

                    // if we hit a piece that's our own, capture the sequence
                    if(direction_cell == currentPlayer){
                        if(steps > 1){
                            // we've gone at least one step, capture the direction.
                            totalCaptured += steps - 1;
                            if(doMove) { // convert all pieces to currentPlayer
                                while(steps-- > 0){
                                    board[i + (dx*steps)][j + (dy*steps)] = currentPlayer;
                                };
                            }
                        }
                        break;
                    }
                }
            }
        }
        return totalCaptured;
    }

}
