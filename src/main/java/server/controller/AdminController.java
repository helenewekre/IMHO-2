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
    public Quiz createQuiz(String quiz) {
        Quiz newQuiz = new Gson().fromJson(quiz, Quiz.class);
        Quiz createdQuiz = dbManager.createQuiz(newQuiz);

        if(createdQuiz != null ) {
            Globals.log.writeLog(getClass().getName(), this, "Quiz created", 2);
            return createdQuiz;
        } else {
            return null;
        }

    }

    //Method for creating a question
    public Question createQuestion(Question question) {

        Question createdQuestion = dbManager.createQuestion(question);
        Globals.log.writeLog(getClass().getName(), this, "Question created", 2);

        if(createdQuestion != null) {
            return createdQuestion;
        } else {
            return null;
        }

    }

    //Method for creating a option
    public Option createOption(String optionJson) {
        System.out.println(optionJson);
        Option option = new Gson().fromJson(optionJson, Option.class);
        Option newOption = dbManager.createOption(option);
        Globals.log.writeLog(getClass().getName(), this, "Option created", 2);

        if(newOption != null) {
            return newOption;
        } else {
            return null;
        }

    }

    //Method for deleting a quiz and all it's sub-tables
    public Boolean deleteQuiz(int quizId) {
        Boolean ifDeleted = dbManager.deleteQuiz(quizId);

        if (ifDeleted) {
            return true;
        } else {
            return false;
        }
    }



}
