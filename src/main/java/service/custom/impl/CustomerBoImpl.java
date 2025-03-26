package service.custom.impl;

import model.Customer;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import service.custom.CustomerBo;
import util.DaoType;

import java.util.List;

public class CustomerBoImpl implements CustomerBo {

    CustomerDao customerDao = DaoFactory.getInstance().getDaoType(DaoType.CUSTOMER);

    @Override
    public boolean addCustomer(Customer customer) {
        return customerDao.save(customer);
    }

    @Override
    public boolean updateCustomer(Customer customer) {
        return false;
    }

    @Override
    public Customer searchCustomer(Customer customer) {
        return customerDao.search(customer);
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }

    @Override
    public boolean deleteCustomer(String id) {
        return false;
    }


}
