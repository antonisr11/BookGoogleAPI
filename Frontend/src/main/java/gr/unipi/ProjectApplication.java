package gr.unipi;

import gr.unipi.Helper.HistoryRecord;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import java.awt.*;
import java.net.URL;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class ProjectApplication {

    public static final List<HistoryRecord> historyRecordList = new ArrayList<>();

    public static void addHistoryRecord(HistoryRecord historyRecord) {
        // Add a history record to the list and remove the oldest one if the list is full (list capacity: 5 records)
        if (historyRecordList.size() == 5) {
            historyRecordList.remove(0);
        }
        historyRecordList.add(historyRecord);
    }

    public static void main(String[] args) {
        // Add 5 empty history records to the list (so that the list will not be empty when the user wants to see the history)
        for (int i = 0; i < 5; i++) {
            addHistoryRecord(new HistoryRecord());
        }

        // Start the Spring application
        SpringApplication.run(ProjectApplication.class, args);

        // Open the main screen in the browser
        try {
            System.setProperty("java.awt.headless", "false");
            Desktop.getDesktop().browse(new URL("http://localhost:8080/").toURI());
        } catch (Exception e) {
            System.out.println("CustomErrorController opening main screen in browser");
        }
    }

}
