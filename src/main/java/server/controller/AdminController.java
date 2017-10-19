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

    //Method for creating a question
    public Boolean createQuiz(Quiz quiz) {
        Boolean ifCreated = dbManager.createQuiz(quiz);

        if(ifCreated) {
            Globals.log.writeLog(getClass().getName(), this, "Quiz created", 2);
            return true;
        } else {
            return false;
        }

    }

    //Method for creating a question
    public Boolean createQuestion(Question question) {

        Boolean ifCreated = dbManager.createQuestion(question);
        Globals.log.writeLog(getClass().getName(), this, "Question created", 2);

        if(ifCreated) {
            return true;
        } else {
            return false;
        }

    }

    //Method for creating a option
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

    //Method for deleting a quiz and all it's sub-tables
    public Boolean deleteQuiz(int idQuiz) {

        Boolean ifDeleted = dbManager.deleteQuiz(idQuiz);

        if (ifDeleted) {
            return true;
        } else {
            return false;
        }
    }

}
