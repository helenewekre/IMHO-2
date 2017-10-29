package server.models;

public class Quiz {

    private int quizId;
    private String createdBy;
    private int questionCount;
    private String quizTitle;
    private String quizDescription;
    private int courseId;

    public Quiz(int quizId, String createdBy, int questionCount, String quizTitle, String quizDescription, int courseId) {
        this.quizId = quizId;
        this.createdBy = createdBy;
        this.questionCount = questionCount;
        this.quizTitle = quizTitle;
        this.quizDescription = quizDescription;
        this.courseId= courseId;
    }

    public Quiz() {

    }

    public int getQuizId() {
        return quizId;
    }

    public void setQuizId(int quizId) {
        this.quizId = quizId;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public String getQuizTitle() {
        return quizTitle;
    }

    public void setQuizTitle(String quizTitle) {
        this.quizTitle = quizTitle;
    }

    public String getQuizDescription() {
        return quizDescription;
    }

    public void setQuizDescription(String quizDescription) {
        this.quizDescription = quizDescription;
    }

    public int getCourseId() {
        return courseId; }

    public void setCourseId (int courseId) {
        this.courseId = courseId;
    }
}