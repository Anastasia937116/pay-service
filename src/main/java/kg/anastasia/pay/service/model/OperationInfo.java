package kg.anastasia.pay.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationInfo {
    private LocalDateTime date;
    private String type;
    private BigDecimal amount;
}
