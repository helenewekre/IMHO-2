package server.dbmanager;

import server.models.Question;
import server.models.Quiz;
import server.resetdatabase.ResetDatabase;

import javax.ws.rs.GET;
import java.sql.*;

public class dbmanager2 {

    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    public dbmanager2() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }
    /* Method for creating a quiz */
    public boolean createQuiz(Quiz quiz) throws IllegalArgumentException {
        try {
            PreparedStatement createQuiz = connection
                    .prepareStatement("INSERT INTO Quiz (created_by, question_count, quiz_title, quiz_description, idCourse) VALUES (?,?,?,?,?)");

            createQuiz.setString(1, quiz.getCreatedBy());
            createQuiz.setInt(2, quiz.getQuestionCount());
            createQuiz.setString(3, quiz.getQuizTitle());
            createQuiz.setString(4, quiz.getQuizDescription());
            createQuiz.setInt(5, quiz.getIdCourse());

            int rowsAffected = createQuiz.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    /* Method for creating a question */
    public boolean createQuestion(Question question) throws IllegalArgumentException {
        try {
            PreparedStatement createQuestion = connection
                    .prepareStatement("INSERT INTO Question (question, quiz_id) VALUES (?, ?)");
            createQuestion.setString(1, question.getQuestion());
            createQuestion.setInt(2, question.getQuizIdQuiz());

            int rowsAffected = createQuestion.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}