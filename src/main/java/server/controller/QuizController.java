package server.controller;

import com.google.gson.Gson;
import server.dbmanager.DbManager;
import server.models.Course;
import server.models.Option;
import server.models.Question;
import server.models.Quiz;
import server.utility.Globals;

import java.util.ArrayList;

public class QuizController {


    public QuizController() {
    }

    //Method for loading courses
    public ArrayList<Course> loadCourses() {
        DbManager dbManager = new DbManager();
        ArrayList<Course> courses = dbManager.loadCourses();
        return courses;
    }

    // Method for loading quizzes
    public ArrayList<Quiz> loadQuizzes(int courseId) {
        DbManager dbManager = new DbManager();
        ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);
        return quizzes;
    }

    //Method for creating a question
    public Quiz createQuiz(Quiz quiz) {
        DbManager dbManager = new DbManager();
        Quiz createdQuiz = dbManager.createQuiz(quiz);
        if (createdQuiz != null) {
            return createdQuiz;
        } else {
            return null;
        }
    }

    //Method for deleting a quiz and all it's sub-tables
    public Boolean deleteQuiz(int quizId) {
        DbManager dbManager = new DbManager();
        Boolean ifDeleted = dbManager.deleteQuiz(quizId);
        return ifDeleted;
    }

    public ArrayList<Question> loadQuestions(int quizId) {
        DbManager dbManager = new DbManager();
        ArrayList<Question> question = dbManager.loadQuestions(quizId);

        if(!question.isEmpty()) {
            return question;
        } else {
            return null;
        }
    }

    //Method for creating a question
    public Question createQuestion(Question question) {
        DbManager dbManager = new DbManager();
        Question createdQuestion = dbManager.createQuestion(question);

        if (createdQuestion != null) {
            return createdQuestion;
        } else {
            return null;
        }
    }

    //Method for creating a option
    public Option createOption(Option option) {
        DbManager dbManager = new DbManager();
        Option newOption = dbManager.createOption(option);
        if (newOption != null) {
            return newOption;
        } else {
            return null;
        }

    }
    //Method for loading options
    public ArrayList<Option> getOptions(int idQuestion) {
        DbManager dbManager = new DbManager();
        ArrayList<Option> options = dbManager.loadOptions(idQuestion);
        if(options !=null) {
            return options;
        } else {
            return null;
        }
    }



}
