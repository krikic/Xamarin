package by.zhakov.sudoku.exceptions;

/**
 * Created by Aleksei on 14.05.14.
 */
public class WrongFieldNumberException extends Exception{
    public WrongFieldNumberException() {
        super();
    }

    public WrongFieldNumberException(String detailMessage) {
        super(detailMessage);
    }
}
