package service.custom;

import model.Payment;
import service.SuperService;

import java.util.List;

public interface BillingBo extends SuperService {
    String makePayment(Payment payment);
    boolean updatePayment(Payment payment);
    Payment searchPayment(Payment payment);
    List<Payment> getAll();
    boolean deletePayment(Payment payment);

    Payment getDetails(Integer paymentId);

}
