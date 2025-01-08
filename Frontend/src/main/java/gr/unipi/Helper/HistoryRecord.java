package gr.unipi.Helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class HistoryRecord {
    // This class is used to store the history of the user's actions.
    private final LocalDateTime time;
    private final String action;

    public HistoryRecord() {
        // This constructor is used to initialize the historyRecordList in ProjectApplication.java
        this.time = null;
        this.action = "";
    }

    public HistoryRecord(String action) {
        // This constructor is used to add a new record to the historyRecordList
        this.time = LocalDateTime.now();
        this.action = action;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("dd/MM/yyy HH:mm:ss"));
    }

    public String getAction() {
        return action;
    }

    @Override
    public String toString() {
        return "HistoryRecord{" +
                "time=" + time +
                ", action='" + action + '\'' +
                '}';
    }
}
