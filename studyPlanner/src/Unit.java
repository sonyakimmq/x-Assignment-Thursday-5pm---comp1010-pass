/**
 * The Unit class represents a university unit with its weekly study breakdown.
 * Each unit contains lecture hours, practical hours, and automatically calculated self-study hours.
 * The total hours per week for each unit must be 10.
 */
public class Unit {

    // Private attributes to follow encapsulation
    private String unitName;
    private int lectureHours;
    private int practicalHours;
    private int selfStudyHours;

    public Unit(String unitName) {
    this.unitName = unitName;
    this.lectureHours = 0;
    this.practicalHours = 0;
    this.selfStudyHours = 10;
}

    /**
     * Constructor for the Unit class.
     * Calculates selfStudyHours as 10 - (lecture + practical).
     *
     * @param unitName       Name of the unit (e.g., "COMP1010")
     * @param lectureHours   Number of lecture hours per week
     * @param practicalHours Number of practical (SGTA/workshop) hours per week
     */
    public Unit(String unitName, int lectureHours, int practicalHours) {
        this.unitName = unitName;
        this.lectureHours = lectureHours;
        this.practicalHours = practicalHours;
        this.selfStudyHours = 10 - (lectureHours + practicalHours);
    }

    // Getters

    public String getUnitName() {
        return unitName;
    }

    public int getLectureHours() {
        return lectureHours;
    }

    public int getPracticalHours() {
        return practicalHours;
    }

    public int getSelfStudyHours() {
        return selfStudyHours;
    }

    // Setters

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public void setLectureHours(int lectureHours) {
        this.lectureHours = lectureHours;
        recalculateSelfStudyHours();
    }

    public void setPracticalHours(int practicalHours) {
        this.practicalHours = practicalHours;
        recalculateSelfStudyHours();
    }

    /**
     * Recalculates the self-study hours based on updated lecture/practical hours.
     * Ensures the total is always 10 hours.
     */
    private void recalculateSelfStudyHours() {
        this.selfStudyHours = 10 - (this.lectureHours + this.practicalHours);
    }

    /**
     * Returns a string representation of the Unit object.
     *
     * @return A formatted string with unit name and study hours.
     */
    @Override
    public String toString() {
        return unitName + ": " + lectureHours + "h lecture, " + 
               practicalHours + "h practical, " + 
               selfStudyHours + "h self-study";
    }
}
