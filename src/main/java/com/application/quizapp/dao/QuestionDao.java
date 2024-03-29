package com.application.quizapp.dao;

import com.application.quizapp.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository  //(db işlemleri için gerekli metotlar)
public interface QuestionDao extends JpaRepository<Question, Integer> {

    // JPA bizim yerimize kategoriye göre soru getirebilir.
    List<Question> findByCategory(String category);

    @Query(value = "SELECT * FROM question q Where q.category=:category ORDER BY RANDOM() LIMIT :numQ", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);

    List<Question> findByRightAnswer(String right_answer);

}
