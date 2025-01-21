package service.factory;

import service.OngoingMatchServiceImpl;
import service.OngoingMatchService;

public class MatchServiceFactory {
    private static final OngoingMatchService INSTANCE = OngoingMatchServiceImpl.getInstance();

    public static OngoingMatchService getMatchService() {
        return INSTANCE;
    }
}
