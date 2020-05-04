import java.util.ArrayList;
import java.util.List;

public class MeetingPlanner {
    private Calendar firstCalendar;
    private Calendar secondCalendar;
    private int meetingDuration;

    public MeetingPlanner(Calendar firstCalendar, Calendar secondCalendar, int meetingDuration) {
        this.firstCalendar = firstCalendar;
        this.secondCalendar = secondCalendar;
        this.meetingDuration = meetingDuration;
    }

    public String findAvailableTimes() {
        int firstPersonStartTime = firstCalendar.getWorkingTimeStart();
        int firstPersonEndTime = firstCalendar.getWorkingTimeEnd();
        int secondPersonStartTime = secondCalendar.getWorkingTimeStart();
        int secondPersonEndTime = secondCalendar.getWorkingTimeEnd();

        int generalStartTime, generalEndTime;

        if (firstPersonStartTime > secondPersonStartTime) {
            generalStartTime = firstPersonStartTime;
        } else {
            generalStartTime = secondPersonStartTime;
        }

        if (firstPersonEndTime < secondPersonEndTime) {
            generalEndTime = firstPersonEndTime;
        } else {
            generalEndTime = secondPersonEndTime;
        }

        int possibleMeetingsAmount = (generalEndTime - generalStartTime) / meetingDuration;

        boolean[][] availability = new boolean[possibleMeetingsAmount][2];
        for (int i = 0; i < possibleMeetingsAmount; i++) {
            availability[i][0] = true;
            availability[i][1] = true;
        }

        for (int i = 0; i < possibleMeetingsAmount; i++) {
            int currentCaseStart = generalStartTime + i * meetingDuration;
            int currentCaseEnd = currentCaseStart + meetingDuration;

            for (Meeting meeting : firstCalendar.getMeetings()) {
                if (meeting.getStart() > currentCaseEnd) {
                    break;
                }
                if (meeting.getStart() <= currentCaseStart && meeting.getEnd() >= currentCaseEnd) {
                    availability[i][0] = false;
                    break;
                } else if (meeting.getStart() < currentCaseEnd && meeting.getEnd() >= currentCaseEnd) {
                    availability[i][0] = false;
                    break;
                } else if (meeting.getStart() <= currentCaseStart && meeting.getEnd() > currentCaseStart) {
                    availability[i][0] = false;
                    break;
                }
            }

            for (Meeting meeting : secondCalendar.getMeetings()) {
                if (meeting.getStart() > currentCaseEnd) {
                    break;
                }
                if (meeting.getStart() <= currentCaseStart && meeting.getEnd() >= currentCaseEnd) {
                    availability[i][1] = false;
                    break;
                } else if (meeting.getStart() < currentCaseEnd && meeting.getEnd() >= currentCaseEnd) {
                    availability[i][1] = false;
                    break;
                } else if (meeting.getStart() <= currentCaseStart && meeting.getEnd() > currentCaseStart) {
                    availability[i][1] = false;
                    break;
                }
            }
        }

        List<Meeting> possibleMeetings = new ArrayList<>();

        for (int i = 0; i < possibleMeetingsAmount; i++) {
            if (availability[i][0] && availability[i][1]) {
                possibleMeetings.add(new Meeting(generalStartTime + i * meetingDuration, generalStartTime + i * meetingDuration + meetingDuration));
            }
        }

        StringBuilder result = new StringBuilder("[");

        for (Meeting meeting : possibleMeetings) {
            result.append(meeting.toString());
        }
        result.append("]");
        return result.toString();
    }
}
