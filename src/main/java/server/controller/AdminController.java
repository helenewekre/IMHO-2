package server.controller;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Option;
import server.models.Question;
import server.models.Quiz;
import server.utility.Globals;

import java.util.ArrayList;

public class AdminController {

    private DbManager dbManager;

    public AdminController() {
        dbManager = new DbManager();
    }

    //Method for creating a question
    public Quiz createQuiz(Quiz quiz) {
        Quiz createdQuiz = dbManager.createQuiz(quiz);
        if(createdQuiz != null ) {
            Globals.log.writeLog(getClass().getName(), this, "Quiz created", 2);
            return createdQuiz;
        } else {
            return null;
        }
    }

    public ArrayList<Quiz> loadQuizzes(int courseId) {
        ArrayList<Quiz> quiz = dbManager.loadQuizzes(courseId);
        return quiz;
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

    public ArrayList<Question> loadQuestions (int quizId) {
        ArrayList<Question> question = dbManager.loadQuestions(quizId);
        return question;
    }

    //Method for creating a option
    public Option createOption(Option option) {
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
