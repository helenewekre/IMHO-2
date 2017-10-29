package server.models;

public class Option {

    private int optionId;
    private String option;
    private int optionToQuestionId;
    private int isCorrect;

    public Option(int optionId, String option, int optionToQuestionId, int isCorrect) {
        this.optionId = optionId;
        this.option = option;
        this.optionToQuestionId = optionToQuestionId;
        this.isCorrect = isCorrect;
    }

    public Option() {

    }

    public int getOptionId() {
        return optionId;
    }

    public void setOptionId(int optionId) {
        this.optionId = optionId;
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public int getOptionToQuestionId() {
        return optionToQuestionId;
    }

    public void setOptionToQuestionId(int optionToQuestionId) {
        this.optionToQuestionId = optionToQuestionId;
    }

    public int getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(int isCorrect) {
        this.isCorrect = isCorrect;
    }
}