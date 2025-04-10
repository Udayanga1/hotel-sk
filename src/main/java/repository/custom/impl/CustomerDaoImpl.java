package repository.custom.impl;

import db.DBConnection;
import model.Customer;
import repository.custom.CustomerDao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerDaoImpl implements CustomerDao {
    @Override
    public boolean save(Customer entity) {
        String SQL = "INSERT INTO customer (name, contact_no) VALUES(?,?)";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);

            psTm.setObject(1,entity.getName());
            psTm.setObject(2,entity.getContactNumber());
            int rowsAffected = psTm.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            throw new RuntimeException("Error saving customer", e);
        }


    }

    @Override
    public boolean update(Customer entity) {
        String SQL = "UPDATE customer SET name=?, contact_no=? WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,entity.getName());
            psTm.setObject(2,entity.getContactNumber());
            psTm.setObject(3,entity.getId());
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Customer search(Customer entity) {
        String SQL = "";
        Customer customer = null;

        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm;

            if (!entity.getName().isEmpty() && !entity.getContactNumber().isEmpty()) {
                SQL = "SELECT * FROM customer WHERE name = ? AND contact_no = ? LIMIT 1;";
                psTm = connection.prepareStatement(SQL);
                psTm.setString(1, entity.getName());
                psTm.setString(2, entity.getContactNumber());
            } else if (!entity.getName().isEmpty()) {
                SQL = "SELECT * FROM customer WHERE name = ? LIMIT 1;";
                psTm = connection.prepareStatement(SQL);
                psTm.setString(1, entity.getName());
            } else if (!entity.getContactNumber().isEmpty()) {
                SQL = "SELECT * FROM customer WHERE contact_no = ? LIMIT 1;";
                psTm = connection.prepareStatement(SQL);
                psTm.setString(1, entity.getContactNumber());
            } else {
                return null;
            }

            ResultSet rs = psTm.executeQuery();

            if (rs.next()) {
                customer = new Customer();
                customer.setId(rs.getInt("id"));
                customer.setName(rs.getString("name"));
                customer.setContactNumber(rs.getString("contact_no"));
            }
            rs.close();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return customer;
    }

    @Override
    public boolean delete(Integer cusId) {
        String SQL = "DELETE FROM customer WHERE id=?";
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            PreparedStatement psTm = connection.prepareStatement(SQL);
            psTm.setObject(1,cusId);
            return psTm.executeUpdate()>0;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Customer> getAll() {
        String SQL = "SELECT * FROM customer";
        List<Customer> customerList = new ArrayList<>();

        try (Connection connection = DBConnection.getInstance().getConnection();
             PreparedStatement psTm = connection.prepareStatement(SQL);
             ResultSet resultSet = psTm.executeQuery()) {

            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3)
                );
                customerList.add(customer);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error retrieving customers", e);
        }

        return customerList;
    }
}
