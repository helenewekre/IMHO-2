package server.dbmanager;

import server.models.Course;
import server.models.Question;
import server.models.Quiz;
import server.resetdatabase.ResetDatabase;

import java.sql.*;
import java.util.ArrayList;

public class dbmanager3 {

    /*This ccode connects to database, remember last part of line 14 - solve timestamp issues for database*/
    private static final String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static Connection connection = null;


    private ResetDatabase resetdatabase;

    public dbmanager3() {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            System.out.println("Worked!");
        } catch (SQLException exception) {
            exception.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
            /*
            try {
                resultSet.close();
            } catch (SQLException ef) {
                ef.printStackTrace();
                close();
            }
            */

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




}