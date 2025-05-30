import java.io.*;
import java.util.*;

/**
 * Represents a weekly schedule of university activities,
 * including lectures, practicals, and self-study sessions.
 */
public class Schedule {
    // List of all scheduled entries (private for encapsulation)
    private ArrayList<ScheduleEntry> entries;

    /**
     * Constructor to initialize an empty schedule.
     */
    public Schedule() {
        this.entries = new ArrayList<>();
    }

    /**
     * Adds a ScheduleEntry to the schedule.
     * @param entry the entry to add
     */
    public void addEntry(ScheduleEntry entry) {
        this.entries.add(entry);
    }

    /**
     * Returns the list of all schedule entries.
     * @return list of ScheduleEntry objects
     */
    public ArrayList<ScheduleEntry> getEntries() {
        return entries;
    }

    /**
     * Prints all schedule entries to the console.
     */
    public void printSchedule() {
        for (ScheduleEntry entry : entries) {
            System.out.println(entry);
        }
    }

    /**
     * Saves the schedule to a CSV file.
     * Each line contains: unitName,activityType,day,start,end
     * @param fileName the name of the file to save
     * @throws IOException if file cannot be written
     */
    public void saveToCSV(String fileName) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(fileName));
        for (ScheduleEntry entry : entries) {
            writer.write(entry.toString());
            writer.newLine();
        }
        writer.close();
    }

    /**
     * Loads a schedule from a CSV file.
     * Assumes each line is: unitName,activityType,day,startTime,endTime
     * @param fileName the name of the file to load
     * @throws IOException if file cannot be read
     */
    public void loadFromCSV(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            try {
                String[] parts = line.split(",");
                String unitName = parts[0];
                String activityType = parts[1];
                String dayOfWeek = parts[2];

                // Parse start time
                String[] startParts = parts[3].split(":");
                int startHour = Integer.parseInt(startParts[0]);
                int startMinute = Integer.parseInt(startParts[1]);
                DayTime startTime = new DayTime(startHour, startMinute, dayOfWeek);

                // Parse end time
                String[] endParts = parts[4].split(":");
                int endHour = Integer.parseInt(endParts[0]);
                int endMinute = Integer.parseInt(endParts[1]);
                DayTime endTime = new DayTime(endHour, endMinute, dayOfWeek);

                // Create entry and add to schedule
                ScheduleEntry entry = new ScheduleEntry(unitName, activityType, startTime, endTime);
                this.entries.add(entry);
            } catch (Exception e) {
                System.out.println("Skipping malformed line: " + line);
            }
        }
        reader.close();
    }
}
