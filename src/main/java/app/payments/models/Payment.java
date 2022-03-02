package app.payments.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Payments")
public class Payment {

    @Id
    private String id;
    private Double amountPay;
    private String nameThird;
    private String numberIdentity;
    @DateTimeFormat(pattern = "yyyy-mm-dd")
    private Date createDate;
    private String idAccount;

}
