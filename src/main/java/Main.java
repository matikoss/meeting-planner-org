public class Main {
    public static void main(String[] args) {
        CalendarReader calendarReader = new CalendarReader();
        Calendar calendar1 = calendarReader.readCalendar("/kalendarz1");
        Calendar calendar2 = calendarReader.readCalendar("/kalendarz2");
        int meetingDur = calendarReader.readMeetingDuration("/czas-spotkania");
        MeetingPlanner meetingPlanner = new MeetingPlanner(calendar1, calendar2, meetingDur);
        meetingPlanner.findAvailableTimes();
        System.out.println(meetingPlanner.findAvailableTimes());
    }
}
