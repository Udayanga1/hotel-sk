package repository;

import repository.custom.impl.CustomerDaoImpl;
import repository.custom.impl.ReservationDaoImpl;
import repository.custom.impl.RoomDaoImpl;
import repository.custom.impl.UserDaoImpl;
import util.DaoType;

public class DaoFactory {
    private static DaoFactory instance;
    private DaoFactory(){}
    public static DaoFactory getInstance() {
        return instance==null?instance=new DaoFactory():instance;
    }

    public <T extends SuperDao> T getDaoType(DaoType type){
        switch (type){
            case CUSTOMER: return (T) new CustomerDaoImpl();
            case USER: return (T) new UserDaoImpl();
            case ROOM: return (T) new RoomDaoImpl();
            case RESERVATION: return (T) new ReservationDaoImpl();
        }
        return null;
    }

}
