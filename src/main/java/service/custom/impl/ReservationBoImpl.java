package service.custom.impl;

import model.Reservation;
import repository.DaoFactory;
import repository.custom.ReservationDao;
import service.custom.ReservationBo;
import util.DaoType;

import java.util.List;

public class ReservationBoImpl implements ReservationBo {

    ReservationDao reservationDao = DaoFactory.getInstance().getDaoType(DaoType.RESERVATION);

    @Override
    public boolean addReservation(Reservation reservation) {
        return reservationDao.save(reservation);
    }

    @Override
    public String updateReservation(Reservation reservation) {

        String reservationStatus = reservationDao.getReservationStatus(reservation.getReservationId());

        if (reservationStatus.equals("paid")){
            return "paid";
        } else {
            Boolean isUpadated = reservationDao.update(reservation);
            if (isUpadated) {
                return "Reservation updated!!!";
            }
        }

        return null;

    }

    @Override
    public Reservation searchReservation(Reservation reservation) {
        return reservationDao.search(reservation);
    }

    @Override
    public List<Reservation> getAll() {
        return reservationDao.getAll();
    }

    @Override
    public String deleteReservation(Integer reservationNo) {

        String reservationStatus = reservationDao.getReservationStatus(reservationNo);

        if (reservationStatus.equals("paid")){
            return "paid";
        } else {
            Boolean isDeleted = reservationDao.delete(reservationNo);
            if (isDeleted) {
                return "Reservation deleted!!!";
            }
        }

        return null;

    }

    @Override
    public String getCustomerName(Integer cusId) {
        return reservationDao.getCustomerName(cusId);
    }

    @Override
    public String getRoomStatus(Integer roomNo) {
        return reservationDao.getRoomStatus(roomNo);
    }

    @Override
    public void makeRoomOccupied(Integer roomNo) {
        reservationDao.makeRoomOccupied(roomNo);
    }

    @Override
    public Double getTotalAmount(Long daysBtwn, Integer roomNo) {
        Double roomPrice = reservationDao.getRoomPrice(roomNo);
        return  roomPrice*(daysBtwn+1);
    }

}
