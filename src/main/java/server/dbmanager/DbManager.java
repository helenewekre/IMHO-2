package server.dbmanager;

import server.models.Course;
import server.models.Question;
import server.models.Option;
import server.models.Quiz;
import server.models.User;
import server.utility.Globals;

import server.utility.Crypter;
import server.models.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

public class DbManager {

    // Creating the connection for the database
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";
    private static Connection connection = null;

    Crypter crypter = new Crypter();

    public DbManager() {
        Globals.log.writeLog(this.getClass().getName(), this, "Database connected", 2);

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

        //ResultSet and User to temporary contain values from SQL statement
        ResultSet resultSet = null;
        User user = null;

        //Try-catch method to avoid the program crashing on exceptions
        try {

            //SQL statement
            PreparedStatement authorizeUser = connection
                    .prepareStatement("SELECT * from User where username = ? AND password = ?");
            //Setting parameters for user object
            authorizeUser.setString(1, username);
            authorizeUser.setString(2, password);

            //ResultSet consisting parameters from SQL statement
            resultSet = authorizeUser.executeQuery();

            //Method will run as long as there is content in the next line of the resultSet
            while (resultSet.next()) {
                user = new User();
                user.setIdUser(resultSet.getInt("idUser"));
                user.setUsername(resultSet.getString("username"));
                user.setPassword(resultSet.getString("password"));
                user.setType(resultSet.getInt("type"));

            }
        //Exception to avoid crashing
        } catch (SQLException exception) {
            exception.printStackTrace();
        } finally {
            try {
                //closing the resultSet, because its a temporary table of content
                resultSet.close();
            } catch (SQLException exception) {
                exception.printStackTrace();
                close();
            }
        }
        return user;
    }

    // Method for creating a user - Boolean returned, which decides if the user is created or not
    public boolean createUser(User user) throws IllegalArgumentException {

        //Try-catch method to avoid the program crashing on exceptions
        try {

            //SQL statement
            PreparedStatement createUser = connection
                    .prepareStatement("INSERT INTO User (username, password) VALUES (?,?)");
            //Setting parameters for user object
            createUser.setString(1, user.getUsername());
            createUser.setString(2, user.getPassword());

            //rowsAffected
            int rowsAffected = createUser.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        //Exception to avoid crashing
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return false;
    }

    // Method for creating a quiz
    public boolean createQuiz(Quiz quiz) throws IllegalArgumentException {
        //Try-catch
        try {
            //SQL statement to create a quiz
            PreparedStatement createQuiz = connection
                    .prepareStatement("INSERT INTO Quiz (created_by, question_count, quiz_title, quiz_description, idCourse) VALUES (?,?,?,?,?)");
            //Setting parameters for quiz object
            createQuiz.setString(1, quiz.getCreatedBy());
            createQuiz.setInt(2, quiz.getQuestionCount());
            createQuiz.setString(3, quiz.getQuizTitle());
            createQuiz.setString(4, quiz.getQuizDescription());
            createQuiz.setInt(5, quiz.getIdCourse());

            int rowsAffected = createQuiz.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        //Exception to avoid crashing
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Method for creating a question
    public boolean createQuestion(Question question) throws IllegalArgumentException {
        //Try-catch
        try {
            //SQL statement
            PreparedStatement createQuestion = connection
                    .prepareStatement("INSERT INTO Question (question, quiz_id) VALUES (?, ?)");
            //Setting parameters
            createQuestion.setString(1, question.getQuestion());
            createQuestion.setInt(2, question.getQuizIdQuiz());

            int rowsAffected = createQuestion.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        //Exception to avoid crashing
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    /* Method for creating a option */
    public boolean createOption(Option option) throws IllegalArgumentException {
        //Try-catch
        try {
            //SQL statement
            PreparedStatement createOption = connection
                    .prepareStatement("INSERT INTO `Option` (`option`, question_id, is_correct) VALUES (?, ?, ?);");
                //Setting parameters for user object
                createOption.setString(1, option.getOptions());
                createOption.setInt(2, option.getQuestionIdQuestion());
                createOption.setInt(3, option.getIsCorrect());

                int rowsAffected = createOption.executeUpdate();

        if (rowsAffected == 1) {
            return true;
        }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method for loading courses
    public ArrayList<Course> loadCourses() {   //loadCourses
        ResultSet resultSet = null;
        ArrayList<Course> courses = new ArrayList<Course>();

        //Try-catch
        try {
            //SQL statement
            PreparedStatement loadCourse = connection.prepareStatement("SELECT * FROM Course");

            resultSet = loadCourse.executeQuery();

            while (resultSet.next()) {
                Course course = new Course();
                course.setIdCourse(resultSet.getInt("idCourse"));
                course.setCourseTitle(resultSet.getString("course_title"));
                courses.add(course);

            }

        //Exception to avoid crashing
        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            try {
                //closing the resultSet, because its a temporary table of content
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

        //Try-catch
        try {
            //SQL statement
            PreparedStatement loadQuizzes = connection
                    .prepareStatement("SELECT * FROM Quiz WHERE idCourse = ?");

            loadQuizzes.setInt(1, courseId);
            //resultSet gets the value of the SQL statement
            resultSet = loadQuizzes.executeQuery();

            //Method will run as long as there is content in the next line of the resultset
            while (resultSet.next()) {
                //Adding values in ResultSet to quiz object
                Quiz quiz = new Quiz();
                quiz.setIdQuiz(resultSet.getInt("idQuiz"));
                quiz.setCreatedBy(resultSet.getString("created_by"));
                quiz.setQuestionCount(resultSet.getInt("question_count"));
                quiz.setQuizTitle(resultSet.getString("quiz_description"));
                quiz.setIdCourse(resultSet.getInt("idCourse"));
                quizzes.add(quiz);

            }

        //Exception to avoid crashing
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                //closing the resultSet, because its a temporary table of content
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

        ///Try-catch
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
                //Creating question object
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

        //Try-catch
        try {
            //SQL statement
            PreparedStatement loadQuestions = connection
                    .prepareStatement("SELECT * FROM `Option` WHERE question_id = ?");
            loadQuestions.setInt(1, questionId);
            //Resultset gets the value of the SQL statement
            resultSet = loadQuestions.executeQuery();

            //Method will run as long as there is content in the next line of the resultset
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
        //Always close the resultSet as it is a temporary table of content
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


    //Method to get a specific userprofile based on a corresponding ID
    public User getUserProfile(int idUser) {

        ResultSet resultSet = null;
        User user = null;

        //Try-catch
        try {
            //SQL statement
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
        //Method for deleting a quiz and all it's sub-tables
    public boolean deleteQuiz(int idQuiz) throws IllegalArgumentException {
        //Try-catch
        try {
            //SQL statement that deletes a quiz and all it's sub-tables
            PreparedStatement deleteQuiz = connection
                    .prepareStatement("DELETE FROM Quiz WHERE idQuiz = ?");

            deleteQuiz.setInt(1, idQuiz);
            int rowsAffected = deleteQuiz.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //Method to get the number of corret answers of a quiz using quiz ID og user ID as parameters
    public int getNrCorrectAnswers(int quizID, int userID) {
        ResultSet resultSet = null;
        int score = 0;
        //Try-catch
        try {
            //SQL statement that gets all the correct answers the user have on a quiz
            //In the SELECT part we define that we need the DB to return quiz description and count. we use count
            PreparedStatement getNrCorrectAnswers = connection.prepareStatement("SELECT q.quiz_description, count(*)\n" +
                    "FROM user u\n" + // we define that it is from user, her after known as u.
                    "INNER JOIN answer a\n" + // we inner join the statement with answer. now know as a.
                    "ON u.idUser = a.user_id\n" + // and that is should do it where user id from the user table = user id from answer table
                    "INNER JOIN `option` o\n" + // the statement is continued with option o.
                    "ON a.option_id = o.idOption\n" +//where a option id = o option id.
                    "INNER JOIN question qt\n" + // same as before with question qt.
                    "ON o.question_id = qt.idQuestion\n" + // where o question id = qt question id.
                    "INNER JOIN quiz q\n" + // finally inner join with quiz q.
                    "ON qt.quiz_id = q.idQuiz\n" + // on qt quiz id = q id quiz
                    "WHERE quiz_id = ? \n" + // where quiz id = ?. ? is defined by the user.
                    "\tAND user_id = ?\n" + // and user id = + ?. also defined by the user.
                    "GROUP BY o.is_correct" // in the end it is sortet by o is correct. so the final count is returned.
            );

            getNrCorrectAnswers.setInt(1, quizID);
            getNrCorrectAnswers.setInt(2, userID);

            resultSet = getNrCorrectAnswers.executeQuery();


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

    //Method gets the number of questions using quiz ID as a parameter
    public int getNrQuestion(int quizID) {

        ResultSet resultSet = null;
        int nrQuestions = 0;

        //Try-catch
        try {
            //SQL statement that gets all the questions on a quiz
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

    public boolean deleteAnswer(int idUser) throws IllegalArgumentException {
        try {
            PreparedStatement deleteAnswer = connection
                    .prepareStatement("DELETE FROM Answer WHERE user_id = ?");

            deleteAnswer.setInt(1, idUser);
            int rowsAffected = deleteAnswer.executeUpdate();
            if (rowsAffected == 1) {
                return true;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}