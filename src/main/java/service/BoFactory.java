package service;

import service.custom.impl.*;
import util.BoType;

public class BoFactory {
    private static BoFactory instance;
    private BoFactory(){}
    public static BoFactory getInstance() {
        return instance==null?instance=new BoFactory():instance;
    }
    public <T extends SuperService> T getBoType(BoType type){

        switch (type){
            case CUSTOMER: return (T) new CustomerBoImpl();
            case USER: return (T) new UserBoImpl();
            case ROOM: return (T) new RoomBoImpl();
            case RESERVATION: return (T) new ReservationBoImpl();
            case BILL: return (T) new BillingBoImpl();
        }
        return null;

    }
}
