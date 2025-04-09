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

        String SQL = "INSERT INTO payment (reservation_no, pay_date, total_due, discount, tax) VALUES (?,?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,entity.getReservationNo());
            psTm.setObject(2,entity.getPayDate());
            psTm.setObject(3,entity.getTotalDue());
            psTm.setObject(4,entity.getDiscount());
            psTm.setObject(5,entity.getTax());
            int rowsAffected = psTm.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving payment: ", e);
        }
    }

    @Override
    public boolean update(Payment entity) {
        return false;
    }

    @Override
    public Payment search(Payment entity) {
        String SQL = "SELECT * FROM payment WHERE reservation_no = ?";

        Payment payment = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setInt(1, entity.getReservationNo());

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                payment = new Payment(
                        rs.getInt("id"),
                        rs.getInt("reservation_no"),
                        rs.getString("type"),
                        rs.getDate("pay_date").toLocalDate(),
                        rs.getDouble("total_due"),
                        rs.getDouble("discount"),
                        rs.getDouble("tax")
                );
            }

            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for payment: ", e);
        }
        return payment;
    }

    @Override
    public boolean delete(Integer invoiceNo) {
        String SQL = "DELETE FROM payment WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,invoiceNo);
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    @Override
    public String getReservationStatus(Integer reservationNo) {
        String SQL = "SELECT status from reservation WHERE id = ?";

        String reservationStatus = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setInt(1, reservationNo);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                reservationStatus = rs.getString("status");

            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for reservation: ", e);
        }
        return reservationStatus;
    }

    @Override
    public void updateReservationStatus(Integer reservationNo, String status) {

        String SQL = "UPDATE reservation SET status=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1, status);
            psTm.setObject(2, reservationNo);
            psTm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error updating reservation: " + e);
        }
    }
}
