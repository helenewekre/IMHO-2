package server.models;

public class Course {

    private int courseId;
    private String courseTitle;

    public Course(int courseId, String courseTitle) {
        this.courseId = courseId;
        this.courseTitle = courseTitle;
    }
    public Course() {

    }

    public int getCourseId() {
        return courseId;
    }

    public void setCourseId(int courseId) {

        this.courseId = courseId;
    }

    public String getCourseTitle() {
        return courseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;

    }
}