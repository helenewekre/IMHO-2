package server.controller;

import server.dbmanager.DbManager;
import server.models.*;

import java.util.ArrayList;

public class UserController {

    private DbManager dbManager;

    public UserController() {
        dbManager = new DbManager();
    }


    //Method for calculating result
    public Result getResult(int quizID, int userID) {
        Result result = new Result();
        result.setResult(dbManager.getNrCorrectAnswers(quizID, userID));
        result.setQuestionCount(dbManager.getNrQuestion(quizID));

        return result;
    }

    //Method for deleting answers
    public Boolean deleteAnswer(int userId) {
        Boolean ifDeleted = dbManager.deleteAnswer(userId);
        return ifDeleted;
    }

}