package app.payments.accountclient;

import app.payments.accountclient.dto.AccountClient;
import reactor.core.publisher.Mono;

public interface AccountServiceClient {

    Mono<AccountClient> findById(String idAccount);
}
