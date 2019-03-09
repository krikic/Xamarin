package by.zhakov.sudoku.controllers;

import java.util.HashSet;
import java.util.Set;

import by.zhakov.sudoku.model.Field;
import by.zhakov.sudoku.util.IntPoint;

/**
 * Created by Aleksei on 14.05.14.
 */
public class GameController {
    private static GameController instance;
    private Field field;
    private IntPoint active;
    private boolean solved;
    private int[][] initial;

    public static GameController getInstance(){
        if (instance == null){
            instance = new GameController();
        }
        return instance;
    }

    public void setInitial(int[][] initial) {
        this.initial = initial;
    }

    public void clean(){
        active = new IntPoint(-1, -1);
        field = new Field();
    }

    private GameController(){
        field = new Field();
        active = new IntPoint(-1, -1);
    }

    public IntPoint getActive(){
        return active;
    }

    public void touch(int x, int y){
        if (x == active.getX() && y == active.getY()){
            active.setX(-1);
            active.setY(-1);
        } else {
            active.setX(x);
            active.setY(y);
        }
    }

    public void setCell(int value){
        if (active.getX() != -1 && initial[active.getY()][active.getX()] == 0 && !isSolved()){
            field.setCell(active.getX(), active.getY(), value);
        }
    }

    public boolean checkErrors(){
        for (int i = 0; i < 9; i++){
            Set<Integer> numbers = new HashSet<Integer>();
            for (int q = 0; q < 9; q++){
                if (!(field.getCell(q, i) == 0 && initial[i][q] == 0)){
                    if (numbers.contains(field.getCell(q,i))){
                        return false;
                    }
                    numbers.add(field.getCell(q, i) != 0 ? field.getCell(q, i) : initial[i][q]);
                }
            }
        }
        for (int q = 0; q < 9; q++){
            Set<Integer> numbers = new HashSet<Integer>();
            for (int i = 0; i < 9; i++){
                if (!(field.getCell(q, i) == 0 && initial[i][q] == 0)){
                    if (numbers.contains(field.getCell(q,i))){
                        return false;
                    }
                    numbers.add(field.getCell(q, i) != 0 ? field.getCell(q, i) : initial[i][q]);
                }
            }
        }
        for(int x = 0; x < 3; x++){
            for (int y = 0; y < 3; y++){
                Set<Integer> numbers = new HashSet<Integer>();
                for (int w = 0; w < 3; w++){
                    for (int h = 0; h < 3; h++){
                        int resX = x*3 + w;
                        int resY = y*3 + h;
                        if (!(field.getCell(resX, resY) == 0 && initial[resY][resX] == 0)){
                            if (numbers.contains(field.getCell(resX,resY))){
                                return false;
                            }
                            numbers.add(field.getCell(resX, resY) != 0 ? field.getCell(resX, resY)
                                    : initial[resY][resX]);
                        }
                    }
                }
            }
        }
        return true;
    }

    public int[][] getNumbers(){
        return field.getCells();
    }

    public int[][] getInitialNumber(){
        return initial;
    }

    public boolean isSolved(){
        for (int i = 0; i < 9; i++){
            for (int q = 0; q < 9; q++){
                if (field.getCell(i, q) == 0 && initial[q][i] == 0){
                    return false;
                }
            }
        }
        return checkErrors();
    }
}
