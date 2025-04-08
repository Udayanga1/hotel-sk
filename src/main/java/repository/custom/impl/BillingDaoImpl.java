package repository.custom.impl;

import db.DBConnection;
import model.Payment;
import model.Reservation;
import model.Room;
import repository.custom.BillingDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BillingDaoImpl implements BillingDao {
    @Override
    public boolean save(Payment entity) {
        return false;
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public Payment search(Payment entity) {
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Payment> getAll() {
        return List.of();
    }

    @Override
    public Reservation getReservationDetails(Integer reservationNo) {
        String SQL = "SELECT * FROM reservation WHERE id = ?";

        Reservation reservation = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setInt(1, reservationNo);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                reservation = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("cusId"),
                        rs.getInt("roomNo"),
                        rs.getDate("checkInDate").toLocalDate(),
                        rs.getDate("checkOutDate").toLocalDate()
                );
            }

            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for reservation: ", e);
        }
        return reservation;
    }

    @Override
    public Double getRoomPrice(Integer roomNo) {
        Double roomPrice = 0.0;
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm;

            String SQL = "SELECT price FROM room WHERE id=?";
            psTm = connection.prepareStatement(SQL);
            psTm.setInt(1, roomNo);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                roomPrice = rs.getDouble("price");
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return roomPrice;
    }
}
