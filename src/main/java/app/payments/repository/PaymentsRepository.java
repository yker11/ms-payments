package app.payments.repository;

import app.payments.models.Payment;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends ReactiveMongoRepository<Payment, String> {
}
