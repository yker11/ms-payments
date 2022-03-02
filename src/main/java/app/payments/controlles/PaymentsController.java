package app.payments.controlles;

import app.payments.models.Payment;
import app.payments.service.PaymentsServiceImpl;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/payments")
public class PaymentsController {

    private final PaymentsServiceImpl paymentsService;

    @CircuitBreaker(name="payments", fallbackMethod = "fallback")
    @TimeLimiter(name="payments")
    @PostMapping
    public Mono<ResponseEntity<Payment>>  savePaymentThird(@RequestBody Payment payment){
        return paymentsService.savePaymentThird(payment)
                .map(p -> ResponseEntity.created(URI.create("/payments/".concat(p.getId())))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p)).defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @CircuitBreaker(name="payments", fallbackMethod = "fallback")
    @TimeLimiter(name="payments")
    @GetMapping
    public Mono<ResponseEntity<Flux<Payment>>> getPayments(){
        log.info("iniciando lista");
        return Mono.just(
                ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(paymentsService.findAll()));
    }

    @CircuitBreaker(name="payments", fallbackMethod = "fallback")
    @TimeLimiter(name="payments")
    @GetMapping("/{id}")
    public Mono<ResponseEntity<Payment>> getById(@PathVariable String id){
        return paymentsService.findById(id)
                .map(p -> ResponseEntity.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(p))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    private String fallback(HttpServerErrorException ex) {
        return "Response 200, fallback method for error:  " + ex.getMessage();
    }


}
