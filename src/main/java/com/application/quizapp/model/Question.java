package com.application.quizapp.model;

//import jakarta.persistence.Entity;
//import jakarta.persistence.GeneratedValue;
//import jakarta.persistence.GenerationType;
//import jakarta.persistence.Id;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Question {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="pk_sequence",sequenceName="question_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    private Integer id;
    private String category;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String right_answer;
    private String difficultylevel;


}
