package service.custom;

import model.Reservation;
import service.SuperService;

import java.util.List;

public interface ReservationBo extends SuperService {
    boolean addReservation(Reservation reservation);
    boolean updateReservation(Reservation reservation);
    Reservation searchReservation(Reservation reservation);
    List<Reservation> getAll();
    boolean deleteReservation(Integer reservationNo);

    String getCustomerName(Integer cusId);

    String getRoomStatus(Integer roomNo);

    void makeRoomOccupied(Integer roomNo);

    Double getTotalAmount(Long daysBetween, Integer roomNo);
}
