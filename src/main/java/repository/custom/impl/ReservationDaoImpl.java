package repository.custom.impl;

import db.DBConnection;
import model.Reservation;
import repository.custom.ReservationDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReservationDaoImpl implements ReservationDao {

    @Override
    public String getCustomerName(Integer cusId) {

        String SQL = "SELECT name FROM customer WHERE id=?";

        String cusName = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,cusId);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()){
                cusName=rs.getString("name");
                rs.close();
                return cusName;
            } else {
                return cusName;
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String getRoomStatus(Integer roomNo) {
        String SQL = "SELECT status FROM room WHERE id=?";

        String roomStatus = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,roomNo);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()){
                roomStatus=rs.getString("status");
                rs.close();
                return roomStatus;
            } else {
                return roomStatus;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding the room status: " + e);
        }
    }

    @Override
    public void makeRoomOccupied(Integer roomNo) {
        String SQL = "UPDATE room SET status=? WHERE id=?";

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,"Occupied");
            psTm.setObject(2,roomNo);

            psTm.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error changing the room status: " + e);
        }
    }

    @Override
    public Double getRoomPrice(Integer roomNo) {
        String SQL = "SELECT price FROM room WHERE id=?";

        Double roomPrice = 0.0;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,roomNo);

            ResultSet rs = psTm.executeQuery();

            if (rs.next()){
                roomPrice=rs.getDouble("price");
                rs.close();
                return roomPrice;
            } else {
                return roomPrice;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding the room price: " + e);
        }
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
    public boolean save(Reservation entity) {
        String SQL = "INSERT INTO reservation (cusId, roomNo, checkInDate, checkOutDate) VALUES(?,?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,entity.getCustomerId());
            psTm.setObject(2,entity.getRoomNo());
            psTm.setObject(3,entity.getCheckInDate());
            psTm.setObject(4,entity.getCheckOutDate());
            int rowsAffected = psTm.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving reservation: ", e);
        }
    }

    @Override
    public boolean update(Reservation entity) {

        String SQL = "UPDATE reservation SET cusId=?, roomNo=?, checkInDate=?, checkOutDate=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,entity.getCustomerId());
            psTm.setObject(2,entity.getRoomNo());
            psTm.setObject(3,entity.getCheckInDate());
            psTm.setObject(4,entity.getCheckOutDate());
            psTm.setObject(5,entity.getReservationId());
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException("Error updating reservation: " + e);
        }
    }


    @Override
    public Reservation search(Reservation entity) {
        String SQL = "";
        if (entity.getCheckInDate() != null || entity.getCheckOutDate() != null){
            SQL = "SELECT * FROM reservation WHERE cusId = ? AND roomNo = ? AND checkInDate >= ? AND checkOutDate <= ? ORDER BY id DESC LIMIT 1";
        } else {
            SQL = "SELECT * FROM reservation WHERE cusId = ? AND roomNo = ? ORDER BY id DESC LIMIT 1";
        }

        Reservation reservation = null;

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL)) {

            psTm.setInt(1, entity.getCustomerId());
            psTm.setInt(2, entity.getRoomNo());
            if (entity.getCheckInDate() != null || entity.getCheckOutDate() != null){
                psTm.setDate(3, java.sql.Date.valueOf(entity.getCheckInDate()));
                psTm.setDate(4, java.sql.Date.valueOf(entity.getCheckOutDate()));
            }

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                reservation = new Reservation(
                        rs.getInt("id"),
                        rs.getInt("cusId"),
                        rs.getInt("roomNo"),
                        rs.getDate("checkInDate").toLocalDate(),
                        rs.getDate("checkOutDate").toLocalDate(),
                        "unpaid"
                );
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException("Error searching for reservation: ", e);
        }
        return reservation;
    }

    @Override
    public boolean delete(Integer reservationId) {
        String SQL = "DELETE FROM reservation WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,reservationId);
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Reservation> getAll() {
        String SQL = "SELECT * FROM reservation";
        List<Reservation> reservationList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL);
             ResultSet resultSet = psTm.executeQuery()) {

            while (resultSet.next()) {
                Reservation reservation = new Reservation(
                        resultSet.getInt(1),
                        resultSet.getInt(2),
                        resultSet.getInt(3),
                        resultSet.getDate(4).toLocalDate(),
                        resultSet.getDate(5).toLocalDate(),
                        resultSet.getString(6)
                );
                reservationList.add(reservation);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving reservations: ", e);
        }

        return reservationList;
    }
}
