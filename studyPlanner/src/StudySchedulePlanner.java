import java.time.*; // Import time-related classes
import java.util.*; // Import utility classes like List and Map

// Main class to run the schedule planner
public class StudySchedulePlanner {
    public static void main(String[] args) {
        // Create a list to store units
        List<Unit> units = new ArrayList<>();

        // Create and add lectures and practicals for a unit
        Unit unit1 = new Unit("Math101");
        unit1.addLecture(new ScheduledSession(DayOfWeek.MONDAY, new TimeSlot(LocalTime.of(9, 0), 60), SessionType.LECTURE));
        unit1.addPractical(new ScheduledSession(DayOfWeek.TUESDAY, new TimeSlot(LocalTime.of(11, 0), 60), SessionType.PRACTICAL));
        units.add(unit1);

        // Define user preferred study time slots
        UserPreferences prefs = new UserPreferences();
        prefs.addPreferredSlot(DayOfWeek.WEDNESDAY, new TimeSlot(LocalTime.of(14, 0), 60));
        prefs.addPreferredSlot(DayOfWeek.THURSDAY, new TimeSlot(LocalTime.of(10, 0), 60));
        prefs.addPreferredSlot(DayOfWeek.FRIDAY, new TimeSlot(LocalTime.of(16, 0), 60));

        // Generate the schedule using the planner
        SchedulePlanner planner = new SchedulePlanner();
        planner.generateSchedule(units, prefs);

        // Print out the final schedule
        for (Unit unit : units) {
            System.out.println("Schedule for unit: " + unit.getName());
            for (ScheduledSession session : unit.getAllSessions()) {
                System.out.printf("%s - %s: %s for %d minutes\n",
                    session.getDay(), session.getTimeSlot().getStartTime(),
                    session.getType(), session.getTimeSlot().getDurationInMinutes());
            }
            System.out.println(); // Print a newline for spacing
        }
    }
}

// Class representing a unit with lectures, practicals, and self-study sessions
class Unit {
    private String name;
    private List<ScheduledSession> lectures = new ArrayList<>();
    private List<ScheduledSession> practicals = new ArrayList<>();
    private List<ScheduledSession> selfStudySessions = new ArrayList<>();

    public Unit(String name) { this.name = name; } // Constructor

    public String getName() { return name; } // Getter for name

    public void addLecture(ScheduledSession s) { lectures.add(s); } // Add lecture session

    public void addPractical(ScheduledSession s) { practicals.add(s); } // Add practical session

    // Get all sessions including lectures, practicals, and self-study
    public List<ScheduledSession> getAllSessions() {
        List<ScheduledSession> all = new ArrayList<>();
        all.addAll(lectures);
        all.addAll(practicals);
        all.addAll(selfStudySessions);
        return all;
    }

    public List<ScheduledSession> getLectures() { return lectures; } // Get lectures
    public List<ScheduledSession> getPracticals() { return practicals; } // Get practicals
    public List<ScheduledSession> getSelfStudySessions() { return selfStudySessions; } // Get self-study
}

// Class representing a single scheduled session
class ScheduledSession {
    private DayOfWeek day;
    private TimeSlot timeSlot;
    private SessionType type;

    public ScheduledSession(DayOfWeek day, TimeSlot slot, SessionType type) {
        this.day = day;
        this.timeSlot = slot;
        this.type = type;
    }

    public DayOfWeek getDay() { return day; } // Get session day
    public TimeSlot getTimeSlot() { return timeSlot; } // Get time slot
    public SessionType getType() { return type; } // Get session type
}

// Enum to define session types
enum SessionType { LECTURE, PRACTICAL, SELF_STUDY }

// Class representing a time slot for a session
class TimeSlot {
    private LocalTime startTime;
    private int durationInMinutes;

    public TimeSlot(LocalTime startTime, int durationInMinutes) {
        this.startTime = startTime;
        this.durationInMinutes = durationInMinutes;
    }

    public LocalTime getStartTime() { return startTime; } // Get start time
    public int getDurationInMinutes() { return durationInMinutes; } // Get duration
    public LocalTime getEndTime() { return startTime.plusMinutes(durationInMinutes); } // Get end time
}

// Class representing user preferences for self-study slots
class UserPreferences {
    private Map<DayOfWeek, List<TimeSlot>> preferredStudySlots = new HashMap<>();

    public void addPreferredSlot(DayOfWeek day, TimeSlot slot) {
        preferredStudySlots.computeIfAbsent(day, k -> new ArrayList<>()).add(slot);
    }

    public Map<DayOfWeek, List<TimeSlot>> getPreferredSlots() {
        return preferredStudySlots;
    }
}

// Class that contains the schedule planning logic
class SchedulePlanner {
    // Main method to generate schedule for all units
    public void generateSchedule(List<Unit> units, UserPreferences prefs) {
        for (Unit unit : units) {
            distributeSelfStudy(unit, prefs);
        }
    }

    // Distribute self-study hours across preferred time slots
    private void distributeSelfStudy(Unit unit, UserPreferences prefs) {
        int requiredSelfStudyMinutes = (10 * 60) - totalMinutes(unit.getLectures()) - totalMinutes(unit.getPracticals());

        for (DayOfWeek day : prefs.getPreferredSlots().keySet()) {
            for (TimeSlot slot : prefs.getPreferredSlots().get(day)) {
                if (requiredSelfStudyMinutes <= 0) return;
                if (!hasConflict(day, slot, unit.getAllSessions())) {
                    unit.getSelfStudySessions().add(new ScheduledSession(day, slot, SessionType.SELF_STUDY));
                    requiredSelfStudyMinutes -= slot.getDurationInMinutes();
                }
            }
        }
    }

    // Calculate total duration of sessions
    private int totalMinutes(List<ScheduledSession> sessions) {
        return sessions.stream().mapToInt(s -> s.getTimeSlot().getDurationInMinutes()).sum();
    }

    // Check if the new slot conflicts with any existing session
    private boolean hasConflict(DayOfWeek day, TimeSlot newSlot, List<ScheduledSession> sessions) {
        for (ScheduledSession s : sessions) {
            if (s.getDay() == day) {
                TimeSlot existing = s.getTimeSlot();
                if (!(newSlot.getEndTime().isBefore(existing.getStartTime()) || newSlot.getStartTime().isAfter(existing.getEndTime()))) {
                    return true;
                }
            }
        }
        return false;
    }
}
