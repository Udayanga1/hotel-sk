package service;

import service.custom.impl.CustomerBoImpl;
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
        }
        return null;

    }
}
