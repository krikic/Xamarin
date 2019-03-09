package by.zhakov.sudoku.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Aleksei on 17.05.14.
 */
public class RecordsInfo {
    private static final String PATTERN = "Difficulty: %s \n" +
            "Average game time: %s \n" +
            "Games played: %d \n" +
            "------RECORDS------\n";
    private ArrayList<Integer> records = new ArrayList<Integer>();
    private int avgTime;
    private int gamesPlayed;
    private String difficulty;

    public int getAvgTime() {
        return avgTime;
    }

    public int getGamesPlayed() {
        return gamesPlayed;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public RecordsInfo(int avgTime, int gamesPlayed, String difficulty) {
        this.avgTime = avgTime;
        this.gamesPlayed = gamesPlayed;
        this.difficulty = difficulty;
    }

    public void addRecord(int rec){
        records.add(rec);
    }

    public int getRecord(int index){
        return records.get(index);
    }

    public int getSize(){
        return records.size();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        Collections.sort(records);
        String avgTimeString = avgTime/1000/60 + " : " + avgTime/1000%60;
        result.append(String.format(PATTERN, difficulty, avgTimeString, gamesPlayed));
        for (int i = 0; i < records.size(); i++){
            int seconds = (records.get(i) / 1000);
            int minutes = seconds / 60;
            seconds = seconds % 60;
            result.append((i + 1) + ")\t" + minutes + " : " + seconds + "\n");
        }
        return result.toString();
    }
}
