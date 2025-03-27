package service.custom.impl;

import model.Customer;
import model.User;
import repository.DaoFactory;
import repository.custom.CustomerDao;
import repository.custom.UserDao;
import service.custom.UserBo;
import util.DaoType;

import java.util.List;

public class UserBoImpl implements UserBo {

    UserDao userDao = DaoFactory.getInstance().getDaoType(DaoType.USER);


//    @Override
//    public boolean add(User user) {
//        System.out.println("user in UserBoImpl: " + user);
//
//        return  userDao.save(user) ;
//    }

    @Override
    public boolean addUser(User user) {
        System.out.println("UserBoImpl: " + user);
        return userDao.save(user);
    }

    @Override
    public boolean updateUser(User user) {
        return false;
    }

    @Override
    public User searchUser(User user) {
        return null;
    }

    @Override
    public List<User> getAll() {
        return List.of();
    }

    @Override
    public boolean deleteUser(String id) {
        return false;
    }
}
