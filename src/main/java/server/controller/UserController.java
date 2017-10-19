package server.controller;

import server.dbmanager.DbManager;
import server.models.*;

import java.util.ArrayList;

public class UserController {




    //this code calculate user results on a quiz.
    public Result getResult (int quizID, int userID){
        Result result = new Result();
        DbManager dbManager = new DbManager();
        result.setResult(dbManager.getNrCorrectAnswers(quizID, userID));
        result.setNrQuestions(dbManager.getNrQuestion(quizID));

        return result;
        }

}