using System;
using Java.IO;
using Java.Lang;
using Android.Util;
using System.IO;

namespace Sudoku
{
	public class SudokuFileReader
	{
		public static int[ ,] getRandSudoku(Stream ist){
			try{
				BufferedReader br = new BufferedReader(new InputStreamReader(ist));
				int count = Integer.ParseInt(br.ReadLine());
				int rand = new Random().Next(1, count);
				for (int i = 0; i < rand - 1; i++){
					br.ReadLine();
				}
				string[] sudoku = br.ReadLine().Split(' ');
				int[ ,] result = new int[9 ,9];
				for (int i = 0; i < 9; i++){
					for (int q = 0; q < 9; q++){
						result[i ,q] = Integer.ParseInt(sudoku[i*9 + q]);
					}
				}
				return result;
			} catch (System.Exception e){
				Log.Error(typeof(SudokuFileReader).FullName, "FILE EXCEPTION", e);
			}
			return null;
		}
	}
}

