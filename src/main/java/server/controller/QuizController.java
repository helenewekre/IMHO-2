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

    private DbManager dbManager;

    public QuizController() {
        dbManager = new DbManager();
    }

    //Method for loading courses
    public ArrayList<Course> loadCourses() {
        ArrayList<Course> courses = dbManager.loadCourses();
        return courses;
    }

    // Method for loading quizzes
    public ArrayList<Quiz> loadQuizzes(int courseId) {
        ArrayList<Quiz> quizzes = dbManager.loadQuizzes(courseId);
        return quizzes;
    }

    //Method for creating a question
    public Quiz createQuiz(Quiz quiz) {
        Quiz createdQuiz = dbManager.createQuiz(quiz);
        if (createdQuiz != null) {
            return createdQuiz;
        } else {
            return null;
        }
    }

    //Method for deleting a quiz and all it's sub-tables
    public Boolean deleteQuiz(int quizId) {
        Boolean ifDeleted = dbManager.deleteQuiz(quizId);
        return ifDeleted;
    }

    public ArrayList<Question> loadQuestions(int quizId) {
        ArrayList<Question> question = dbManager.loadQuestions(quizId);
        return question;
    }

    //Method for creating a question
    public Question createQuestion(Question question) {
        Question createdQuestion = dbManager.createQuestion(question);
        if (createdQuestion != null) {
            return createdQuestion;
        } else {
            return null;
        }
    }

    //Method for creating a option
    public Option createOption(Option option) {
        Option newOption = dbManager.createOption(option);
        if (newOption != null) {
            return newOption;
        } else {
            return null;
        }

    }
    //Method for loading options
    public ArrayList<Option> getOptions(int idQuestion) {
        ArrayList<Option> options = dbManager.loadOptions(idQuestion);
        if(options !=null) {
            return options;
        } else {
            return null;
        }
    }



}
