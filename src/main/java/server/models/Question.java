package server.models;

import java.util.ArrayList;

public class Question {

    private int idQuestion;
    private String question;
    private int quizIdQuiz;
    private ArrayList<Option> options;
    private String option;
    /*private int quizTopicIdTopic;*/

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    /*evt tilføj til parameter (slettet pr 17/10) int quizTopicIdTopic)*/
    public Question(int idQuestion, String question, int quizIdQuiz) {
        this.idQuestion = idQuestion;
        this.question = question;
        this.quizIdQuiz = quizIdQuiz;
        this.options = new ArrayList<Option>();
       /* this.quizTopicIdTopic = quizTopicIdTopic; */
    }

    public Question(){
        this.options = new ArrayList<Option>();
    }

    public ArrayList<Option> getOptions() {
        return options;
    }

    public void setOption(Option option) {
        this.options.add(option);
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQuizIdQuiz() {
        return quizIdQuiz;
    }

    public void setQuizIdQuiz(int quizIdQuiz) {
        this.quizIdQuiz = quizIdQuiz;
    }

   /* public int getQuizTopicIdTopic() {
        return quizTopicIdTopic;
    }

    public void setQuizTopicIdTopic(int quizTopicIdTopic) {
        this.quizTopicIdTopic = quizTopicIdTopic;
    } */
}