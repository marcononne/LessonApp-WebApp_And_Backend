package utils.JSONClasses;

public class LessonJSON {

    private final String username;
    private final String teacher;
    private final String course;
    private final String date;
    private final String day;
    private final int hour;
    private final String status;

    public LessonJSON(String username, String teacher, String course, String date, String day, int hour, String status) {
        this.username = username;
        this.teacher = teacher;
        this.course = course;
        this.date = date;
        this.day = day;
        this.hour = hour;
        this.status = status;
    }
}
