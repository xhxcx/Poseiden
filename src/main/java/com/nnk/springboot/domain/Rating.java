package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;


@Entity
@Data
@Table(name = "rating")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "moodysRating")
    private String moodysRating;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "sandPRating")
    private String sandPRating;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "fitchRating")
    private String fitchRating;

    @Column(name = "orderNumber")
    private Integer orderNumber;

}
