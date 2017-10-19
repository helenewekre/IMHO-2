package server.controller;

import server.dbmanager.DbManager;
import server.models.Answer;
import server.models.Option;
import server.models.Question;
import server.models.Quiz;

import java.util.ArrayList;

public class UserController {


  private Quiz quiz = new Quiz();

  // Todo coments by peter
   public int getQuizResult(Quiz quiz) throws Exception {
        DbManager dbManager = new DbManager();
        Option option = new Option();
       Answer answer = new Answer();
        int result = 0;
        int nrQuestion = 0;
        int quizId = quiz.getIdQuiz();
        ArrayList<Option> options = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList();

         questions = dbManager.loadQuestions(quizId);

        for(int i = 0; i < questions.size(); i++){
            options = dbManager.getOption(i);
            for(int o = 0; o < options.size(); o++){
                option = options.get(o);
                if(option.getIsCorrect() == 1) {
                    if(answer.getIdAnswer() == option.getIdOption()){
                        result++; nrQuestion++;
                    }else nrQuestion++;
                }
            }
        }
       return result + nrQuestion;
        }

}