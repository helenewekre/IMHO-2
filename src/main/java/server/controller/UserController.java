package server.controller;

import server.dbmanager.DbManager;
import server.models.*;

import java.util.ArrayList;

public class UserController {


    public UserController() {

    }


    //Method for calculating result
    public Result getResult(int quizId, int userId) {
        DbManager dbManager = new DbManager();
        Result result = new Result();
        result.setResult(dbManager.getCorrectAnswersCount(quizId, userId));
        result.setQuestionCount(dbManager.getQuestionCount(quizId));

        return result;
    }

    //Method for deleting answers
    public Boolean deleteAnswer(int userId) {
        DbManager dbManager = new DbManager();
        Boolean ifDeleted = dbManager.deleteAnswer(userId);
        return ifDeleted;
    }

}