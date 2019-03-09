using System;
using System.Collections.Generic;
using System.Text;

namespace Sudoku
{
	public class RecordInfo
	{
		private const string PATTERN = "Difficulty: %s \n" +
			"Average game time: %s \n" +
			"Games played: %d \n" +
			"------RECORDS------\n";
		private List<Int32> records = new List<Int32>();
		private int avgTime;
		private int gamesPlayed;
		private Java.Lang.String difficulty;

		public int getAvgTime() {
			return avgTime;
		}

		public int getGamesPlayed() {
			return gamesPlayed;
		}

		public Java.Lang.String getDifficulty() {
			return difficulty;
		}

		public RecordInfo(int avgTime, int gamesPlayed, Java.Lang.String difficulty) {
			this.avgTime = avgTime;
			this.gamesPlayed = gamesPlayed;
			this.difficulty = difficulty;
		}

		public void addRecord(int rec){
			records.Add(rec);
		}

		public int getRecord(int index){
			return records[index];
		}

		public int getSize(){
			return records.Count;
		}

		public override System.String ToString() {
			StringBuilder result = new StringBuilder();
			records.Sort();
			System.String avgTimeString = avgTime/1000/60 + " : " + avgTime/1000%60;
			result.Append(Java.Lang.String.Format(PATTERN, difficulty, avgTimeString, gamesPlayed));
			for (int i = 0; i < records.Count; i++){
				int seconds = (records[i] / 1000);
				int minutes = seconds / 60;
				seconds = seconds % 60;
				result.Append((i + 1) + ")\t" + minutes + " : " + seconds + "\n");
			}
			return result.ToString();
		}
	}
}

