package service.custom;

import model.User;
import service.SuperService;

import java.util.List;

public interface UserBo extends SuperService {
    boolean addUser(User user);
    boolean updateUser(User user);
    User searchUser(User user);
    List<User> getAll();
    boolean deleteUser(String id);
}
