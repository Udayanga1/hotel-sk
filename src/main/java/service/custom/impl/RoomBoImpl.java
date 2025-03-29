package service.custom.impl;

import model.Customer;
import model.Room;
import repository.DaoFactory;
import repository.custom.RoomDao;
import service.custom.RoomBo;
import util.DaoType;

import java.util.List;

public class RoomBoImpl implements RoomBo {

    RoomDao roomDao = DaoFactory.getInstance().getDaoType(DaoType.ROOM);

    @Override
    public boolean addRoom(Room room) {
        System.out.println("RoomBoImpl: " + room);
        return roomDao.save(room);
    }

    @Override
    public boolean updateRoom(Room room) {
        return false;
    }

    @Override
    public Customer searchRoom(Room room) {
        return null;
    }

    @Override
    public List<Customer> getAll() {
        return List.of();
    }

    @Override
    public boolean deleteRoom(Room room) {
        return false;
    }
}
