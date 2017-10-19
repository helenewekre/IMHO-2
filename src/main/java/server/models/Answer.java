package server.models;

public class Answer {

    private int idAnswer;
    private int answerResult;
    private int optionIdOption;

    public Answer(int idAnswer, int answerResult, int optionIdOption) {
        this.idAnswer = idAnswer;
        this.answerResult = answerResult;
        this.optionIdOption = optionIdOption;
    }

    public Answer() {

    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }

    public int getAnswerResult() {
        return answerResult;
    }

    public void setAnswerResult(int answerResult) {
        this.answerResult = answerResult;
    }

    public int getOptionIdOption() {
        return optionIdOption;
    }

    public void setOptionIdOption(int optionIdOption) {
        this.optionIdOption = optionIdOption;
    }


}