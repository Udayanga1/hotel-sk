package repository;

import repository.custom.impl.*;
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
            case BILL: return (T) new BillingDaoImpl();
        }
        return null;
    }

}
