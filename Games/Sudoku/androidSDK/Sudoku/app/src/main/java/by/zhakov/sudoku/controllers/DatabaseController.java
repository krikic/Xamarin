package by.zhakov.sudoku.controllers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import by.zhakov.sudoku.model.RecordsInfo;

public class DatabaseController{
    private DBHelper dbHelper;

    public void putRecord(long time, String difficulty, Context context){

        dbHelper = new DBHelper(context);
        ContentValues cv = new ContentValues();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor c = db.query("records", null, " difficulty = ?", new String[]{difficulty}, null, null, null);
        int count = 1;
        if (c.moveToFirst()) {

            int idColIndex = c.getColumnIndex("id");
            int timeColIndex = c.getColumnIndex("time");

            int maxDBindex = c.getInt(idColIndex);
            int maxDBtime = c.getInt(timeColIndex);
            count++;
            while (c.moveToNext()) {
                if (c.getInt(timeColIndex) > maxDBtime){
                    maxDBtime = c.getInt(timeColIndex);
                    maxDBindex = c.getInt(idColIndex);
                }
                count++;
            }
            if (count == 6){
                if (time < maxDBtime){
                    db.delete("records", " id = ?", new String[]{maxDBindex + ""});
                } else {
                    c.close();
                    db.close();
                    return;
                }
            }
        }
        cv.put("time", time);
        cv.put("difficulty", difficulty);
        db.insert("records", null, cv);
        cv.clear();

        Cursor gc = db.query("general", null, "difficulty = ?", new String[]{difficulty}, null, null, null);
        gc.moveToFirst();
        int avgTimeColIndex = gc.getColumnIndex("avgTime");
        int gamesCountColIndex = gc.getColumnIndex("gamesCount");
        int avgTime = 0;
        int gamesCount = 0;
        if (gc.moveToFirst()){
            avgTime = gc.getInt(avgTimeColIndex);
            gamesCount = gc.getInt(gamesCountColIndex);
        }
        int newGamesCount = gamesCount + 1;
        int newAvgTime = (avgTime * gamesCount / newGamesCount) + (int)(time / newGamesCount);
        cv.put("difficulty", difficulty);
        cv.put("avgTime", newAvgTime);
        cv.put("gamesCount", newGamesCount);
        db.delete("general", " difficulty = ?", new String[]{difficulty});
        db.insert("general", null, cv);
        db.close();
        c.close();
        gc.close();
    }

    public RecordsInfo getRecordsInfo(String difficulty, Context context){
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor gc = db.query("general", null, "difficulty = ?", new String[]{difficulty}, null, null, null);
        int avgTimeColIndex = gc.getColumnIndex("avgTime");
        int gamesCountColIndex = gc.getColumnIndex("gamesCount");
        int avgTime = 0;
        int gamesCount = 0;
        if (gc.moveToFirst()){
            avgTime = gc.getInt(avgTimeColIndex);
            gamesCount = gc.getInt(gamesCountColIndex);
        }
        RecordsInfo result = new RecordsInfo(avgTime, gamesCount, difficulty);
        Cursor c = db.query("records", null, " difficulty = ?", new String[]{difficulty}, null, null, null);
        if (c.moveToFirst()) {
            int timeColIndex = c.getColumnIndex("time");

            result.addRecord(c.getInt(timeColIndex));
            while (c.moveToNext()) {
                result.addRecord(c.getInt(timeColIndex));
            }
        }
        db.close();
        c.close();
        gc.close();
        return result;
    }

    public void clearDB(Context context){
        dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        db.delete("general", null, null);
        db.delete("records", null, null);
        db.close();
    }

    private class DBHelper extends SQLiteOpenHelper{
        public DBHelper(Context context) {
            super(context, "sudokuDB", null, 9);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            initDB(db);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i2) {
            initDB(db);
        }

        public void initDB(SQLiteDatabase db){
            db.execSQL("DROP TABLE IF EXISTS " + "general");
            db.execSQL("DROP TABLE IF EXISTS " + "records");
            db.execSQL("create table records ("
                    + "id integer primary key autoincrement,"
                    + "time int,"
                    + "difficulty text" + ");");
            db.execSQL("create table general ("
                    + "difficulty text primary key,"
                    + "avgTime int,"
                    + "gamesCount int" + ");");
            ContentValues cv = new ContentValues();
            cv.put("difficulty", "Easy");
            cv.put("avgTime", 0);
            cv.put("gamesCount", 0);
            db.insert("general", null, cv);
            cv.clear();
            cv.put("difficulty", "Normal");
            cv.put("avgTime", 0);
            cv.put("gamesCount", 0);
            db.insert("general", null, cv);
            cv.clear();
            cv.put("difficulty", "Hard");
            cv.put("avgTime", 0);
            cv.put("gamesCount", 0);
            db.insert("general", null, cv);
        }
    }
}
