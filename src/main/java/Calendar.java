import java.util.List;

public class Calendar {
    private int workingTimeStart;
    private int workingTimeEnd;
    private List<Meeting> meetings;

    public Calendar(int workingTimeStart, int workingTimeEnd, List<Meeting> meetings) {
        this.workingTimeStart = workingTimeStart;
        this.workingTimeEnd = workingTimeEnd;
        this.meetings = meetings;
    }

    public int getWorkingTimeStart() {
        return workingTimeStart;
    }

    public int getWorkingTimeEnd() {
        return workingTimeEnd;
    }

    public List<Meeting> getMeetings() {
        return meetings;
    }
}
