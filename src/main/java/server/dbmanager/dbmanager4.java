package server.dbmanager;

import server.models.Option;
import server.models.Question;
import server.models.User;

import java.sql.*;
import java.util.ArrayList;


public class dbmanager4 {

    private static String URL = "jdbc:mysql://localhost:3306/quizDB?useSSL=false&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private static String USERNAME = "root";
    private static String PASSWORD = "";
    private static Connection connection = null;

    public dbmanager4() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
    //Closes the connection to the databases
    private static void close(){
        try{
            connection.close();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
    //creates an arrayList based on questions and use the quiz ID, to find the corresponding questions
    public ArrayList<Question> getQuestion(int quizId){
        ResultSet resultSet = null;
        ArrayList<Question> questions = new ArrayList<>();

        //PreparedStatements which communicates with the database
        try{
            PreparedStatement getQuestion = connection.prepareStatement("SELECT * FROM Question WHERE quiz_id = ?");

            getQuestion.setInt(1,quizId);
            resultSet = getQuestion.executeQuery();

            //add all tables from DB to specific questions and finally add the question to the array.
            //the result.next() does this for each question.
            while(resultSet.next()){
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
            } catch (SQLException ex) {
                ex.printStackTrace();
                close();
            }

        }
        //returns to the array
        return questions;

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

}