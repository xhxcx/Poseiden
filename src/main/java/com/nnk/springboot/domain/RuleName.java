package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Data
@Table(name = "rulename")
public class RuleName {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "name")
    private String name;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "description")
    private String description;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "json")
    private String json;

    @Size(max = 512, message = "Text should be 512 characters max")
    @Column(name = "template")
    private String template;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "sqlStr")
    private String sqlStr;

    @Size(max = 125, message = "Text should be 125 characters max")
    @Column(name = "sqlPart")
    private String sqlPart;
}
