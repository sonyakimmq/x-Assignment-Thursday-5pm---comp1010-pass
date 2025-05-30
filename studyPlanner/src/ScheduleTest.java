public class ScheduleTest {
    public static void main(String[] args) {
        // Create DayTime start and end
        DayTime start = new DayTime(9, 0, "MONDAY");
        DayTime end = new DayTime(10, 0, "MONDAY");

        // Create a ScheduleEntry
        ScheduleEntry entry = new ScheduleEntry("COMP1010", "LECTURE", start, end);

        // Create a Schedule and add entry
        Schedule schedule = new Schedule();
        schedule.addEntry(entry);

        // Print all entries
        schedule.printSchedule();
    }
}
