package com.application.quizapp.model;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class Response {  // quiz controller içinde kullanılıyor
    private Integer questionid;
    private String response;

    public Integer getQuestionId() {
        return this.questionid;
    }
}
