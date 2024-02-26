package utils.JSONClasses;

public class TeachingJSON {

    private final String teacher;
    private final String course;
    private final boolean status;

    public TeachingJSON(String course, String teacher, boolean status) {
        this.teacher = teacher;
        this.course = course;
        this.status = status;
    }
}
