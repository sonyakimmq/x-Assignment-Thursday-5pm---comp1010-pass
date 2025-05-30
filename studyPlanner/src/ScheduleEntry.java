/**
 * Represents a single scheduled activity in the weekly planner.
 * Contains the unit name, type of activity, and the start and end times.
 */
public class ScheduleEntry {

    // Fields should be private (encapsulation)
    private String unitName;
    private String activityType; // "Lecture", "Practical", or "Self-Study"
    private DayTime startTime;
    private DayTime endTime;

    /**
     * Constructor for ScheduleEntry class
     * @param unitName the unit code (e.g. COMP1010)
     * @param activityType type of session (Lecture, Practical, Self-Study)
     * @param startTime when the session starts
     * @param endTime when the session ends
     */
    public ScheduleEntry(String unitName, String activityType, DayTime startTime, DayTime endTime) {
        this.unitName = unitName;
        this.activityType = activityType;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    // Getters (for other classes to access fields)

    public String getUnitName() {
        return unitName;
    }

    public String getActivityType() {
        return activityType;
    }

    public DayTime getStartTime() {
        return startTime;
    }

    public DayTime getEndTime() {
        return endTime;
    }

    /**
     * Converts the ScheduleEntry object to a CSV-formatted string
     * Format: unitName,activityType,day,start,end
     */
    @Override
    public String toString() {
        return unitName + "," + activityType + "," +
               startTime.getDayOfWeek() + "," +
               startTime.toString() + "," +
               endTime.toString();
    }
}
