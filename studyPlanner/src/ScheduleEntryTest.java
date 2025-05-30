import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ScheduleEntryTest {

    @Test
    public void testScheduleEntryFieldsAndToString() {
        // Create DayTime objects
        DayTime start = new DayTime(9, 0, "Monday");
        DayTime end = new DayTime(10, 0, "Monday");

        // Create ScheduleEntry object
        ScheduleEntry entry = new ScheduleEntry("COMP1010", "LECTURE", start, end);

        // Test field values using getters
        assertEquals("COMP1010", entry.getUnitName());
        assertEquals("LECTURE", entry.getActivityType());
        assertEquals(start, entry.getStartTime());
        assertEquals(end, entry.getEndTime());

        // Test toString output
        String expected = "COMP1010,LECTURE,Monday,09:00,10:00";
        assertEquals(expected, entry.toString());
    }
}
