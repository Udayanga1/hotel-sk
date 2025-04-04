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
    public boolean updateReservation(Reservation reservation) {
        return reservationDao.update(reservation);
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
    public boolean deleteReservation(Integer reservationNo) {
        return reservationDao.delete(reservationNo);
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
