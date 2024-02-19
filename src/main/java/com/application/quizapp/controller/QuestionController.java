package com.application.quizapp.controller;

import com.application.quizapp.model.Question;
import com.application.quizapp.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("question")
public class QuestionController {

    @Autowired  // aralarında bağımlılık olan sınıflar varsa bu sınıfların aralarındaki bağımlılığı otomatik olarak sağlar.
    QuestionService questionService;

    // Tüm Soruları Listeleme
    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    //Kategoriye Göre Listeleme
    @GetMapping("category/{category}")
    public ResponseEntity<List<Question>> getQuestionByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    // Doğru cevaba göre listeleme
    @GetMapping("right/{right_answer}")
    public ResponseEntity<List<Question>> getQuestionByRight(@PathVariable String right_answer){
        return questionService.getQuestionsByRight(right_answer);
    }


    // Soru Ekleme (RequestBody annotation ile Json formatında gönderdiğiğimiz veriyi alır)
    @PostMapping("add")
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    // silme işlemi için eklendi

    @DeleteMapping("deleteQuestion/{id}")
    public String deleteQuestionById(@PathVariable("id") Integer id) {
        return questionService.deleteQuestionById(id);
    }
}
