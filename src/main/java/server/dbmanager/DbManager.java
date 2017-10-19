package server.dbmanager;

import server.models.*;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    // Creating the connection for the database
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "test";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    public DbManager() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    //Method for closing the connection
    private static void close() {
        try {
            connection.close();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }

    }

    //Method for authorizing the user. Prepared statement are used, and a user object is returned.
    public User authorizeUser(String username, String password) throws IllegalArgumentException {
        ResultSet resultSet = null;
        User user = null;

        try {

            PreparedStatement authorizeUser = connection.prepareStatement("SELECT * from User where username = ? AND password = ?");

            authorizeUser.setString(1, username);
            authorizeUser.setString(2, password);

            resultSet = authorizeUser.executeQuery();
            System.out.println("RS:" + resultSet);

            while (resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getInt("type"));

            }

        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
                close();
            }
        }
        return user;
    }

    // Method for creating a user. Boolean returned, which decides if the user is created or not.
    public boolean createUser(User user) throws IllegalArgumentException {
        try {
            PreparedStatement createUser = connection.prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
            createUser.setString(1, user.getUsername());
            createUser.setString(2, user.getPassword());

            int rowsAffected = createUser.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
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

    /* Method for loading courses. */
    public ArrayList<Course> loadCourses() {   //loadCourses
        ResultSet resultSet = null;
        ArrayList<Course> courses = new ArrayList<Course>();

        try {
            PreparedStatement loadCourse = connection.prepareStatement("SELECT * FROM Course");
                                            /* connection.prepareStatement(*/
            resultSet = loadCourse.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();
                course.setIdCourse(resultSet.getInt("idCourse"));
                course.setCourseTitle(resultSet.getString("course_title"));
                courses.add(course);
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
        return courses;

    }

    /* Method for seeing available wquizzes within a chosen course */
    public ArrayList<Quiz> loadQuizzes(int courseId) {
        ResultSet resultSet = null;
        ArrayList<Quiz> quizzes = new ArrayList<Quiz>();
        try {
            PreparedStatement loadQuizzes = connection
                    .prepareStatement("SELECT * FROM Quiz WHERE idCourse = ?");


            loadQuizzes.setInt(1, courseId);
            resultSet = loadQuizzes.executeQuery();


            while (resultSet.next()) {
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("idQuiz"));
                quiz.setCreatedBy(resultSet.getString("created_by"));
                quiz.setQuestionCount(resultSet.getInt("question_count"));
                quiz.setQuizTitle(resultSet.getString("quiz_description"));
                quiz.setIdCourse(resultSet.getInt("idCourse"));
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
            return quizzes;
        }
    }

    /*Method for starting quiz - hereby showing questionlist*/

    public ArrayList<Question> loadQuestions(int quizId) {
        ResultSet resultSet = null;
        ArrayList<Question> questions = new ArrayList<Question>();
        try {
            PreparedStatement loadQuestions = connection
                    .prepareStatement("SELECT * FROM Question WHERE quiz_id = ?");


            loadQuestions.setInt(1, quizId);
            resultSet = loadQuestions.executeQuery();


            while (resultSet.next()) {
                Question question = new Question();
                question.setIdQuestion(resultSet.getInt("idQuestion"));
                question.setQuestion(resultSet.getString("question"));
                question.setQuizIdQuiz(resultSet.getInt("quiz_id"));
                questions.add(question);
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
            return questions;
        }

    }

    //to get a specific userprofile based on a corresponding ID
    public User getUserProfile(int idUser) {

        ResultSet resultSet = null;
        User user = null;

        //PreparedStatements which communicates with the database
        try {
            PreparedStatement getUserProfile = connection
                    .prepareStatement("SELECT * FROM User where idUser = ?");


            getUserProfile.setInt(1, idUser);

            resultSet = getUserProfile.executeQuery();

            //resultSet.next() takes user information from the DB and creates a temporary (user profile)
            while (resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("idUser"));
                user.setType(resultSet.getInt("type"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }

        }

        //The user is returned
        return user;
    }

    //the method get the number of corret answers of a quiz using quiz ID og user ID as parameters
    public int getNrCorectAnswers(int quizID, int userID) {
        ResultSet resultSet = null;
        int score = 0;

        //this preparedStatement get all the correct answers the user have on a quiz
        try {
            PreparedStatement getNrCorectAnswers = connection.prepareStatement("SELECT q.quiz_description, o.option, o.idOption, count(*)\n" +
                    "FROM user u\n" +
                    "INNER JOIN answer a\n" +
                    "ON u.idUser = a.user_id\n" +
                    "INNER JOIN `option` o\n" +
                    "ON a.option_id = o.idOption\n" +
                    "INNER JOIN question qt\n" +
                    "ON o.question_id = qt.idQuestion\n" +
                    "INNER JOIN quiz q\n" +
                    "ON qt.quiz_id = q.idQuiz\n" +
                    "WHERE quiz_id = ? \n" +
                    "\tAND user_id = ?\n" +
                    "GROUP BY o.is_correct");

            getNrCorectAnswers.setInt(1, quizID);
            getNrCorectAnswers.setInt(2, userID);

            resultSet = getNrCorectAnswers.executeQuery();


            while (resultSet.next()) {
                 //gets the count of the total correct answers and writes it to score.
                score = (resultSet.getInt("count(*)"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }

        }
        return score;
    }
    //the method get the number of question using quiz ID as a parameter
    public int getNrQuestion(int quizID) {

        ResultSet resultSet = null;
        int nrQuestions = 0;

        //this preparedStatement get all the questions on a quiz
        try {
            PreparedStatement getNrQuestion = connection.prepareStatement("SELECT q.idQuiz, count(*)\n" +
                    "FROM quiz q\n" +
                    "INNER JOIN question qt\n" +
                    "ON q.idQuiz = qt.quiz_id\n" +
                    "WHERE idQuiz = ?\n" +
                    "GROUP BY idQuiz");

            getNrQuestion.setInt(1, quizID);

            resultSet = getNrQuestion.executeQuery();


            while (resultSet.next()) {

                //gets the count of the total questions and writes it to nrQuestion.
                nrQuestions = (resultSet.getInt("count(*)"));

            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }

        }
        return nrQuestions;
    }

}