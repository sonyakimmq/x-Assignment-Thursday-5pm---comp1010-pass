public class UnitTest {
    public static void main(String[] args) {
        // Create a Unit (Name from StudySchedulePlanner's version)
        Unit u = new Unit("COMP1010");

        // Add lectures and practicals manually (60 minutes each)
        u.addLecture(new ScheduledSession(java.time.DayOfWeek.MONDAY,
                new TimeSlot(java.time.LocalTime.of(9, 0), 60),
                SessionType.LECTURE));

        u.addPractical(new ScheduledSession(java.time.DayOfWeek.TUESDAY,
                new TimeSlot(java.time.LocalTime.of(10, 0), 60),
                SessionType.PRACTICAL));

        // Print the unit name
        System.out.println("Unit: " + u.getName());

        // Print all scheduled sessions
        for (ScheduledSession s : u.getAllSessions()) {
            System.out.println(s.getType() + " on " + s.getDay() + " at " + s.getTimeSlot().getStartTime());
        }
    }
}
