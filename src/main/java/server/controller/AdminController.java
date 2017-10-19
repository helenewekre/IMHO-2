package server.controller;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Option;
import server.models.Question;
import server.models.Quiz;
import server.utility.Globals;

public class AdminController {

    private DbManager dbManager;

    public AdminController() {
        dbManager = new DbManager();

    }

    public Boolean createQuiz(String quizJson) {
        Quiz quiz = new Gson().fromJson(quizJson, Quiz.class);
        Boolean ifCreated = dbManager.createQuiz(quiz);

        if(ifCreated) {
            Globals.log.writeLog(getClass().getName(), this, "Quiz created", 2);
            return true;
        } else {
            return false;
        }

    }

    public Boolean createQuestion(String questionJson) {
        Question question = new Gson().fromJson(questionJson, Question.class);
        Boolean ifCreated = dbManager.createQuestion(question);
        Globals.log.writeLog(getClass().getName(), this, "Question created", 2);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }

    public Boolean createOption(String optionJson) {
        System.out.println(optionJson);
        Option option = new Gson().fromJson(optionJson, Option.class);
        Boolean ifCreated = dbManager.createOption(option);
        Globals.log.writeLog(getClass().getName(), this, "Option created", 2);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }

}
