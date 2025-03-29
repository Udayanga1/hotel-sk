package repository.custom.impl;

import db.DBConnection;
import model.Room;
import repository.custom.RoomDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class RoomDaoImpl implements RoomDao {
    @Override
    public boolean save(Room entity) {
        System.out.println("RoomDaoImpl: " + entity);
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
        return null;
    }

    @Override
    public boolean delete(Integer integer) {
        return false;
    }

    @Override
    public List<Room> getAll() {
        return List.of();
    }
}
