package com.riskfocus.currencyconverter.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Entity
@Table(name = "CURRENCY")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Immutable
public class Currency {

    @Id
    @GeneratedValue(strategy = SEQUENCE)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[A-Z]{3}$")
    @Column(name = "CODE", length = 3, nullable = false, unique = true, updatable = false)
    private String code;
}
