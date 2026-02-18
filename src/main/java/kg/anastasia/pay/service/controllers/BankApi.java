package kg.anastasia.pay.service.controllers;


import kg.anastasia.pay.service.model.BalanceResponse;
import kg.anastasia.pay.service.model.OperationInfo;
import kg.anastasia.pay.service.service.BankService;
import org.springframework.web.bind.annotation.*;
import kg.anastasia.pay.service.model.OperationResponse;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api")
public class BankApi {

    private final BankService bankService;

    public BankApi(BankService bankService) {
        this.bankService = bankService;
    }

    @GetMapping("/getBalance")
    public BalanceResponse getBalance(@RequestParam Long userId) {

        var balance = bankService.getBalance(userId);

        if (balance.compareTo(BigDecimal.valueOf(-1)) == 0) {
            return new BalanceResponse(-1, "Пользователь не найден", null);
        }

        return new BalanceResponse(1, "Баланс получен", balance);
    }


    @PostMapping("/takeMoney")
    public OperationResponse takeMoney(@RequestParam Long userId, @RequestParam Double amount) {

        int result = bankService.takeMoney(userId, BigDecimal.valueOf(amount));

        switch (result) {
            case -2:
                return new OperationResponse(0, "Сумма должна быть больше нуля");

            case -1:
                return new OperationResponse(0, "Пользователь не найден");

            case 0:
                return new OperationResponse(0, "Недостаточно средств");

            case 1:
                return new OperationResponse(1, "Списание выполнено успешно");

            default:
                return new OperationResponse(0, "Неизвестная ошибка");
        }
    }


    @PostMapping("/putMoney")
    public OperationResponse putMoney(@RequestParam Long userId, @RequestParam Double amount) {

        int result = bankService.putMoney(userId, BigDecimal.valueOf(amount));

        switch (result) {
            case -2:
                return new OperationResponse(0, "Сумма должна быть больше нуля");

            case -1:
                return new OperationResponse(0, "Пользователь не найден");

            case 1:
                return new OperationResponse(1, "Баланс успешно пополнен");

            default:
                return new OperationResponse(0, "Неизвестная ошибка");
        }
    }


    @GetMapping("/getOperationList")
    public List<OperationInfo> getOperationList(@RequestParam Long userId, @RequestParam(required = false) String startDate, @RequestParam(required = false) String endDate) {

        LocalDateTime start = null;
        LocalDateTime end = null;

        if (startDate != null && endDate != null) {
            start = LocalDateTime.parse(startDate);
            end = LocalDateTime.parse(endDate);
        }

        return bankService.getOperations(userId, start, end)
                .stream()
                .map(op -> {
                    OperationInfo info = new OperationInfo();
                    info.setDate(op.getOperationDate());
                    info.setType(op.getOperationType());
                    info.setAmount(op.getAmount());
                    return info;
                })
                .toList();
    }


    @PostMapping("/transferMoney")
    public OperationResponse transferMoney(@RequestParam Long fromUserId, @RequestParam Long toUserId, @RequestParam Double amount) {

        int result = bankService.transferMoney(fromUserId, toUserId, BigDecimal.valueOf(amount));

        switch (result) {

            case -4:
                return new OperationResponse(0, "Нельзя переводить деньги самому себе");

            case -3:
                return new OperationResponse(0, "Сумма должна быть больше нуля");

            case -1:
                return new OperationResponse(0, "Отправитель не найден");

            case -2:
                return new OperationResponse(0, "Получатель не найден");

            case 0:
                return new OperationResponse(0, "Недостаточно средств");

            case 1:
                return new OperationResponse(1, "Перевод выполнен успешно");

            default:
                return new OperationResponse(0, "Неизвестная ошибка");
        }
    }

}
