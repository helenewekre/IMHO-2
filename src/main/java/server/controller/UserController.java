package server.controller;

import server.dbmanager.DbManager;
import server.models.*;

import java.util.ArrayList;

public class UserController {

    private DbManager dbManager;

    public UserController() {
        dbManager = new DbManager();
    }



    //this code calculate user results on a quiz.
    public Result getResult (int quizID, int userID){
        Result result = new Result();

        result.setResult(dbManager.getNrCorrectAnswers(quizID, userID));
        result.setNrQuestions(dbManager.getNrQuestion(quizID));

        return result;
        }

    public Boolean deleteAnswer(int idUser) {

        Boolean ifDeleted = dbManager.deleteAnswer(idUser);

        if (ifDeleted) {
            return true;
        } else {
            return false;
        }
    }

}