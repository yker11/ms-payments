package app.payments.service;

import app.payments.accountclient.dto.AccountClient;
import app.payments.models.Payment;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IPaymentsService {

    Flux<Payment> findAll();

    Mono<Payment> findById(String id);

    Mono<Payment> savePaymentThird(Payment payment);



}
