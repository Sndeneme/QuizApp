package com.application.quizapp.service;

import com.application.quizapp.dao.QuestionDao;
import com.application.quizapp.dao.QuizDao;
import com.application.quizapp.model.Question;
import com.application.quizapp.model.QuestionWrapper;
import com.application.quizapp.model.Quiz;
import com.application.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        Optional<Quiz> quizOptional = quizDao.findById(id);
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            List<Question> questionsFromDB = quiz.getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
/*
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(String right_answer) {
        Optional<Quiz> quizOptional = quizDao.findById();
        if (quizOptional.isPresent()) {
            Quiz quiz = quizOptional.get();
            List<Question> questionsFromDB = quiz.getQuestions();
            List<QuestionWrapper> questionsForUser = new ArrayList<>();
            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
                questionsForUser.add(qw);
            }
            return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

 */

    // Doğru Sayısı
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Quiz quiz = quizDao.findById(id).get();
        List<Question> questions = quiz.getQuestions();
        int right = 0;
        int i = 0;
        for (Response response : responses) {
            if(response.getResponse().equals(questions.get(i).getRight_answer()))
                right++;
// yanlış soruyu göruntuleme?
            i++;
        }
            return new ResponseEntity<>(right, HttpStatus.OK);
    }


    /*
    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> responses) {
        Optional<Quiz> optionalQuiz = quizDao.findById(id);
        if (optionalQuiz.isPresent()) {
            Quiz quiz = optionalQuiz.get();
            List<Question> questions = quiz.getQuestions();

            int right = 0;

            int numOfResponses = Math.min(responses.size(), questions.size());

            for (int i = 0; i < numOfResponses; i++) {
                Response response = responses.get(i);
                Question question = questions.get(i);

                if (response.getResponse().equals(question.getRight_answer())){
                    right++;
                }
            }
            return new ResponseEntity<>(right, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    */

}

