package app.payments.serviceTest;

import app.payments.DataPayments;
import app.payments.accountclient.AccountServiceClient;
import app.payments.models.Payment;
import app.payments.repository.PaymentsRepository;
import app.payments.service.PaymentsServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentsServiceTest {

    @Mock
    private static PaymentsRepository paymentsRepository;
    private static PaymentsServiceImpl paymentsService;
    private static AccountServiceClient accountServiceClient;

    @BeforeAll
    public static void setUp(){
        paymentsRepository = mock(PaymentsRepository.class);
        paymentsService = new PaymentsServiceImpl(paymentsRepository, accountServiceClient);
    }

    @Test
    void getPaymentsTest() {

        Flux<Payment> payment = Flux.just(DataPayments.getList());

        when(paymentsService.findAll()).thenReturn(payment);

        Flux<Payment> respBody = paymentsService.findAll();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNext(DataPayments.getList())
                .verifyComplete();
    }

    @Test
    void findByIdPaymentTest() throws Exception{

        Mono<Payment> payment = Mono.just(DataPayments.getList());
        when(paymentsService.findById(any())).thenReturn(payment);

        Mono<Payment> respBody = paymentsService.findById(any());

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getId().equals("12233d"))
                .verifyComplete();
    }

   /* @Test
    void savePaymentThirdTest(){

        Payment payment = DataPayments.savePaymentThird();

        when(paymentsService.savePaymentThird(payment)).thenReturn(Mono.just(payment));

        Mono<Payment> respBody = paymentsService.savePaymentThird(payment);

        StepVerifier.create(respBody)
                .expectSubscription()
                //.expectNextMatches(p -> p.getIdAccount().equals(any()))
                .expectComplete();
    }*/
}
