package com.riskfocus.currencyconverter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "CURRENCY_CONVERSION_RATE")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CurrencyConversionRate {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "FROM_CURRENCY_ID", nullable = false, updatable = false)
    private Currency from;

    @NotNull
    @ManyToOne(optional = false)
    @JoinColumn(name = "TO_CURRENCY_ID", nullable = false, updatable = false)
    private Currency to;

    @NotNull
    @Digits(integer = 19, fraction = 14)
    @Column(name = "RATE", nullable = false)
    private BigDecimal rate;
}
