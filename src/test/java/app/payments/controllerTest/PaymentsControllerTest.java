package app.payments.controllerTest;

import app.payments.DataPayments;
import app.payments.controlles.PaymentsController;
import app.payments.models.Payment;
import app.payments.service.PaymentsServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentsControllerTest {

    private static WebTestClient webTestClient;
    @Mock
    private static PaymentsServiceImpl paymentsService;

    @BeforeAll
    public static void setUp(){
        paymentsService = mock(PaymentsServiceImpl.class);
        webTestClient = WebTestClient.bindToController(new PaymentsController(paymentsService))
                .configureClient()
                .baseUrl("/payments")
                .build();
    }

    @Test
    void savePaymentThirdTest() throws Exception{
        Payment pay = DataPayments.savePaymentThird();

        when(paymentsService.savePaymentThird(pay)).thenReturn(Mono.just(pay));

        Mono<Payment> respBody = paymentsService.savePaymentThird(pay);

        StepVerifier.create(respBody)
                .expectSubscription()
                //.expectNext(Data.getList())
                .expectComplete();
    }

    @Test
    void getPaymentsTest() {

        Flux<Payment> pay = Flux.just(DataPayments.getList());

        when(paymentsService.findAll()).thenReturn(pay);

        Flux<Payment> respBody = webTestClient.get()
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk() //200
                .returnResult(Payment.class)
                .getResponseBody();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNext(DataPayments.getList())
                .verifyComplete();
    }

    @Test
    void findByIdPaymentsTest() throws Exception{

        Mono<Payment> pay = Mono.just(DataPayments.getList());
        when(paymentsService.findById(any())).thenReturn(pay);

        Flux<Payment> respBody = webTestClient.get().uri("/12233d")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .returnResult(Payment.class)
                .getResponseBody();

        StepVerifier.create(respBody)
                .expectSubscription()
                .expectNextMatches(p -> p.getId().equals("12233d"))
                .verifyComplete();
    }
}
