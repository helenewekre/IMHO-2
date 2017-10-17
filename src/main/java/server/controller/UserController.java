package server.controller;

import server.dbmanager.dbmanager4;
import server.models.Answer;
import server.models.Option;
import server.models.Question;
import server.models.Quiz;

import java.util.ArrayList;

public class UserController {

  private Quiz quiz = new Quiz();

  // Todo coments by peter
   public int getQuizResult(Quiz quiz) throws Exception {
        dbmanager4 db4 = new dbmanager4();
        Option option = new Option();
       Answer answer = new Answer();
        int result = 0;
        int nrQuestion = 0;
        int quizId = quiz.getIdQuiz();
        ArrayList<Option> options = new ArrayList<>();
        ArrayList<Question> questions = new ArrayList();

         questions = db4.getQuestion(quizId);

        for(int i = 0; i < questions.size(); i++){
            options = db4.getOption(i);
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