package server.controller;

import server.dbmanager.DbManager;
import server.models.*;

import java.util.ArrayList;

public class UserController {


  private Quiz quiz = new Quiz();
  DbManager dbManager = new DbManager();

        public Result getResult (int quizID, int userID){
        Result result = new Result();

        result.setResult(dbManager.getNrCorectAnswers(quizID, userID));
        result.setNrQuestions(dbManager.getNrQuestion(quizID));

       return result;
        }

}