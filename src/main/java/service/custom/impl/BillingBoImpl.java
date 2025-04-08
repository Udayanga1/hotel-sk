package service.custom.impl;

import model.Payment;
import model.Reservation;
import repository.DaoFactory;
import repository.custom.BillingDao;
import service.custom.BillingBo;
import util.DaoType;

import java.time.temporal.ChronoUnit;

public class BillingBoImpl implements BillingBo {
    BillingDao billingDao = DaoFactory.getInstance().getDaoType(DaoType.BILL);

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
