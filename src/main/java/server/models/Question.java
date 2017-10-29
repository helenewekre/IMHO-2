package server.models;

import java.util.ArrayList;

public class Question {

    private int questionId;
    private String question;
    private int questionToQuizId;
    private ArrayList<Option> options;
    private String option;


    public Question(int questionId, String question, int questionToQuizId) {
        this.questionId = questionId;
        this.question = question;
        this.questionToQuizId = questionToQuizId;
        this.options = new ArrayList<Option>();
    }

    public Question(){
        this.options = new ArrayList<Option>();
    }


    public int getQuestionId() {
        return questionId;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuestionToQuizId() {
        return questionToQuizId;
    }

    public void setQuestionToQuizId(int questionToQuizId) {
        this.questionToQuizId = questionToQuizId;
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOptions(Option option) {
        this.options.add(option);
    }

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }


}