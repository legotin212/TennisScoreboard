package service;

import service.model.OngoingMatch;

import java.util.UUID;

public class MatchScoreService {
    private final MatchService matchService = DefaultMatchService.getInstance();

    public void addPointToPlayer(UUID matchId, UUID playerId) {

    }

    private OngoingMatch getMatch(UUID matchId){
        return matchService.getOngoingMatch(matchId);

    }
}
