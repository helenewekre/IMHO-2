package server.dbmanager;

import server.models.Question;
import server.models.Quiz;
import server.resetdatabase.ResetDatabase;

import javax.ws.rs.GET;
import java.sql.*;
import java.util.ArrayList;

public class dbmanager2 {

    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "VibrugerikkeRoot";
    private static final String PASSWORD = "root1234";
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
                    .prepareStatement("INSERT INTO Quiz (created_by, question_count, quiz_title, quiz_description, topic_id) VALUES (?,?,?,?,?)");

            createQuiz.setString(1, quiz.getCreatedBy());
            createQuiz.setInt(2, quiz.getQuestionCount());
            createQuiz.setString(3, quiz.getQuizTitle());
            createQuiz.setString(4, quiz.getQuizDescription());
            createQuiz.setInt(5, quiz.getTopicId());

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
    /* Method for loading quizzes */
    public ArrayList<Quiz> loadQuizzes(int topicId) throws IllegalArgumentException {
        ResultSet resultSet = null;
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        try {
            PreparedStatement loadQuizzes = connection
                    .prepareStatement("GET * FROM Quiz WHERE topic_id = ?");

            loadQuizzes.setInt(1, topicId);
            resultSet = loadQuizzes.executeQuery();

            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("idQuiz"));
                quiz.setCreatedBy(resultSet.getString("created_by"));
                quiz.setQuestionCount(resultSet.getInt("question_count"));
                quiz.setQuizTitle(resultSet.getString("quiz_title"));
                quiz.setQuizDescription(resultSet.getString("quiz_description"));
                quiz.setTopicId(resultSet.getInt("topic_id"));
                quizzes.add(quiz);
            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                resultSet.close();
            } catch (SQLException ef) {
                ef.printStackTrace();
                close();
            }
        }
        return quizzes;

    }

    /* Method for deleting a quiz */
    public boolean deleteQuiz(Quiz quiz) throws IllegalArgumentException {
        try {
            PreparedStatement deleteQuiz = connection
                    .prepareStatement("DELETE FROM Quiz WHERE idQuiz = ?");

            deleteQuiz.setInt(1, quiz.getIdQuiz());

            int rowsAffected = deleteQuiz.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}