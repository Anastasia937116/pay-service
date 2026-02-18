package kg.anastasia.pay.service;

import kg.anastasia.pay.service.entitys.UserBalance;
import kg.anastasia.pay.service.repository.OperationRepository;
import kg.anastasia.pay.service.repository.UserBalanceRepository;
import kg.anastasia.pay.service.service.BankService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BankServiceTest {

    @Mock
    private UserBalanceRepository balanceRepository;

    @Mock
    private OperationRepository operationRepository;

    @InjectMocks
    private BankService bankService;

    @Test
    void getBalance_userExists() {
        UserBalance user = new UserBalance();
        user.setUserId(1L);
        user.setBalance(new BigDecimal("1000"));

        when(balanceRepository.findByUserId(1L))
                .thenReturn(Optional.of(user));

        BigDecimal result = bankService.getBalance(1L);

        assertEquals(new BigDecimal("1000"), result);
    }

    @Test
    void putMoney_success() {
        UserBalance user = new UserBalance();
        user.setUserId(1L);
        user.setBalance(new BigDecimal("1000"));

        when(balanceRepository.findByUserId(1L))
                .thenReturn(Optional.of(user));

        int result = bankService.putMoney(1L, new BigDecimal("200"));

        assertEquals(1, result);
        assertEquals(new BigDecimal("1200"), user.getBalance());
    }
}
