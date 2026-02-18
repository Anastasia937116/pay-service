package kg.anastasia.pay.service.entitys;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "operations")
@Data
public class PayOperation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "operation_date", nullable = false)
    private LocalDateTime operationDate;

    @Column(name = "operation_type", nullable = false)
    private String operationType;

    @Column(nullable = false)
    private BigDecimal amount;
}
