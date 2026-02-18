package kg.anastasia.pay.service.service;

import kg.anastasia.pay.service.entitys.PayOperation;
import kg.anastasia.pay.service.repository.OperationRepository;
import org.springframework.transaction.annotation.Transactional;
import kg.anastasia.pay.service.entitys.UserBalance;
import kg.anastasia.pay.service.repository.UserBalanceRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BankService {

    private final UserBalanceRepository balanceRepository;
    private final OperationRepository operationRepository;

    public BankService(UserBalanceRepository balanceRepository, OperationRepository operationRepository) {
        this.balanceRepository = balanceRepository;
        this.operationRepository = operationRepository;
    }

    public BigDecimal getBalance(Long userId) {

        return balanceRepository.findByUserId(userId)
                .map(UserBalance::getBalance)
                .orElse(BigDecimal.valueOf(-1));
    }

    @Transactional
    public int takeMoney(Long userId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return -2;
        }

        Optional<UserBalance> optionalUser = balanceRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            return -1;
        }

        UserBalance user = optionalUser.get();

        if (user.getBalance().compareTo(amount) < 0) {
            return 0;
        }

        user.setBalance(user.getBalance().subtract(amount));

        balanceRepository.save(user);

        PayOperation operation = new PayOperation();
        operation.setUserId(userId);
        operation.setOperationDate(LocalDateTime.now());
        operation.setOperationType("Снятие со счета");
        operation.setAmount(amount);

        operationRepository.save(operation);

        return 1;
    }


    @Transactional
    public int putMoney(Long userId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return -2;
        }

        Optional<UserBalance> optionalUser = balanceRepository.findByUserId(userId);

        if (optionalUser.isEmpty()) {
            return -1;
        }

        UserBalance user = optionalUser.get();

        user.setBalance(user.getBalance().add(amount));

        balanceRepository.save(user);

        PayOperation operation = new PayOperation();
        operation.setUserId(userId);
        operation.setOperationDate(LocalDateTime.now());
        operation.setOperationType("Пополнение счета");
        operation.setAmount(amount);

        operationRepository.save(operation);

        return 1;
    }


    public List<PayOperation> getOperations(Long userId, LocalDateTime start, LocalDateTime end) {

        if (start != null && end != null) {
            return operationRepository.findByUserIdAndOperationDateBetween(userId, start, end);
        }

        return operationRepository.findByUserId(userId);
    }


    @Transactional
    public int transferMoney(Long fromUserId, Long toUserId, BigDecimal amount) {

        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            return -3; // некорректная сумма
        }

        if (fromUserId.equals(toUserId)) {
            return -4; // перевод самому себе
        }

        Optional<UserBalance> fromOptional = balanceRepository.findByUserId(fromUserId);
        Optional<UserBalance> toOptional = balanceRepository.findByUserId(toUserId);

        if (fromOptional.isEmpty()) {
            return -1; // отправитель не найден
        }

        if (toOptional.isEmpty()) {
            return -2; // получатель не найден
        }

        UserBalance fromUser = fromOptional.get();
        UserBalance toUser = toOptional.get();

        if (fromUser.getBalance().compareTo(amount) < 0) {
            return 0; // недостаточно средств
        }

        // списываем
        fromUser.setBalance(fromUser.getBalance().subtract(amount));
        // зачисляем
        toUser.setBalance(toUser.getBalance().add(amount));

        balanceRepository.save(fromUser);
        balanceRepository.save(toUser);

        // операция для отправителя
        PayOperation opFrom = new PayOperation();
        opFrom.setUserId(fromUserId);
        opFrom.setOperationDate(LocalDateTime.now());
        opFrom.setOperationType("перевод другому клиенту");
        opFrom.setAmount(amount);
        operationRepository.save(opFrom);

        // операция для получателя
        PayOperation opTo = new PayOperation();
        opTo.setUserId(toUserId);
        opTo.setOperationDate(LocalDateTime.now());
        opTo.setOperationType("перевод от другого клиента вам");
        opTo.setAmount(amount);
        operationRepository.save(opTo);

        return 1;
    }


}