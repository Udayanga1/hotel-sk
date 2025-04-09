package repository.custom;

import model.Reservation;
import repository.CrudRepository;

public interface ReservationDao extends CrudRepository<Reservation, Integer> {
    String getCustomerName(Integer cusId);

    String getRoomStatus(Integer roomNo);

    void makeRoomOccupied(Integer roomNo);

    Double getRoomPrice(Integer roomNo);

    String getReservationStatus(Integer reservationNo);
}
