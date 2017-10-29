package server.models;

public class Answer {

    private int answerId;
    private int answerResult;
    private int answerToOptionId;

    public Answer(int answerId, int answerResult, int answerToOptionId) {
        this.answerId = answerId;
        this.answerResult = answerResult;
        this.answerToOptionId = answerToOptionId;
    }

    public Answer() {

    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(int answerResult) {
        this.answerResult = answerResult;
    }

    public int getAnswerToOptionId() {
        return answerToOptionId;
    }

    public void setAnswerToOptionId(int answerToOptionId) {
        this.answerToOptionId = answerToOptionId;
    }


}