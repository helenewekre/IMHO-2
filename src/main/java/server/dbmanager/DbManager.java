package server.dbmanager;

import server.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbManager {
    // Creating the connection for the database
    private static final String URL = "jdbc:mysql://localhost:3306/quizdb?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
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

    //Method for loading questions
    public ArrayList<Question> loadQuestions(int quizId) {

        //Resultset to temporary contain values from SQL statement, first given the value of null
        ResultSet resultSet = null;
        //Arraylist of question object
        ArrayList<Question> questions = new ArrayList<Question>();

        //Try/catch method to avoid the program crashing on exceoptions
        try {
            //SQL query sent to SQL to get values from the database. Done thoug the connection method - see top of class.
            PreparedStatement loadQuestions = connection
                    .prepareStatement("SELECT * FROM Question WHERE quiz_id = ?");

            //Gives the SQL statement value to parameter - the quizid integer
            loadQuestions.setInt(1, quizId);
            //Adds values from SQL statement to resultset (temporarty table)
            resultSet = loadQuestions.executeQuery();

            //Method will run as long as there is content in the next line of the resultset
            while (resultSet.next()) {
                //Creating questoin object
                Question question = new Question();
                //Adding values to parameter variables in the question object
                question.setQuestion(resultSet.getString("question"));
                question.setIdQuestion(resultSet.getInt("idQuestion"));
                question.setQuizIdQuiz(resultSet.getInt("quiz_id"));
                //Adding the questoin object to the arraylist of question objects
                questions.add(question);
            }

            //Question to avoid crashes on exceptions
        } catch (SQLException e) {
            e.printStackTrace();
            //Always close the resultset as it is a temporary table of content
        } finally {
            try {
                if(resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException ef) {
                ef.printStackTrace();
                close();
            }
        }

      // Retuning the ArrayList of question objects found in database with given quiz id
        return questions;

    }

    //Method for loading options to a given question
    public ArrayList<Option> loadOptions(int questionId) {

        //Resultset to temporary contain values from SQL statement
        ResultSet resultSet = null;
        //Arraylist of Option object
        ArrayList<Option> options = new ArrayList<Option>();

        //Try-catch to avoid the program crashing on exceptions
        try {
            //SQL statement sendt to DB via. conncetion method.
            PreparedStatement loadQuestions = connection
                    .prepareStatement("SELECT * FROM `Option` WHERE question_id = ?");
            loadQuestions.setInt(1, questionId);
            //Resultset gets the value of the SQL statement
            resultSet = loadQuestions.executeQuery();

            //The method will run as long as the resultset contains more (next line)
            while (resultSet.next()) {
                //Adding values in resultset to option objet.
                Option option = new Option();
                option.setIdOption(resultSet.getInt("idOption"));
                option.setQuestionIdQuestion(resultSet.getInt("question_id"));
                option.setIsCorrect(resultSet.getInt("is_correct"));
                option.setOption(resultSet.getString("option"));
                //Adding option object with given parameter values to the arraylist of several option objects.
                options.add(option);
            }

            //Catch to avoid crashing on exceptions
        } catch (SQLException e) {
            e.printStackTrace();
        }
            //Always close the resultset as it is a temporary table of content
        finally {
            try {
                resultSet.close();
            } catch (SQLException ef) {
                ef.printStackTrace();
                close();
            }
            //Returning the arraylist of option objects
            return options;
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

}

