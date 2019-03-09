using System;
using Android.Database.Sqlite;
using Android.Content;

namespace Sudoku
{
	public class DatabaseController
	{
		private DBHelper dbHelper;

		public void putRecord(long time, String difficulty, Context context){

			dbHelper = new DBHelper(context);
			ContentValues cv = new ContentValues();
			SQLiteDatabase db = dbHelper.WritableDatabase;
			SQLiteCursor c = (SQLiteCursor)db.Query("records", null, " difficulty = ?", new String[]{difficulty}, null, null, null);
			int count = 1;
			if (c.MoveToFirst()) {

				int idColIndex = c.GetColumnIndex("id");
				int timeColIndex = c.GetColumnIndex("time");

				int maxDBindex = c.GetInt(idColIndex);
				int maxDBtime = c.GetInt(timeColIndex);
				count++;
				while (c.MoveToNext()) {
					if (c.GetInt(timeColIndex) > maxDBtime){
						maxDBtime = c.GetInt(timeColIndex);
						maxDBindex = c.GetInt(idColIndex);
					}
					count++;
				}
				if (count == 6){
					if (time < maxDBtime){
						db.Delete("records", " id = ?", new String[]{maxDBindex + ""});
					} else {
						c.Close();
						db.Close();
						return;
					}
				}
			}
			cv.Put("time", time);
			cv.Put("difficulty", difficulty);
			db.Insert("records", null, cv);
			cv.Clear();

			SQLiteCursor gc = (SQLiteCursor)db.Query("general", null, "difficulty = ?", new String[]{difficulty}, null, null, null);
			gc.MoveToFirst();
			int avgTimeColIndex = gc.GetColumnIndex("avgTime");
			int gamesCountColIndex = gc.GetColumnIndex("gamesCount");
			int avgTime = 0;
			int gamesCount = 0;
			if (gc.MoveToFirst()){
				avgTime = gc.GetInt(avgTimeColIndex);
				gamesCount = gc.GetInt(gamesCountColIndex);
			}
			int newGamesCount = gamesCount + 1;
			int newAvgTime = (avgTime * gamesCount / newGamesCount) + (int)(time / newGamesCount);
			cv.Put("difficulty", difficulty);
			cv.Put("avgTime", newAvgTime);
			cv.Put("gamesCount", newGamesCount);
			db.Delete("general", " difficulty = ?", new String[]{difficulty});
			db.Insert("general", null, cv);
			db.Close();
			c.Close();
			gc.Close();
		}

		public RecordInfo getRecordsInfo(String difficulty, Context context){
			dbHelper = new DBHelper(context);
			SQLiteDatabase db = dbHelper.WritableDatabase;
			SQLiteCursor gc = (SQLiteCursor)db.Query("general", null, "difficulty = ?", new String[]{difficulty}, null, null, null);
			int avgTimeColIndex = gc.GetColumnIndex("avgTime");
			int gamesCountColIndex = gc.GetColumnIndex("gamesCount");
			int avgTime = 0;
			int gamesCount = 0;
			if (gc.MoveToFirst()){
				avgTime = gc.GetInt(avgTimeColIndex);
				gamesCount = gc.GetInt(gamesCountColIndex);
			}
			RecordInfo result = new RecordInfo(avgTime, gamesCount, new Java.Lang.String(difficulty));
			SQLiteCursor c = (SQLiteCursor)db.Query("records", null, " difficulty = ?", new String[]{difficulty}, null, null, null);
			if (c.MoveToFirst()) {
				int timeColIndex = c.GetColumnIndex("time");

				result.addRecord(c.GetInt(timeColIndex));
				while (c.MoveToNext()) {
					result.addRecord(c.GetInt(timeColIndex));
				}
			}
			return result;
		}

		public void clearDB(Context context){
			dbHelper = new DBHelper(context);
			SQLiteDatabase db = dbHelper.WritableDatabase;
			db.Delete("general", null, null);
			db.Delete("records", null, null);
		}

		private class DBHelper : SQLiteOpenHelper{
			public DBHelper(Context context) : base(context, "sudokuDB", null, 9) {
			}
				
			public override void OnCreate(SQLiteDatabase db) {
				initDB(db);
			}


			public override void OnUpgrade(SQLiteDatabase db, int i, int i2) {
				initDB(db);
			}

			public void initDB(SQLiteDatabase db){
				db.ExecSQL("DROP TABLE IF EXISTS " + "general");
				db.ExecSQL("DROP TABLE IF EXISTS " + "records");
				db.ExecSQL("create table records ("
					+ "id integer primary key autoincrement,"
					+ "time int,"
					+ "difficulty text" + ");");
				db.ExecSQL("create table general ("
					+ "difficulty text primary key,"
					+ "avgTime int,"
					+ "gamesCount int" + ");");
				ContentValues cv = new ContentValues();
				cv.Put("difficulty", "Easy");
				cv.Put("avgTime", 0);
				cv.Put("gamesCount", 0);
				db.Insert("general", null, cv);
				cv.Clear();
				cv.Put("difficulty", "Normal");
				cv.Put("avgTime", 0);
				cv.Put("gamesCount", 0);
				db.Insert("general", null, cv);
				cv.Clear();
				cv.Put("difficulty", "Hard");
				cv.Put("avgTime", 0);
				cv.Put("gamesCount", 0);
				db.Insert("general", null, cv);
			}
		}
	}
}

