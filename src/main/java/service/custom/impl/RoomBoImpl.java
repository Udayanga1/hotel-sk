package service.custom.impl;

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
        return roomDao.save(room);
    }

    @Override
    public boolean updateRoom(Room room) {
        return roomDao.update(room);
    }

    @Override
    public Room searchRoom(Integer roomNo) {
        Room room = new Room();
        room.setId(roomNo);
        return roomDao.search(room);
    }

    @Override
    public List<Room> getAll() {
        return roomDao.getAll();
    }

    @Override
    public boolean deleteRoom(Integer roomNo) {
        return roomDao.delete(roomNo);
    }
}
