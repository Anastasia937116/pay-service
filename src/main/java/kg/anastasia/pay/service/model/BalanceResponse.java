package kg.anastasia.pay.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BalanceResponse {
    private int code;           // 1 или -1
    private String message;     // причина ошибки
    private BigDecimal balance; // текущий баланс
}
