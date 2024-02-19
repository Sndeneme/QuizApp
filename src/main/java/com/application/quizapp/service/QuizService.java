package com.application.quizapp.service;

import com.application.quizapp.dao.QuestionDao;
import com.application.quizapp.dao.QuizDao;
import com.application.quizapp.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;

@Service
public class QuizService {

    @Autowired
    QuizDao quizDao;


    @Autowired
    QuestionDao questionDao;

    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("Success", HttpStatus.CREATED);

        //http://localhost:8080/quiz/create?category=Java&numQ=5&title=JQuiz  Quiz oluşturma
    }

    /*  NoSuchElementException
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q : questionsFromDB){
            QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(qw);
        }


        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);

    } */

    // Quiz Sorularını getirecek
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quizOptional = quizDao.findById(id); //verilen id'ye sahip quiz bulunmaya çalışılıyor
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();  // quiz alınıyor
            List<Question> questionsFromDB = quiz.getQuestions();  // quiz içindeki sorular alınıyor
            List<QuestionWrapper> questionsForUser = new ArrayList<>(); // istenen alanları döndürecek şekilde veriyi oluşturuyor.
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);  // sorular questionforusera ekleniyor.
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }


    // Doğru Sayısı
/*
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;

                i++;
            }
        }
        return new ResponseEntity<>(right, HttpStatus.OK); // rightanswer dönecek count değil
    }
 */

     // düzenlenmiş  ??? question service içinde olması gerek????
   public Question getQuestionById(Integer questionId){
        Optional<Question> optionalQuestion = questionDao.findById(questionId);
        return optionalQuestion.orElse(null);
   }


    // yanlış cevaplanan soruların filtrelenip döndürülmesi
    public ResponseEntity<List<Question>> getIncorrectlyAnsweredQuestions(Integer id, List<Response> responses) {  // (quiz kimliği ve cevapların listesi)
        Quiz quiz = quizDao.findById(id).orElse(null);
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quiz.getQuestions();
        List<Question> incorrectQuestions = new ArrayList<>();

        if (questions.size() != responses.size()) {  // soru ve cevap sayısının eşit olup olmadığının kontrolü
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            Response userResponse = responses.get(i);

            if (!userResponse.getResponse().equals(question.getRightAnswer())) {
                incorrectQuestions.add(question);
            }
        }

        return new ResponseEntity<>(incorrectQuestions, HttpStatus.OK);
    }

}

