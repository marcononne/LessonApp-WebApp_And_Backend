package utils.JSONClasses;

public class TeacherJSON {
    private final String name;
    private final String surname;
    private final boolean status;

    public TeacherJSON(String name, String surname, boolean status) {
        this.name = name;
        this.surname = surname;
        this.status = status;
    }

    @Override
    public String toString() {
        return name + " " + surname + " ---- " + status;
    }
}
