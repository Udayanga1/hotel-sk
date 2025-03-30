package service.custom;

import model.Customer;
import model.Room;
import service.SuperService;

import java.util.List;

public interface RoomBo extends SuperService {
    boolean addRoom(Room room);
    boolean updateRoom(Room room);
    Room searchRoom(Integer roomNo);
    List<Room> getAll();
    boolean deleteRoom(Room room);
}
