package app.payments.accountclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class AccountClient {

    private String id;
    private String number;
    private Double lineAvailable;
    private Double lineUsed;
    private Double balancePast;
    private Date createDate;
    private String idClient;
    private String idProducts;
}
