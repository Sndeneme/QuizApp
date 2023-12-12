package com.application.quizapp.controller;

import com.application.quizapp.model.Question;
import com.application.quizapp.model.QuestionWrapper;
import com.application.quizapp.service.QuizService;
import com.application.quizapp.model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class QuizController {

    @Autowired
    QuizService quizService;

    // Quiz Oluşturma
    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }

    //Oluşturulan Quiz Sorularını Listeleme
    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }
/*
    @GetMapping("get/{right_answer}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable String right_answer){
        return quizService.getQuizQuestions(right_answer);
    }

 */

    // Gönderilen cevapların doğruluğunu değerlendirme
    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }


    // delete Method deletebyId Id ye göre silecek
    /*
    public ResponseEntity<Integer> deleteById(Integer id) {
        QuizController questionDao;
        questionDao.deleteById(Integer id);
        return new ResponseEntity<>("success");
    } */
}
