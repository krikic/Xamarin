using System;

namespace Sudoku
{
	public class Field
	{
		private int[,] cells;

		public Field() {
			cells = new int[9, 9];
		}

		public int[,] getCells() {
			return cells;
		}

		public int getCell(int x, int y){
			if (x > 8 || x <0 || y > 8 || y < 0){
				return 0;
			}
			return cells[x, y];
		}

		public void setCell(int x, int y, int value){
			if (x > 8 || x <0 || y > 8 || y < 0){
				return;
			}
			cells[x, y] = value;
		}
	}
}

