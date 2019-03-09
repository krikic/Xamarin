using System;
using System.Collections;
using System.Collections.Generic;
using Java.Lang;

namespace Sudoku
{
	public class GameController
	{
		private static GameController instance;
		private Field field;
		private IntPoint active;
		private int[,] initial =
		{{2, 7, 0, 8, 0, 0, 9, 3, 0},
			{4, 3, 0, 9, 0, 0, 2, 5, 0},
			{5, 9, 0, 2, 3, 4, 7, 0, 0},
			{6, 0, 7, 0, 9, 5, 3, 0, 2},
			{8, 0, 9, 0, 4, 0, 6, 0, 7},
			{1, 0, 3, 6, 7, 0, 4, 0, 5},
			{0, 0, 4, 7, 6, 1, 0, 2, 3},
			{0, 6, 2, 0, 0, 3, 0, 4, 9},
			{0, 1, 5, 0, 0, 9, 0, 7, 6}};

		public static GameController getInstance(){
			if (instance == null){
				instance = new GameController();
			}
			return instance;
		}

		public void setInitial(int[,] initial) {
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
			if (active.getX() != -1 && initial[active.getY(), active.getX()] == 0 && !isSolved()){
				field.setCell(active.getX(), active.getY(), value);
			}
		}

		public bool checkErrors(){
			for (int i = 0; i < 9; i++){
				HashSet<Integer> numbers = new HashSet<Integer>();
				for (int q = 0; q < 9; q++){
					if (!(field.getCell(q, i) == 0 && initial[i,q] == 0)){
						if (numbers.Contains((Integer)field.getCell(q,i))){
							return false;
						}
						Integer fieldCell = (Integer)field.getCell (q, i);
						Integer initialCell = (Integer)initial [i,q];
						numbers.Add(fieldCell != (Integer)0 ? fieldCell : initialCell);
					}
				}
			}
			for (int q = 0; q < 9; q++){
				HashSet<Integer> numbers = new HashSet<Integer>();
				for (int i = 0; i < 9; i++){
					if (!(field.getCell(q, i) == 0 && initial[i,q] == 0)){
						if (numbers.Contains((Integer)field.getCell(q,i))){
							return false;
						}
						Integer fieldCell = (Integer)field.getCell (q, i);
						Integer initialCell = (Integer)initial [i,q];
						numbers.Add(fieldCell != (Integer)0 ? fieldCell : initialCell);
					}
				}
			}
			for(int x = 0; x < 3; x++){
				for (int y = 0; y < 3; y++){
					HashSet<Integer> numbers = new HashSet<Integer>();
					for (int w = 0; w < 3; w++){
						for (int h = 0; h < 3; h++){
							int resX = x*3 + w;
							int resY = y*3 + h;
							if (!(field.getCell(resX, resY) == 0 && initial[resY,resX] == 0)){
								if (numbers.Contains((Integer)field.getCell(resX,resY))){
									return false;
								}
								Integer fieldCell = (Integer)field.getCell (resX, resY);
								Integer initialCell = (Integer)initial [resY,resX];
								numbers.Add(fieldCell != (Integer)0 ? fieldCell : initialCell);
								numbers.Add(fieldCell != (Integer)0 ? fieldCell : initialCell);
							}
						}
					}
				}
			}
			return true;
		}

		public int[,] getNumbers(){
			return field.getCells();
		}

		public int[,] getInitialNumber(){
			return initial;
		}

		public bool isSolved(){
			for (int i = 0; i < 9; i++){
				for (int q = 0; q < 9; q++){
					if (field.getCell(i, q) == 0 && initial[q,i] == 0){
						return false;
					}
				}
			}
			return checkErrors();
		}
	}
}

