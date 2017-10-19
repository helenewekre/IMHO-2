package server.dbmanager;

import server.models.*;

import java.sql.*;
import java.util.ArrayList;

public class DbManager {
    // Creating the connection for the database
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "test";
    private static final String PASSWORD = " ";
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

            while(resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getInt("type"));

            }

        } catch(SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                resultSet.close();
            } catch(SQLException exception) {
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
            createUser.setString(1,user.getUsername());
            createUser.setString(2,user.getPassword());

            int rowsAffected = createUser.executeUpdate();
            if(rowsAffected == 1) {
                return true;
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        } return false;
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

    public ArrayList<Question> loadQuestions (int quizId) {
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
            while(resultSet.next()) {
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

    //creates an arrayList based on options and use the question´s ID, to find the corresponding options
    public ArrayList<Option> getOption(int question){
        ResultSet resultSet = null;
        ArrayList<Option> options = new ArrayList<>();

        //PreparedStatements which communicates with the database
        try{
            PreparedStatement getOption = connection.prepareStatement("SELECT * FROM Option WHERE question_id = ?");

            getOption.setInt(1,question);
            resultSet = getOption.executeQuery();

            //add all tables from DB to specific option and finally add the option to the array.
            //the result.next() does this for each option.
            while(resultSet.next()){
                Option option = new Option();
                option.setIdOption(resultSet.getInt("idQuestion"));
                option.setOption(resultSet.getString("option"));
                option.setQuestionIdQuestion(resultSet.getInt("question_id"));
                option.setIsCorrect(resultSet.getInt("is_correct"));

                options.add(option);
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
        return options;

    }

}

