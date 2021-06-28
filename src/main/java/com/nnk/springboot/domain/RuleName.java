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
    @Column(name = "TradeId")
    private Integer id;

    @Size(max = 125)
    @Column(name = "name")
    private String name;

    @Size(max = 125)
    @Column(name = "description")
    private String description;

    @Size(max = 125)
    @Column(name = "json")
    private String json;

    @Size(max = 512)
    @Column(name = "template")
    private String template;

    @Size(max = 125)
    @Column(name = "sqlStr")
    private String sqlStr;

    @Size(max = 125)
    @Column(name = "sqlPart")
    private String sqlPart;
}
