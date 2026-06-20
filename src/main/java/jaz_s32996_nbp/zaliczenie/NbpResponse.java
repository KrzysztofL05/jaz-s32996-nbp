package jaz_s32996_nbp.zaliczenie;

import java.math.BigDecimal;
import java.util.List;

public class NbpResponse {

    private String code;
    private List<Rate> rates;

    public String getCode() {
        return code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public static class Rate {
        private BigDecimal mid;

        public BigDecimal getMid() {
            return mid;
        }
    }
}