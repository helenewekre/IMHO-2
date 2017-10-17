package server.controller;

import com.google.gson.Gson;
import server.dbmanager.dbmanager2;
import server.models.Question;
import server.models.Quiz;

public class AdminController {

    private dbmanager2 db2;

    public AdminController() {
        db2 = new dbmanager2();

    }

    public Boolean createQuiz(String quizJson) {

        Quiz quiz = new Gson().fromJson(quizJson, Quiz.class);

        Boolean ifCreated = db2.createQuiz(quiz);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean createQuestion(String questionJson) {

        Question question = new Gson().fromJson(questionJson, Question.class);

        Boolean ifCreated = db2.createQuestion(question);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }
}
