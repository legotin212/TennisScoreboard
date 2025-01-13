package service.factory;

import service.DefaultMatchService;
import service.MatchService;

public class MatchServiceFactory {
    private static final MatchService INSTANCE = DefaultMatchService.getInstance();

    public static MatchService getMatchService() {
        return INSTANCE;
    }
}
