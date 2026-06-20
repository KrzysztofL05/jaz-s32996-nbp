package jaz_s32996_nbp.zaliczenie.dto;

import java.math.BigDecimal;
import java.util.List;

public class NbpResponse {

    private String code;
    private List<Rate> rates;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public void setRates(List<Rate> rates) {
        this.rates = rates;
    }

    public static class Rate {
        private BigDecimal mid;

        public BigDecimal getMid() {
            return mid;
        }

        public void setMid(BigDecimal mid) {
            this.mid = mid;
        }
    }
}