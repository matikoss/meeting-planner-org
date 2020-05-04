public class Meeting {
    private int start;
    private int end;

    public Meeting(int startMinutes, int endMinutes) {
        this.start = startMinutes;
        this.end = endMinutes;
    }

    public Meeting(int startHour, int startMinutes, int endHour, int endMinutes) {
        start = startHour * 60 + startMinutes;
        end = endHour * 60 + endMinutes;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }

    @Override
    public String toString() {
        int startHours = start / 60;
        int startMinutes = start % 60;
        int endHours = end / 60;
        int endMinutes = end % 60;

        String result = "[\"" + startHours + ":" + startMinutes + "\"," +
                "\"" + endHours + ":" + endMinutes + "\"]";

        result = result.replaceAll(":0", ":00");

        return result;
    }
}
