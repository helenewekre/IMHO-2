package server.models;

public class Result {
    int result;
    int questionCount;

    public Result(int result, int questionCount) {
        this.result = result;
        this.questionCount = questionCount;
    }

    public Result() {

    }

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }
}
