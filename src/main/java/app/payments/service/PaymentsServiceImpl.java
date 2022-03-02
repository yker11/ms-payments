package app.payments.service;

import app.payments.accountclient.AccountServiceClient;
import app.payments.models.Payment;
import app.payments.repository.PaymentsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class PaymentsServiceImpl implements IPaymentsService{

    private final PaymentsRepository paymentsRepository;
    private final AccountServiceClient accountServiceClient;

    @Override
    public Flux<Payment> findAll() {
        return paymentsRepository.findAll();
    }

    @Override
    public Mono<Payment> findById(String id) {
        return paymentsRepository.findById(id);
    }

    @Override
    public Mono<Payment> savePaymentThird(Payment payment) {
        return accountServiceClient.findById(payment.getIdAccount())
                .flatMap(a -> {
                    Payment payDto = new Payment();
                    payDto.setAmountPay(payment.getAmountPay());
                    payDto.setNameThird(payment.getNameThird());
                    payDto.setNumberIdentity(payment.getNumberIdentity());
                    payDto.setCreateDate(new Date());
                    payDto.setIdAccount(a.getId());
                    return paymentsRepository.save(payDto);
                });
    }
}
