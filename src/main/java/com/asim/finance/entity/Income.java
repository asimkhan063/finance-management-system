package com.asim.finance.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Entity
@Table(name="income")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Income {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String title;


    private Double amount;


    private String source;


    private LocalDate date;


    private String description;



    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

}