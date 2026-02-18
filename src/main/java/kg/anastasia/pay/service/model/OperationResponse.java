package kg.anastasia.pay.service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationResponse {
    private int code;        // -1, 0 или 1, 2 и тд.
    private String message;  // текстовое сообщение
}