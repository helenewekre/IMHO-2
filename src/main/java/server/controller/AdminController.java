package server.controller;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Question;
import server.models.Quiz;

public class AdminController {

    private DbManager dbManager;

    public AdminController() {
        dbManager = new DbManager();

    }

    public Boolean createQuiz(String quizJson) {
        Quiz quiz = new Gson().fromJson(quizJson, Quiz.class);
        Boolean ifCreated = dbManager.createQuiz(quiz);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean createQuestion(String questionJson) {
        Question question = new Gson().fromJson(questionJson, Question.class);
        Boolean ifCreated = dbManager.createQuestion(question);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }
}
