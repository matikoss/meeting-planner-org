import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CalendarReader {
    public Calendar readCalendar(String calendarPath) {
        String calendar = readFileToString(calendarPath);
        calendar = upgradeToJson(calendar);

        JSONObject obj = new JSONObject(calendar);
        JSONObject workingHoursJson = obj.getJSONObject("working_hours");
        String startWorkStr = workingHoursJson.getString("start");
        String endWorkStr = workingHoursJson.getString("end");

        int startWork = timeStringToIntMinutes(startWorkStr);
        int endWork = timeStringToIntMinutes(endWorkStr);
        JSONArray meetingsJson = obj.getJSONArray("planned_meeting");
        List<Meeting> meetings = new ArrayList<>();
        for (int i = 0; i < meetingsJson.length(); i++) {
            String meetingStart = meetingsJson.getJSONObject(i).getString("start");
            String meetingEnd = meetingsJson.getJSONObject(i).getString("end");
            int meetingStartInt = timeStringToIntMinutes(meetingStart);
            int meetingEndInt = timeStringToIntMinutes(meetingEnd);
            meetings.add(new Meeting(meetingStartInt, meetingEndInt));
        }

        return new Calendar(startWork, endWork, meetings);
    }

    public int readMeetingDuration(String durationPath) {
        String durationStr = readFileToString(durationPath);
        durationStr = StringUtils.substringBetween(durationStr, "[", "]");
        return timeStringToIntMinutes(durationStr);
    }

    private String readFileToString(String path) {
        InputStream inputStream = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        return reader.lines().collect(Collectors.joining(System.lineSeparator()));
    }

    private String upgradeToJson(String calendarString) {
        calendarString = calendarString.replaceAll("start", "\"start\"");
        calendarString = calendarString.replaceAll("end", "\"end\"");
        calendarString = calendarString.replaceAll("working_hours", "\"working_hours\"");
        calendarString = calendarString.replaceAll("planned_meeting", "\"planned_meeting\"");
        return calendarString;
    }

    private int timeStringToIntMinutes(String time) {
        String[] timeArr = time.split(":");
        int hours = Integer.parseInt(timeArr[0]);
        int minutes = Integer.parseInt(timeArr[1]);
        return hours * 60 + minutes;
    }
}
