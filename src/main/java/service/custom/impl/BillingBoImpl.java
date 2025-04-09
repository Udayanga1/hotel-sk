package service.custom.impl;

import model.Payment;
import model.Reservation;
import repository.DaoFactory;
import repository.custom.BillingDao;
import service.custom.BillingBo;
import util.DaoType;

import java.time.temporal.ChronoUnit;
import java.util.List;

public class BillingBoImpl implements BillingBo {
    BillingDao billingDao = DaoFactory.getInstance().getDaoType(DaoType.BILL);

    @Override
    public String makePayment(Payment payment) {

        String reservationStatus = billingDao.getReservationStatus(payment.getReservationNo());

        if (reservationStatus.equals("paid")){
            return "already paid";
        } else {
            Boolean isPaymentMade = billingDao.save(payment);
            if (isPaymentMade) {
                billingDao.updateReservationStatus(payment.getReservationNo(), "paid");
                return "success";
            }
        }

        return null;
    }

    @Override
    public boolean updatePayment(Payment payment) {
        return false;
    }

    @Override
    public Payment searchPayment(Payment payment) {
        return null;
    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public boolean deletePayment(Payment payment) {
        return false;
    }

    @Override
    public Payment getDetails(Integer reservationNo) {
        Reservation reservation = billingDao.getReservationDetails(reservationNo);
        if(reservation!=null){
            Double roomPricePerDay = billingDao.getRoomPrice(reservation.getRoomNo());
            long daysBetween = ChronoUnit.DAYS.between(reservation.getCheckInDate(), reservation.getCheckOutDate());

            return new Payment(
                    null,
                    reservationNo,
                    null,
                    (daysBetween+1)*roomPricePerDay,
                    null,
                    null
            );
        }

        return null;
    }
}
