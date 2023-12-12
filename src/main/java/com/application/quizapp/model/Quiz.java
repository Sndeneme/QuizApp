package com.application.quizapp.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Quiz {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    @SequenceGenerator(name="pk_sequence",sequenceName="quiz_id_seq")
    @GeneratedValue(strategy=GenerationType.SEQUENCE,generator="pk_sequence")
    private Integer id;
    private String title;

    @ManyToMany
    private List<Question> questions;

}
