package repository.custom;

import model.Customer;
import repository.CrudRepository;

public interface CustomerDao extends CrudRepository<Customer,String> {

}
