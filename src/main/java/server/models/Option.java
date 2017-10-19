package server.models;

public class Option {

    private int idOption;
    private String options;
    private int questionIdQuestion;
    private int isCorrect;

    public Option(int idOption, String options, int questionIdQuestion, int isCorrect) {
        this.idOption = idOption;
        this.options = options;
        this.questionIdQuestion = questionIdQuestion;
        this.isCorrect = isCorrect;
    }

    public Option() {

    }

    public int getIdOption() {
        return idOption;
    }

    public void setIdOption(int idOption) {
        this.idOption = idOption;
    }

    public String getOptions() {
        return options;
    }

    public void setOption(String options) {
        this.options = options;
    }

    public int getQuestionIdQuestion() {
        return questionIdQuestion;
    }

    public void setQuestionIdQuestion(int questionIdQuestion) {
        this.questionIdQuestion = questionIdQuestion;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
}