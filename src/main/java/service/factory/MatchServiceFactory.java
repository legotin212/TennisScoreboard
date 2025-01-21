package service.factory;

import service.DefaultOngoingMatchService;
import service.OngoingMatchService;

public class MatchServiceFactory {
    private static final OngoingMatchService INSTANCE = DefaultOngoingMatchService.getInstance();

    public static OngoingMatchService getMatchService() {
        return INSTANCE;
    }
}
