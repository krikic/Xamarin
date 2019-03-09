package by.zhakov.sudoku.util;

/**
 * Created by Aleksei on 14.05.14.
 */
public class IntPoint {
    private int x;
    private int y;

    public IntPoint() {
    }

    public IntPoint(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
