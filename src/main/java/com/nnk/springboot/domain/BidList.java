package com.nnk.springboot.domain;

import lombok.Data;
import org.springframework.beans.factory.annotation.Required;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = "bidlist")
public class BidList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "BidListId")
    private Integer bidListId;

    @NotNull
    @Size(max = 30)
    @Column(name = "account")
    private String account;

    @NotNull
    @Size(max = 30)
    @Column(name = "type")
    private String type;

    @Column(name = "bidQuantity")
    private Double bidQuantity;

    @Column(name = "askQuantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Size(max = 125)
    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "bidListDate")
    private Timestamp bidListDate;

    @Size(max = 125)
    @Column(name = "commentary")
    private String commentary;

    @Size(max = 125)
    @Column(name = "security")
    private String security;

    @Size(max = 10)
    @Column(name = "status")
    private String status;

    @Size(max = 125)
    @Column(name = "trader")
    private String trader;

    @Size(max = 125)
    @Column(name = "book")
    private String book;

    @Size(max = 125)
    @Column(name = "creationName")
    private String creationName;

    @Column(name = "creationDate")
    private Timestamp creationDate;

    @Size(max = 125)
    @Column(name = "revisionName")
    private String revisionName;

    @Column(name = "revisionDate")
    private Timestamp revisionDate;

    @Size(max = 125)
    @Column(name = "dealName")
    private String dealName;

    @Size(max = 125)
    @Column(name = "dealType")
    private String dealType;

    @Size(max = 125)
    @Column(name = "sourceListId")
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side")
    private String side;
}
