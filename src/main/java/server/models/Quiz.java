package server.models;

public class Quiz {

    private int idQuiz;
    private String createdBy;
    private String url;
    private int questionCount;
    private String quizTitle;
    private String quizDescription;
    private int idCourse;

    public Quiz(int idQuiz, String createdBy, String url, int questionCount, String quizTitle, String quizDescription, int idCourse) {
        this.idQuiz = idQuiz;
        this.createdBy = createdBy;
        this.url = url;
        this.questionCount = questionCount;
        this.quizTitle = quizTitle;
        this.quizDescription = quizDescription;
        this.idCourse= idCourse;
    }

    public Quiz() {

    }

    public int getIdQuiz() {
        return idQuiz;
    }

    public void setIdQuiz(int idQuiz) {
        this.idQuiz = idQuiz;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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

    public int getIdCourse() { return idCourse; }

    public void setIdCourse (int idCourse) { this.idCourse = idCourse;
    }
}