package repository.custom;

import model.Payment;
import model.Reservation;
import repository.CrudRepository;

public interface BillingDao extends CrudRepository<Payment, Integer> {
    Reservation getReservationDetails(Integer reservationNo);

    Double getRoomPrice(Integer roomNo);

    String getReservationStatus(Integer reservationNo);

    void updateReservationStatus(Integer reservationNo, String status);
}
