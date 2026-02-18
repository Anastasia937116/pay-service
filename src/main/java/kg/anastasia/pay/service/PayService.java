package kg.anastasia.pay.service;

import kg.anastasia.pay.service.service.BankService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class PayService {
    public static void main(String[] args) {
        SpringApplication.run(PayService.class, args);
    }


    @Bean
    public CommandLineRunner run(BankService bankService) {
        return args -> {

            log.info("Баланс пользователя 1: {}", bankService.getBalance(1L));

            // Пополнение
            int putResult = bankService.putMoney(1L, new BigDecimal("200"));
            log.info("Результат пополнения: {}", putResult);

            // Списание
            int takeResult = bankService.takeMoney(1L, new BigDecimal("100"));
            log.info("Результат списания: {}", takeResult);

            // Перевод
            int transferResult = bankService.transferMoney(1L, 2L, new BigDecimal("50"));
            log.info("Результат перевода: {}", transferResult);

            // Проверяем баланс после операций
            log.info("Баланс пользователя 1 после операций: {}", bankService.getBalance(1L));
            log.info("Баланс пользователя 2 после операций: {}", bankService.getBalance(2L));
        };
    }

}
