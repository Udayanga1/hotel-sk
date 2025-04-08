package service.custom;

import model.Payment;
import service.SuperService;

public interface BillingBo extends SuperService {
    Payment getDetails(Integer roomNo);

}
