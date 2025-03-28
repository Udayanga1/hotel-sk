package service.custom;

import model.Customer;
import service.SuperService;

import java.util.List;

public interface CustomerBo extends SuperService {
    boolean addCustomer(Customer customer);
    boolean updateCustomer(Customer customer);
    Customer searchCustomer(Customer customer);
    List<Customer> getAll();
    boolean deleteCustomer(Customer customer);
}
