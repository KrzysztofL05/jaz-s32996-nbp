package jaz_s32996_nbp.zaliczenie.service;

import jaz_s32996_nbp.zaliczenie.model.CurrencyQuery;
import jaz_s32996_nbp.zaliczenie.dto.NbpResponse;
import jaz_s32996_nbp.zaliczenie.repository.CurrencyQueryRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class NbpService {

    private final CurrencyQueryRepository repository;
    private final RestTemplate restTemplate;

    public NbpService(CurrencyQueryRepository repository,
                      RestTemplate restTemplate) {
        this.repository = repository;
        this.restTemplate = restTemplate;
    }

    public BigDecimal calculateAverageRate(String currency, LocalDate startDate, LocalDate endDate) {

        if (startDate.isAfter(endDate)) {
            throw new RuntimeException("Data początkowa nie może być późniejsza od końcowej");
        }

        if (startDate.plusDays(367).isBefore(endDate)) {
            throw new RuntimeException("Zakres dat nie może przekraczać 367 dni");
        }

        String url = "http://api.nbp.pl/api/exchangerates/rates/a/"
                + currency + "/"
                + startDate + "/"
                + endDate + "/?format=json";

        try {
            NbpResponse response = restTemplate.getForObject(url, NbpResponse.class);

            if (response == null || response.getRates() == null || response.getRates().isEmpty()) {
                throw new RuntimeException("Brak danych z NBP.");
            }

            BigDecimal sum = response.getRates()
                    .stream()
                    .map(NbpResponse.Rate::getMid)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);

            BigDecimal average = sum.divide(
                    BigDecimal.valueOf(response.getRates().size()),
                    4,
                    RoundingMode.HALF_UP
            );

            CurrencyQuery query = new CurrencyQuery(
                    currency.toUpperCase(),
                    startDate,
                    endDate,
                    average,
                    LocalDateTime.now()
            );

            repository.save(query);

            return average;

        } catch (HttpClientErrorException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                throw new RuntimeException("Nie znaleziono danych dla podanej waluty lub zakresu dat.");
            }

            if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                throw new RuntimeException("Niepoprawne zapytanie do API NBP.");
            }

            throw new RuntimeException("Błąd API NBP: " + e.getStatusCode());
        }
    }
}