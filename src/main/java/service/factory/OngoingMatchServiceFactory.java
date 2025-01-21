package service.factory;

import service.OngoingMatchServiceImpl;
import service.OngoingMatchService;

public class OngoingMatchServiceFactory {
    private static final OngoingMatchService INSTANCE = OngoingMatchServiceImpl.getInstance();

    public static OngoingMatchService getMatchService() {
        return INSTANCE;
    }
}
