package com.nnk.springboot.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


@Entity
@Data
@Table(name = "trade")
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "TradeId")
    private Integer tradeId;

    @NotNull
    @Size(max = 30)
    @Column(name = "account")
    private String account;

    @NotNull
    @Size(max = 30)
    @Column(name = "type")
    private String type;

    @Column(name = "buyQuantity")
    private Double buyQuantity;

    @Column(name = "sellQuantity")
    private Double sellQuantity;

    @Column(name = "buyPrice")
    private Double buyPrice;

    @Column(name = "sellPrice")
    private Double sellPrice;

    @Column(name = "tradeDate")
    private Timestamp tradeDate;

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
    @Column(name = "benchmark")
    private String benchmark;

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
