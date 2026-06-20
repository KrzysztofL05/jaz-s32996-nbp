package jaz_s32996_nbp.zaliczenie.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
public class CurrencyQuery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String currency;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigDecimal calculatedRate;

    private LocalDateTime queryDateTime;

    public CurrencyQuery() {
    }

    public CurrencyQuery(String currency,
                         LocalDate startDate,
                         LocalDate endDate,
                         BigDecimal calculatedRate,
                         LocalDateTime queryDateTime)
    {
        this.currency = currency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.calculatedRate = calculatedRate;
        this.queryDateTime = queryDateTime;
    }

    public Long getId() {
        return id;
    }

    public String getCurrency() {
        return currency;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public BigDecimal getCalculatedRate() {
        return calculatedRate;
    }

    public LocalDateTime getQueryDateTime() {
        return queryDateTime;
    }
}