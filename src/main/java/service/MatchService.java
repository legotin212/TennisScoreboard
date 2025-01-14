package service;

import service.model.OngoingMatch;

import java.util.UUID;

public interface MatchService {
    UUID createMatch(Integer playerOneId, Integer playerTwoId);
    OngoingMatch getOngoingMatch(UUID matchId);
    void endMatch(OngoingMatch match, Integer winnerId);
}
