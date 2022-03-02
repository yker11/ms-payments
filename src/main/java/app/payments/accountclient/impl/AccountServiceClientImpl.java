package app.payments.accountclient.impl;

import app.payments.accountclient.AccountServiceClient;
import app.payments.accountclient.dto.AccountClient;
import app.payments.config.properties.AppConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class AccountServiceClientImpl implements AccountServiceClient {

    private final AppConfig properties;
    private final WebClient webClient;

    public AccountServiceClientImpl(AppConfig properties) {
        this.properties = properties;
        this.webClient = WebClient.create(properties.getUrl());
    }

    @Override
    public Mono<AccountClient> findById(String idAccount) {
        log.info("Obteniendo id acccount");
        return webClient.get()
                .uri(properties.getPath().concat("/getById/").concat(idAccount))
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(AccountClient.class);
    }
}
