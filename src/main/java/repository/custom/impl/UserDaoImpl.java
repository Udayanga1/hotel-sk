package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import model.User;
import repository.custom.UserDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class UserDaoImpl implements UserDao {
    public boolean save(User entity) {
        String SQL = "INSERT INTO users (username, email, password) VALUES(?,?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,entity.getUserName());
            psTm.setObject(2,entity.getEmail());
            psTm.setObject(3,entity.getEmail());
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public boolean update(User entity) {
        return false;
    }

    @Override
    public User search(User entity) {
        String SQL = "SELECT username, email FROM users WHERE email = ? AND password = ?";
        User user = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm;
            psTm = connection.prepareStatement(SQL);
            psTm.setString(1, entity.getEmail());
            psTm.setString(2, entity.getPassword());

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserName(rs.getString("username"));
                user.setEmail("email");
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    }

    @Override
    public boolean delete(String s) {
        return false;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }
}
