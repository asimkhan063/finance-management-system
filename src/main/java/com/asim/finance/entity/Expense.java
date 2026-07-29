package com.asim.finance.entity;

import jakarta.persistence.*;
import lombok.*;
import com.fasterxml.jackson.annotation.JsonBackReference;

import java.time.LocalDate;

@Entity
@Table(name="expenses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Expense {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;


    private Double amount;


    private String category;


    private LocalDate date;


    private String description;


    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonBackReference
    private User user;

}