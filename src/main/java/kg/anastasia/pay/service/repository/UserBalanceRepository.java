package kg.anastasia.pay.service.repository;

import kg.anastasia.pay.service.entitys.UserBalance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBalanceRepository extends JpaRepository<UserBalance, Long> {

    Optional<UserBalance> findByUserId(Long userId);
}