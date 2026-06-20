package jaz_s32996_nbp.zaliczenie.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import jaz_s32996_nbp.zaliczenie.service.NbpService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@RestController
@RequestMapping("/api/rates")
public class NbpController {

    private final NbpService nbpService;

    public NbpController(NbpService nbpService) {
        this.nbpService = nbpService;
    }

    @Operation(summary = "Oblicza średni kurs waluty z API NBP")
    @GetMapping("/average")
    public BigDecimal getAverageRate(
            @Parameter(example = "USD")
            @RequestParam String currency,

            @Parameter(example = "2024-01-01")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,

            @Parameter(example = "2024-01-31")
            @RequestParam
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
    ) {
        return nbpService.calculateAverageRate(currency, startDate, endDate);
    }
}