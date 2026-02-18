package kg.anastasia.pay.service.repository;

import kg.anastasia.pay.service.entitys.PayOperation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OperationRepository extends JpaRepository<PayOperation, Long> {

    List<PayOperation> findByUserId(Long userId);

    List<PayOperation> findByUserIdAndOperationDateBetween(
            Long userId,
            LocalDateTime start,
            LocalDateTime end
    );
}
