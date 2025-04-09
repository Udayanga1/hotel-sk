package service.custom;

import model.Reservation;
import service.SuperService;
import java.util.List;

public interface ReservationBo extends SuperService {
    boolean addReservation(Reservation reservation);
    String updateReservation(Reservation reservation);
    Reservation searchReservation(Reservation reservation);
    List<Reservation> getAll();
    String deleteReservation(Integer reservationNo);

    String getCustomerName(Integer cusId);

    String getRoomStatus(Integer roomNo);

    void makeRoomOccupied(Integer roomNo);

    Double getTotalAmount(Long daysBetween, Integer roomNo);
}
