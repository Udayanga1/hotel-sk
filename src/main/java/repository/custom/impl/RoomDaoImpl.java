package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import model.Room;
import repository.custom.RoomDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    @Override
    public boolean save(Room entity) {
        String SQL = "INSERT INTO room (type, price, status) VALUES(?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,entity.getType());
            psTm.setObject(2,entity.getPrice());
            psTm.setObject(3,entity.getStatus());
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Room entity) {
        return false;
    }

    @Override
    public Room search(Room entity) {

        if (entity == null || entity.getId() == null) {
            return null;
        }

        Room room = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm;

            String SQL = "SELECT * FROM room WHERE id=?";
            psTm = connection.prepareStatement(SQL);
            psTm.setInt(1, entity.getId());

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                room = new Room();
                room.setId(rs.getInt("id"));
                room.setType(rs.getString("type"));
                room.setPrice(rs.getDouble("price"));
                room.setStatus(rs.getString("status"));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return room;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Room> getAll() {
        String SQL = "SELECT * FROM room";
        List<Room> roomList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL);
             ResultSet resultSet = psTm.executeQuery()) {

            while (resultSet.next()) {
                // Assuming Room has a constructor Room(int id, String type, double price, String status)
                Room room = new Room(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getDouble(3),
                        resultSet.getString(4)
                );
                roomList.add(room);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving rooms", e);
        }

        return roomList;
    }
}
